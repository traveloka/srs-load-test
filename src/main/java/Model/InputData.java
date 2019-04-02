package main.java.Model;

import java.util.List;

public class InputData {
    private Double popularity;
    private String productName;
    private String productId;
    private List<Geo> parentGeoList;

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Geo> getParentGeoList() {
        return parentGeoList;
    }

    public void setParentGeoList(List<Geo> parentGeoList) {
        this.parentGeoList = parentGeoList;
    }
}
