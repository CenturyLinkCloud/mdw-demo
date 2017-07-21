package demo;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.centurylink.mdw.common.service.ServiceException;
import com.centurylink.mdw.services.rest.JsonRestService;

public class Bugs extends JsonRestService {

    @Override
    public JSONObject get(String path, Map<String, String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.get(path, headers);
    }

    @Override
    public JSONObject post(String path, JSONObject content, Map<String, String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.post(path, content, headers);
    }

    @Override
    public JSONObject put(String path, JSONObject content, Map<String, String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.put(path, content, headers);
    }

    @Override
    public JSONObject delete(String path, JSONObject content, Map<String, String> headers)
            throws ServiceException, JSONException {
        // TODO Auto-generated method stub
        return super.delete(path, content, headers);
    }
    
}
