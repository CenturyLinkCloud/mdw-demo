package demo;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.model.Value;
import com.centurylink.mdw.service.rest.Processes;
import com.centurylink.mdw.services.ServiceLocator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.Path;
import java.util.Map;

@Path("/MyProcesses")
public class MyProcesses extends Processes {

    @Override
    public JSONObject get(String path, Map<String,String> headers) throws ServiceException, JSONException {
        JSONObject processList = super.get(path.substring(path.indexOf('/') + 1), headers);
        if (processList.has("processInstances")) {
            JSONArray instances = processList.getJSONArray("processInstances");
            for (int i = 0; i < instances.length(); i++) {
                JSONObject instance = instances.getJSONObject(i);
                long instanceId = instance.getLong("id");
                Map<String,Value> instanceValues = ServiceLocator.getWorkflowServices().getProcessValues(instanceId);
                Value instanceValue = instanceValues.get("inputVar");
                if (instanceValue != null)
                    instance.put("inputVar", instanceValue.getValue());
            }
        }
        return processList;
    }
}
