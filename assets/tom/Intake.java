/**
 * Copyright (c) 2017 CenturyLink, Inc. All Rights Reserved.
 */
package tom;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;

import org.json.JSONException;
import org.json.JSONObject;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.model.StatusResponse;
import com.centurylink.mdw.services.ServiceLocator;
import com.centurylink.mdw.services.WorkflowServices;
import com.centurylink.mdw.services.rest.JsonRestService;
import com.centurylink.tom.model.Order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("/order")
@Api("TOM Intake Service")
public class Intake extends JsonRestService {

    @Override
    @ApiOperation(value="Create an order",
      notes="TOM Intake service accepts an Order and returns an acknowledgement.", 
      response=StatusResponse.class)
    @ApiImplicitParams({
      @ApiImplicitParam(name="Order", paramType="body", required=true, dataType="com.centurylink.tom.model.Order"),
      @ApiImplicitParam(name="source-system", paramType="header", required=true, dataType="string"),
      @ApiImplicitParam(name="transaction-id", paramType="header", required=true, dataType="string")})
    public JSONObject post(String path, JSONObject content, Map<String,String> headers)
            throws ServiceException, JSONException {
        String masterRequestId = headers.get("transaction-id");
        if (masterRequestId == null)
            throw new ServiceException(ServiceException.BAD_REQUEST, "Missing header: transactionId");
        
        Order order = new Order(content);
        
        WorkflowServices workflowServices = ServiceLocator.getWorkflowServices();
        String intakeProcess = "com.centurylink.tom.workflow/Intake";
        Map<String,Object> vars = new HashMap<>();
        // TODO: populate any input variables

        StatusResponse response = (StatusResponse) workflowServices
                .invokeServiceProcess(intakeProcess, order, masterRequestId, vars, headers);
        
        return response.getJson();
    }

    @Override
    public JSONObject get(String path, Map<String,String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.get(path, headers);
    }

    @Override
    public JSONObject put(String path, JSONObject content, Map<String,String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.put(path, content, headers);
    }

    @Override
    public JSONObject delete(String path, JSONObject content, Map<String,String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.delete(path, content, headers);
    }    
}
