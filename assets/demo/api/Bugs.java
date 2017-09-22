package demo.api;
    
import java.util.Map;

import javax.ws.rs.Path;

import org.json.JSONException;
import org.json.JSONObject;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.demo.bugs.Bug;
import com.centurylink.mdw.model.Status;
import com.centurylink.mdw.model.StatusResponse;
import com.centurylink.mdw.model.task.TaskInstance;
import com.centurylink.mdw.services.ServiceLocator;
import com.centurylink.mdw.services.TaskServices;
import com.centurylink.mdw.services.rest.JsonRestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
    
@Path("/bugs")
@Api("Bugs API")
public class Bugs extends JsonRestService {
    
    @Override
    @ApiOperation(value="Report a bug",
    notes="We expect this service never to be used :)", response=StatusResponse.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name="bug", paramType="body", required=true, 
                dataType="com.centurylink.mdw.demo.bugs.Bug")
    })
    public JSONObject post(String path, JSONObject content, Map<String,String> headers)
            throws ServiceException, JSONException {
        String requestId = Long.toHexString(System.nanoTime());
        return invokeServiceProcess("Create Bug", new Bug(content), requestId, null, headers);
    }
    
    @Override
    @Path("/{id}")
    @ApiOperation(value="Retrieve a bug by id", response=Bug.class)
    public JSONObject get(String path, Map<String,String> headers)
    throws ServiceException, JSONException {
        String id = getSegment(path, 1);
        if (id == null)
            throw new ServiceException(Status.BAD_REQUEST.getCode(), "Missing path parameter: {id}");
        TaskServices taskServices = ServiceLocator.getTaskServices();
        TaskInstance bugTask = taskServices.getInstance(Long.parseLong(id));
        return new Bug(bugTask.getJson()).getJson();
    }    
}