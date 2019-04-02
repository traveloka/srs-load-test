package main.java.Model;

import com.google.gson.annotations.SerializedName;

public class ParentGeo {
  @SerializedName("id")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
