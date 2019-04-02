package main.java.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoKey{
    @SerializedName("gP")
    private GeoPointKey geoPointKey;
    @SerializedName("id")
    private String id;
    @SerializedName("gT")
    private List<String> geoType;

    public List<String> getGeoType() {
        return geoType;
    }

    public void setGeoType(List<String> geoType) {
        this.geoType = geoType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoPointKey getGeoPointKey() {
        return geoPointKey;
    }

    public void setGeoPointKey(GeoPointKey geoPointKey) {
        this.geoPointKey = geoPointKey;
    }
}