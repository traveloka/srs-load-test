
package main.java.parser;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONFlattener {

    private static final Class<?> JSON_OBJECT = JSONObject.class;
    private static final Class<?> JSON_ARRAY = JSONArray.class;
    private static final Logger LOGGER = Logger.getLogger(JSONFlattener.class);


    public static List<Map<String, List<String>>> parseJson(File file) {
        return parseJson(file, "UTF-8");
    }

    /**
     * Parse the JSON file using the specified character encoding
     *
     * @param file
     * @return
     */
    public static List<Map<String, List<String>>> parseJson(File file, String encoding) {
        List<Map<String, List<String>>> flatJson = null;
        String json = "";

        try {
            json = FileUtils.readFileToString(file, encoding);
            flatJson = parseJson(json);
        } catch (IOException e) {
            LOGGER.error("JsonFlattener#ParseJson(file, encoding) IOException: ", e);
        } catch (Exception ex) {
            LOGGER.error("JsonFlattener#ParseJson(file, encoding) Exception: ", ex);
        }

        return flatJson;
    }

    /**
     * Parse the JSON String
     *
     * @param json
     * @return
     * @throws Exception
     */
    public static List<Map<String, List<String>>> parseJson(String json) {
        List<Map<String, List<String>>> flatJson = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            flatJson = new ArrayList<Map<String, List<String>>>();
            flatJson.add(parse(jsonObject));
        } catch (JSONException je) {
            LOGGER.info("Handle the JSON String as JSON Array");
            flatJson = handleAsArray(json);
        }

        return flatJson;
    }

    /**
     * Parse a JSON Object
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, List<String>> parse(JSONObject jsonObject) {
        Map<String, List<String>> flatJson = new LinkedHashMap<String, List<String>>();
        flatten(jsonObject, flatJson, "");

        return flatJson;
    }

    /**
     * Parse a JSON Array
     *
     * @param jsonArray
     * @return
     */
    public static List<Map<String, List<String>>> parse(JSONArray jsonArray) {
        JSONObject jsonObject = null;
        List<Map<String, List<String>>> flatJson = new ArrayList<Map<String, List<String>>>();
        int length = jsonArray.length();

        for (int i = 0; i < length; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Map<String, List<String>> stringMap = parse(jsonObject);
            flatJson.add(stringMap);
        }

        return flatJson;
    }

    /**
     * Handle the JSON String as Array
     *
     * @param json
     * @return
     * @throws Exception
     */
    private static List<Map<String, List<String>>> handleAsArray(String json) {
        List<Map<String, List<String>>> flatJson = null;

        try {
            JSONArray jsonArray = new JSONArray(json);
            flatJson = parse(jsonArray);
        } catch (Exception e) {
            // throw new Exception("Json might be malformed");
            LOGGER.error("JSON might be malformed, Please verify that your JSON is valid");
        }

        return flatJson;
    }

    /**
     * Flatten the given JSON Object
     * <p>
     * This method will convert the JSON object to a Map of
     * String keys and values
     *
     * @param obj
     * @param flatJson
     * @param prefix
     */
    private static void flatten(JSONObject obj, Map<String, List<String>> flatJson, String prefix) {
        Iterator<?> iterator = obj.keys();
        String _prefix = prefix != "" ? prefix + "." : "";

        while (iterator.hasNext()) {
            List<String> values = new ArrayList<String>();
            String key = iterator.next().toString();

            if (obj.get(key).getClass() == JSON_OBJECT) {
                JSONObject jsonObject = (JSONObject) obj.get(key);
                flatten(jsonObject, flatJson, _prefix + key);
            } else if (obj.get(key).getClass() == JSON_ARRAY) {
                JSONArray jsonArray = (JSONArray) obj.get(key);

                if (jsonArray.length() < 1) {
                    continue;
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    String value = jsonArray.getString(i);
                    values.add(value);
                }
                if (values != null && !values.equals("null")) {
                    flatJson.put(_prefix + key, values);
                }
            } else {
                String value = obj.get(key).toString();
                values.add(value);
                if (values != null && !values.equals("null")) {
                    flatJson.put(_prefix + key, values);
                }
            }
        }

    }
}