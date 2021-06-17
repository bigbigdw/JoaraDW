package bigbigdw.joaradw.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    private Util() { throw new IllegalStateException("Util class"); }

    // Parse JSONObject
    public static String getJSONObjStringValue(JSONObject obj, String name) throws JSONException {
            if (obj.has(name)) {
                if (obj.getString(name) == null || obj.getString(name).equals("null")) {
                    return "null";
                } else {
                    return obj.getString(name);
                }
            }
        return "null";
    }

}
