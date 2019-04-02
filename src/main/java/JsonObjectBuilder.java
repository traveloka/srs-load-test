package main.java;

import main.java.parser.JSONtoMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * // TODO Comment
 */


public class JsonObjectBuilder {
  static JsonObjectBuilder j= new JsonObjectBuilder();
  static Map<String, JSONObject> mapJson = JSONtoMap.parseJson(new JsonObjectBuilder().getFile("value.json"), "UTF-8");

  public static JSONObject getJsonObject(List<List<String>> list, String searchClient) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("jsonrpc", "2.0");
    jsonObject.put("id", "123");
    jsonObject.put("source", "wendy-app-02.test.tvlk.cloud");
    jsonObject.put("method", "search");
    jsonObject.put("params", getParamsJsonArray(list, searchClient));
    return jsonObject;

  }

  public static JSONObject getSortObject(String sort) {
    JSONObject sortObject = new JSONObject();
    sortObject.put("field", sort);
    sortObject.put("order", "ASC");
    if (sort.equalsIgnoreCase("DISTANCE")) {
      sortObject.put("values", mapJson.get("DISTANCE"));
    }
    return sortObject;
  }

  public static JSONArray getFilterJsonArray(List<String> filterList) {
    JSONArray filterArray = new JSONArray();
    for (int k = 0; k < filterList.size(); k++) {
      JSONObject obj2 = new JSONObject();
      obj2.put("filterField", filterList.get(k));
      obj2.put("values", mapJson.get(filterList.get(k)));
      filterArray.put(obj2);
    }
    return filterArray;
  }

  public static JSONArray getCategorySpecJsonArray(String category) {
    List<String> categoryList = new ArrayList<>();
    categoryList.add(category);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("categories", categoryList);
    jsonObject.put("page", 1);
    jsonObject.put("size", 20);
    JSONArray categorySpecArray = new JSONArray();
    categorySpecArray.put(jsonObject);
    return categorySpecArray;
  }

  public static JSONArray getParamsJsonArray(List<List<String>> list, String searchClient) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("searchClients", searchClient.toUpperCase());
    jsonObject.put("categorySpecs", getCategorySpecJsonArray(list.get(0).get(0)));
    jsonObject.put("filters", getFilterJsonArray(list.get(1)));
    jsonObject.put("sort", getSortObject(list.get(0).get(1)));
    jsonObject.put("locale", "en_ID");
    jsonObject.put("size", 100);

    JSONObject searchParams = new JSONObject();
    searchParams.put("redisCacheEnabled", false);
    searchParams.put("lruCacheEnabled", false);
    searchParams.put("test", false);
    jsonObject.put("searchParams", searchParams);
    JSONArray paramsArray = new JSONArray();
    paramsArray.put(jsonObject);
    return paramsArray;

  }
  public  File getFile(String fileName) {
    InputStream path = this.getClass().getClassLoader().getResourceAsStream(fileName);
    File file = new File(path.toString());
    try {
      FileUtils.copyInputStreamToFile(path, file);
    }catch (Exception e){
      e.printStackTrace();
    }
    return file;
  }
}
