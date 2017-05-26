/**
 * Copyright (c) 2016 CenturyLink, Inc. All Rights Reserved.
 */

package MyOrderServices;

import org.json.JSONObject;

import com.centurylink.mdw.activity.ActivityException;
import com.centurylink.mdw.model.workflow.ActivityRuntimeContext;
import com.centurylink.mdw.util.log.StandardLogger.LogLevel;
import com.centurylink.mdw.util.timer.Tracked;
import com.centurylink.mdw.workflow.activity.DefaultActivityImpl;
  
@Tracked(LogLevel.TRACE)
public class MyOrderValidatorActivity extends DefaultActivityImpl {
	@Override
	public Object execute(ActivityRuntimeContext runtimeContext) throws ActivityException {
		loginfo("Validating order...");
		boolean valid = false;
		try {
			JSONObject jsonObj = (JSONObject) getVariableValue("request");
			String orderId = (String) jsonObj.get("orderId");
			setVariableValue("orderId", orderId);
			String msg = "Success";
			if (!jsonObj.has("orderId")){
				msg = "Missing order ID.";
			}
			else if (!Character.isDigit(orderId.charAt(0))) {
				msg = "Order ID must begin with a digit.";	        
			}
			valid = msg.equals("Success");
			setVariableValue("validationResult", msg);
			
		} catch (Exception ex) {
			throw new ActivityException(ex.getMessage(), ex);
		}
		return valid;
	}
}