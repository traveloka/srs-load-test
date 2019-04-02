package main.java.Model;

public class Geo {
    private String geoName;
    private String geoType;
    private long geoId;
    private String latitide;
    private String longitude;

    public Geo(String geoName, String geoType, long geoId) {
        this.geoName = geoName;
        this.geoType = geoType;
        this.geoId = geoId;
    }

    public Geo(String geoName, String geoType, long geoId, String latitide, String longitude) {
        this.geoName = geoName;
        this.geoType = geoType;
        this.geoId = geoId;
        this.latitide = latitide;
        this.longitude = longitude;
    }

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public long getGeoId() {
        return geoId;
    }

    public void setGeoId(long geoId) {
        this.geoId = geoId;
    }

    public String getLatitide() {
        return latitide;
    }

    public void setLatitide(String latitide) {
        this.latitide = latitide;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
