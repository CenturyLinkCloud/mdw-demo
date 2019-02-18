package demo;

import java.util.HashMap;
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
        @ApiImplicitParam(name="bug", paramType="body", required=true, dataType="com.centurylink.mdw.demo.bugs.Bug")
    })
    public JSONObject post(String path, JSONObject content, Map<String,String> headers)
    throws ServiceException, JSONException {
        String requestId = Long.toHexString(System.nanoTime());
        return invokeServiceProcess("com.centurylink.mdw.demo.bugs/Create Bug", new Bug(content), requestId,
                null, headers);
    }

    @Override
    @Path("/{id}")
    @ApiOperation(value="Retrieve a bug by id", response=Bug.class)
    public JSONObject get(String path, Map<String,String> headers)
    throws ServiceException, JSONException {
        TaskInstance bugTask = getBugTask(path);
        Bug bug = new Bug(bugTask.getJson());
        bug.setSeverity(bugTask.getPriority());
        bug.setDescription(bugTask.getComments());
        return bug.getJson();
    }

    @Override
    @Path("/{id}")
    @ApiOperation(value="Update a bug", response=StatusResponse.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name="bug", paramType="body", required=true, dataType="com.centurylink.mdw.demo.bugs.Bug")
    })
    public JSONObject put(String path, JSONObject content, Map<String,String> headers)
    throws ServiceException, JSONException {
        TaskInstance bugTask = getBugTask(path);
        Bug bug = new Bug(content);
        TaskServices taskServices = ServiceLocator.getTaskServices();
        // self-assign if needed
        if (bugTask.getAssignee() == null) {
            String user = getAuthUser(headers);
            performAction("Assign", bugTask.getTaskInstanceId(), user, user);
        }
        // update header info
        bugTask.setPriority(bug.getSeverity());
        bugTask.setComments(bug.getDescription());
        taskServices.updateTask(getAuthUser(headers), bugTask);
        // update runtime values
        Map<String,String> values = new HashMap<>();
        values.put("bug", bug.toString());
        taskServices.applyValues(bugTask.getTaskInstanceId(), values);
        return null;  // sends a standard 200 response
    }

    @Override
    @Path("/{id}")
    @ApiOperation(value="Delete (cancel) a bug by id", response=StatusResponse.class)
    public JSONObject delete(String path, JSONObject content, Map<String,String> headers)
    throws ServiceException, JSONException {
        TaskInstance bugTask = getBugTask(path);
        performAction("Cancel", bugTask.getTaskInstanceId(), getAuthUser(headers), null);
        return null;
    }

    /**
     * Return a bug task according to the {id} path param.
     */
    private TaskInstance getBugTask(String path) throws ServiceException {
        String id = getSegment(path, 2);
        if (id == null)
            throw new ServiceException(Status.BAD_REQUEST.getCode(), "Missing path parameter: {id}");
        TaskServices taskServices = ServiceLocator.getTaskServices();
        TaskInstance bugTask = taskServices.getInstance(Long.parseLong(id));
        if (bugTask == null)
            throw new ServiceException(Status.NOT_FOUND.getCode(), "Bug not found: " + id);
        return bugTask;
    }

    private void performAction(String action, Long taskInstanceId, String user, String assignee)
    throws ServiceException {
        ServiceLocator.getTaskServices().performAction(taskInstanceId, action, user, assignee, null, null, true);
    }
}