package main.java;
import main.java.parser.JSONFlattener;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * // TODO Comment
 */
public class SRSJmeterTestMain {
  private static String seachClient;


  public static void main(String[] args) throws IOException {
    String fileName =args[0];
    seachClient=args[1];
    List<Map<String, List<String>>> flatJson = JSONFlattener.parseJson(new JsonObjectBuilder().getFile(fileName), "UTF-8");
    CSVFileBuilder.generateCsvFile();
    for (int i = 0; i < flatJson.size(); i++) {
      if (flatJson.get(i).get("SearchClient").get(0).equalsIgnoreCase(seachClient)) {

        List<List<String>> filters = new ArrayList<List<String>>();
        List<List<String>> lists2 = new ArrayList<List<String>>();
        List<List<List<String>>> finalList = new ArrayList<>();
        List<String> sort = flatJson.get(i).get("sort");
        if (sort != null) {
          lists2.add(sort);
        }
        List<String> filter = flatJson.get(i).get("filter");
        if (filter != null) {
          for (int j = 1; j <= filter.size(); j++) {
            filters.addAll(combination(filter, j));
          }
        }
        finalList.add(filters);
        List<String> categorySpec = flatJson.get(i).get("categorySpec");
        if (categorySpec != null) {
          lists2.add(categorySpec);
        }
        finalList.add(generateCombination(lists2, 0));
        List<List<List<String>>> result = generateCombination2(finalList, 0);

        for (int l = 0; l < result.size(); l++) {
          JSONObject jsonObject = JsonObjectBuilder.getJsonObject(result.get(l), seachClient);
          String jsonText = jsonObject.toString();
          String jsonAlterText = jsonText.replace("\"$", "$");
          String finalText = jsonAlterText.replace("}\"", "}");
          new JmxFileBuilder().generateJmxFile(finalText, l);
        }
      }
    }
  }

  public static List<List<String>> combination(List<String> values, int size) {

    if (size == 0) {
      return Collections.singletonList(Collections.<String>emptyList());
    }
    if (values.isEmpty()) {
      return Collections.emptyList();
    }

    List<List<String>> combination = new LinkedList<List<String>>();

    String actual = values.iterator().next();

    List<String> subSet = new LinkedList<String>(values);
    subSet.remove(actual);

    List<List<String>> subSetCombination = combination(subSet, size - 1);

    for (List<String> set : subSetCombination) {
      List<String> newSet = new LinkedList<String>(set);
      newSet.add(0, actual);
      combination.add(newSet);
    }

    combination.addAll(combination(subSet, size));

    return combination;
  }


  private static List<List<String>> generateCombination(List<List<String>> input, int i) {
    if (i == input.size()) {
      List<List<String>> result = new ArrayList<List<String>>();
      result.add(new ArrayList<String>());
      return result;
    }

    List<List<String>> result = new ArrayList<List<String>>();
    List<List<String>> recursive = generateCombination(input, i + 1);
    for (int j = 0; j < input.get(i).size(); j++) {
      for (int k = 0; k < recursive.size(); k++) {
        List<String> newList = new ArrayList<String>();
        for (String string : recursive.get(k)) {
          newList.add(string);
        }
        newList.add(input.get(i).get(j));
        result.add(newList);
      }
    }
    return result;
  }

  private static List<List<List<String>>> generateCombination2(List<List<List<String>>> input, int i) {

    if (i == input.size()) {
      List<List<List<String>>> result = new ArrayList<>();
      result.add(new ArrayList<List<String>>());
      return result;
    }

    List<List<List<String>>> result = new ArrayList<>();
    List<List<List<String>>> recursive = generateCombination2(input, i + 1);

    for (int j = 0; j < input.get(i).size(); j++) {
      for (int k = 0; k < recursive.size(); k++) {
        List<List<String>> newList = new ArrayList<>();
        for (List<String> list : recursive.get(k)) {
          newList.add(list);
        }
        newList.add(input.get(i).get(j));
        result.add(newList);
      }
    }
    return result;
  }
}
