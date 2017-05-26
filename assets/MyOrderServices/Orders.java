/**
 * Copyright (c) 2016 CenturyLink, Inc. All Rights Reserved.
 */

package MyOrderServices;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;

import org.json.JSONObject;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.common.service.types.StatusMessage;
import com.centurylink.mdw.services.ServiceLocator;
import com.centurylink.mdw.services.WorkflowServices;
import com.centurylink.mdw.services.rest.JsonRestService;
 
@Path("/Order")
public class Orders extends JsonRestService {
	
	@Override
	public JSONObject post(String path, JSONObject content, Map<String, String> headers) throws ServiceException{
		
		//This Map (stringParams) is used to bind the input type process variables with values when it is launched.
		Map<String,Object> stringParams = new HashMap<String,Object>(); 
			
		WorkflowServices workflowServices = ServiceLocator.getWorkflowServices();
		Object response = workflowServices.invokeServiceProcess("MyOrderServices/OrderProcess", content, null, stringParams, headers);
		
		return (JSONObject) response;
	}
}
 