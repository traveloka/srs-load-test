
package main.java.parser;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONtoMap {

  private static final Class<?> JSON_OBJECT = JSONObject.class;
  private static final Logger LOGGER = Logger.getLogger(JSONFlattener.class);


  /**
   * Parse the JSON file using the specified character encoding
   *
   * @param file
   * @return
   */
  public static  Map<String, JSONObject> parseJson(File file, String encoding) {
    Map<String, JSONObject> flatJson = null;
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
  public static Map<String, JSONObject> parseJson(String json) {
    Map<String, JSONObject> flatJson = null;


      JSONObject jsonObject = new JSONObject(json);
      flatJson=parse(jsonObject);


    return flatJson ;
  }

  /**
   * Parse a JSON Object
   *
   * @param jsonObject
   * @return
   */
  public static Map<String, JSONObject> parse(JSONObject jsonObject) {
    Map<String, JSONObject> flatJson = new LinkedHashMap<String, JSONObject>();
    flatten(jsonObject, flatJson);

    return flatJson;
  }

  /**
   * Parse a JSON Array
   *
   * @param
   * @return
   */

  private static void flatten(JSONObject obj, Map<String, JSONObject> flatJson) {
    Iterator<?> iterator = obj.keys();
    while (iterator.hasNext()) {
      String key = iterator.next().toString();

      if (obj.get(key).getClass() == JSON_OBJECT) {
        JSONObject jsonObject = (JSONObject) obj.get(key);
        flatJson.put(key,jsonObject);
      }
    }

  }
}