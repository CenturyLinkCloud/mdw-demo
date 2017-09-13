package api;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;

import org.json.JSONException;
import org.json.JSONObject;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.demo.bugs.Bug;
import com.centurylink.mdw.model.StatusResponse;
import com.centurylink.mdw.services.ServiceLocator;
import com.centurylink.mdw.services.rest.JsonRestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Path("/")
@Api("Bugs REST API")
public class Bugs extends JsonRestService {

    @Override
    @ApiImplicitParams({
        @ApiImplicitParam(name="bug", paramType="body", required=true, 
                dataType="com.centurylink.mdw.demo.bugs.Bug")
    })
    public JSONObject post(String path, JSONObject content, Map<String,String> headers)
            throws ServiceException, JSONException {
        String requestId = Long.toHexString(System.nanoTime());
        Object response = ServiceLocator.getWorkflowServices().invokeServiceProcess("Create Bug",
                new Bug(content), requestId, new HashMap<>(), headers);
        return ((StatusResponse)response).getJson();
    }
}
