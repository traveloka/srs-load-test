package main.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.Model.GeoInputData;
import main.java.Model.GeoPointKey;
import main.java.Model.ParentGeo;
import main.java.Model.PropertiesKey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CSVFileBuilder {
  private static String file_path = "./JMXFiles/CSVFile.csv";

  public static void generateCsvFile() {
    JsonObjectBuilder j=new JsonObjectBuilder();
    List<GeoInputData> inputDataList1 = new ArrayList<>();
    List<GeoInputData> inputDataList2 = new ArrayList<>();
    List<GeoInputData> inputDataList3 = new ArrayList<>();
    List<GeoInputData> inputDataList4 = new ArrayList<>();
    List<GeoInputData> inputDataList5 = new ArrayList<>();
    List<GeoInputData> inputDataList6 = new ArrayList<>();
    Gson gson = new Gson();
    try {
      Type listType = new TypeToken<List<GeoInputData>>() {
      }.getType();
      JsonObjectBuilder jsonObjectBuilder=new JsonObjectBuilder();
      inputDataList1 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("restaurants_geo.json").getName()), listType);
      inputDataList2 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("res_geo.json").getName()), listType);
      inputDataList3 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("tags.json").getName()), listType);
      inputDataList4 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("AccomData.json").getName()), listType);
      inputDataList5 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("theme_data.json").getName()), listType);
      inputDataList6 = gson.fromJson(new FileReader(jsonObjectBuilder.getFile("gId_data.json").getName()), listType);
      List<GeoPointKey> geoPointKeyList = new ArrayList<>();
      List<List<String>> facilitiesList = new ArrayList<>();
      List<List<String>> tagsList = new ArrayList<>();
      List<List<String>> pSCList = new ArrayList<>();
      List<PropertiesKey> propertiesKeyList = new ArrayList<>();
      List<List<String>> themesList = new ArrayList<>();
      List<String> gIdList = new ArrayList<>();


      int null_point = 0;
      for (GeoInputData geoInputData : inputDataList1) {
        try {
          List<String> fc = geoInputData.getGeoSource().getFacilities();
          PropertiesKey propertiesKey = geoInputData.getGeoSource().getPropertiesKey();
          propertiesKeyList.add(propertiesKey);
          facilitiesList.add(fc);
        } catch (Exception e) {
          null_point++;
        }
      }
      for (GeoInputData geoInputData : inputDataList2) {
        try {
          GeoPointKey geoPointKey = geoInputData.getGeoSource().getGeoKey().getGeoPointKey();
          geoPointKeyList.add(geoPointKey);
        } catch (Exception e) {
          null_point++;
        }
      }
      for (GeoInputData geoInputData : inputDataList3) {
        try {
          List<String> tgs = geoInputData.getGeoSource().getTags();
          tagsList.add(tgs);
        } catch (Exception e) {
          null_point++;
        }
      }
      for (GeoInputData geoInputData : inputDataList4) {
        try {
          List<String> pSC = geoInputData.getGeoSource().getSubCategories();
          pSCList.add(pSC);
        } catch (Exception e) {
          null_point++;
        }
      }
      for (GeoInputData geoInputData : inputDataList5) {
        try {
          List<String> themes_value = geoInputData.getGeoSource().getPropertiesKey().getThemes();
          themesList.add(themes_value);
        } catch (Exception e) {
          null_point++;
        }
      }
      for (GeoInputData geoInputData : inputDataList6) {
        try {

          List<ParentGeo> pGList = geoInputData.getGeoSource().getParentGeo();
          for (ParentGeo pG : pGList) {
            String gId = pG.getId();
            gIdList.add(gId);
          }
        } catch (Exception e) {
          null_point++;
        }
      }


      System.out.println("Null Points:" + null_point);
      createCsv(facilitiesList, propertiesKeyList, geoPointKeyList, tagsList, pSCList, themesList, gIdList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createCsv(List<List<String>> facilities, List<PropertiesKey> propertiesKeyList, List<GeoPointKey> geoPointKeyList, List<List<String>> tagsList, List<List<String>> pSCList, List<List<String>> themes, List<String> gIdList) {
    StringBuilder sb = new StringBuilder();
    Integer bound = 5;
    List<String> gTypeList = new ArrayList<String>(Arrays.asList("CITY", "AREA", "DISTRICT", "LANDMARK", "REGION", "LOCALITY", "COUNTRY", "WORLD", "CONTINENT"));
   // sb = sb.append("geoId" + ":" + "duration_min" + ":" + "duration_max" + ":" + "discount_min" + ":" + "discount_max" + ":" + "rating_min" + ":" + "rating_max" + ":" + "gType" + ":" + "lat" + ":" + "lon" + ":" + "trending_bool" + ":" + "specialOffers_bool" + ":" + "instantVoucher_bool" + ":" + "open24Hours_bool" + ":" + "theme_values" + ":" + "active_bool" + ":" + "subCategories_values" + ":" + "tags" + ":" + "facilities" + ":" + "foodRestriction" + ":" + "cuisines" + ":" + "dishes" + "\n");
    int n = Math.min(Math.min(facilities.size(), geoPointKeyList.size()), Math.min(Math.min(tagsList.size(), pSCList.size()), themes.size()));
    for (int i = 0; i < n; i++) {
      PropertiesKey propertiesKey = propertiesKeyList.get(i);
      boolean trending_bool = ThreadLocalRandom.current().nextBoolean();
      boolean specialOffers_bool = ThreadLocalRandom.current().nextBoolean();
      boolean instantVoucher_bool = ThreadLocalRandom.current().nextBoolean();
      boolean active_bool = ThreadLocalRandom.current().nextBoolean();
      boolean open24Hours_bool = ThreadLocalRandom.current().nextBoolean();
      List<String> gType = new ArrayList<>(Arrays.asList(gTypeList.get(ThreadLocalRandom.current().nextInt(gTypeList.size()) % bound)));
      Integer duration_min = ThreadLocalRandom.current().nextInt(0, 10080);
      Integer duration_max = ThreadLocalRandom.current().nextInt(duration_min, 10081);
      Integer discount_min = ThreadLocalRandom.current().nextInt(0, 100);
      Integer discount_max = ThreadLocalRandom.current().nextInt(discount_min, 101);
      Integer rating_min = ThreadLocalRandom.current().nextInt(0, 5);
      Integer rating_max = ThreadLocalRandom.current().nextInt(rating_min, 6);
      String gT = new groovy.json.JsonBuilder(gType).toString();
      String theme = new groovy.json.JsonBuilder(themes.get(i)).toString();
      String pSC = new groovy.json.JsonBuilder(pSCList.get(i)).toString();
      String tags = new groovy.json.JsonBuilder(tagsList.get(i)).toString();
      String facility = new groovy.json.JsonBuilder(facilities.get(i)).toString();
      String foodRestriction = new groovy.json.JsonBuilder(propertiesKey.getFoodRestriction()).toString();
      String cuisines = new groovy.json.JsonBuilder(propertiesKey.getCuisines()).toString();
      String dishes = new groovy.json.JsonBuilder(propertiesKey.getDishes()).toString();


      sb.append(gIdList.get(i) + ":" + duration_min + ":" + duration_max + ":" + discount_min + ":" + discount_max + ":" + rating_min + ":" + rating_max + ":" + gT + ":" + geoPointKeyList.get(i).getLatitude() + ":" + geoPointKeyList.get(i).getLongitude() + ":" + trending_bool + ":" + specialOffers_bool + ":" + instantVoucher_bool + ":" + open24Hours_bool + ":" + theme + ":" + active_bool + ":" + pSC + ":" + tags + ":" + facility + ":" + foodRestriction + ":" + cuisines + ":" + dishes + "\n");
    }
    writeUsingOutputStream(sb.toString());
  }

  private static void writeUsingOutputStream(String data) {
    OutputStream os = null;
    try {
      File outputFile = new File(file_path);
      os = new FileOutputStream(outputFile);
      os.write(data.getBytes(), 0, data.length());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        os.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
