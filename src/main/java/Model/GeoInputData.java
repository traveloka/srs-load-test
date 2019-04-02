package main.java.Model;

import com.google.gson.annotations.SerializedName;

public class GeoInputData {
    @SerializedName("_index")
    private String indexName;
    @SerializedName("_type")
    private String type;
    @SerializedName("_id")
    private String productId;
    @SerializedName("_source")
    private GeoSource geoSource;

    public GeoSource getGeoSource() {
        return geoSource;
    }

    public void setGeoSource(GeoSource geoSource) {
        this.geoSource = geoSource;
    }
}

