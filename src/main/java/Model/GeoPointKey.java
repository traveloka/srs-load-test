package main.java.Model;

import com.google.gson.annotations.SerializedName;

public class GeoPointKey{
    @SerializedName("lat")
    Double latitude;
    @SerializedName("lon")
    Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
