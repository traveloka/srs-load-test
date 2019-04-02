package main.java.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoSource {
    @SerializedName("g")
    private GeoKey geoKey;
    @SerializedName("fc")
    private List<String> facilities;
    @SerializedName("prp")
    private PropertiesKey propertiesKey;
    @SerializedName("tgs")
    private List<String> tags;
    @SerializedName("pSC")
    private List<String> subCategories;
    @SerializedName("pG")
    private List<ParentGeo> parentGeo;

    public List<ParentGeo> getParentGeo() {
        return parentGeo;
    }

    public void setParentGeo(List<ParentGeo> parentGeo) {
        this.parentGeo = parentGeo;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }

    public List<String> getTags() {
        return tags;
    }

    public PropertiesKey getPropertiesKey(){ return propertiesKey;}
    public List<String> getFacilities() {
        return facilities;
    }
    public GeoKey getGeoKey() {
        return geoKey;
    }

    public void setGeoKey(GeoKey geoKey) {
        this.geoKey = geoKey;
    }
}