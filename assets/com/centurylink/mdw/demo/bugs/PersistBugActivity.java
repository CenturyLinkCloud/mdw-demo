package com.centurylink.mdw.demo.bugs;

import java.util.HashMap;
import java.util.Map;

import com.centurylink.mdw.activity.ActivityException;
import com.centurylink.mdw.app.ApplicationContext;
import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.dataaccess.DataAccessException;
import com.centurylink.mdw.model.Status;
import com.centurylink.mdw.model.asset.AssetVersionSpec;
import com.centurylink.mdw.model.listener.Listener;
import com.centurylink.mdw.model.task.TaskInstance;
import com.centurylink.mdw.model.task.TaskTemplate;
import com.centurylink.mdw.model.workflow.ActivityRuntimeContext;
import com.centurylink.mdw.service.data.task.TaskTemplateCache;
import com.centurylink.mdw.services.ServiceLocator;
import com.centurylink.mdw.services.TaskServices;
import com.centurylink.mdw.util.log.StandardLogger.LogLevel;
import com.centurylink.mdw.util.timer.Tracked;
import com.centurylink.mdw.workflow.activity.DefaultActivityImpl;

/**
 * Create a bug using the MDW manual tasks API.
 */
@Tracked(LogLevel.TRACE)
public class PersistBugActivity extends DefaultActivityImpl {
	
	private static final String TASK_TEMPLATE = "com.centurylink.mdw.demo.bugs/ResolveBug.task";

    /**
     * Create a manual task instance for the bug based on the ResolveBug template.
     */
    @Override
    public Object execute(ActivityRuntimeContext runtimeContext) throws ActivityException {
    	Bug requestBug = (Bug) this.getVariableValue("request");
    	
    	try {
	    	AssetVersionSpec templateAsset = new AssetVersionSpec(TASK_TEMPLATE, "0");
	        TaskTemplate template = TaskTemplateCache.getTaskTemplate(templateAsset);
	        if (template == null)
	            throw new DataAccessException("Task template not found: " + template);
	
	        TaskServices taskServices = ServiceLocator.getTaskServices();
	        TaskInstance instance = taskServices.createTask(template.getTaskId(), getMasterRequestId(), 
	        		getProcessInstanceId(), null, null, requestBug.getTitle(), requestBug.getDescription());
	
	        loginfo("Created task instance " + instance.getId() + " (" + template.getTaskName() + ")");

	        // clone request for bug response
	        Bug bug = new Bug(instance.getTaskInstanceId(), requestBug.getJson());
	        // TODO: build bug (containing task instance?)
	        // change the response
	        setVariableValue("response", bug);
	        
	        // set response status and headers
	        Map<String,String> headers = new HashMap<>();
	        headers.put(Listener.METAINFO_HTTP_STATUS_CODE, String.valueOf(Status.CREATED.getCode()));
	        headers.put("Location", ApplicationContext.getServicesUrl() + "/demo/api/bugs/" + bug.getId());
	        setVariableValue("responseHeaders", headers);
	        return null;
    	}
    	catch (DataAccessException | ServiceException ex) {
    		throw new ActivityException("Error creating task " + TASK_TEMPLATE, ex);
    	}
    }
    
}
