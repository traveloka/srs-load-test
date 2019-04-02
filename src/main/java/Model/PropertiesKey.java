package main.java.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PropertiesKey {

  @SerializedName("foodRestrictions")
  private List<String> foodRestriction;
  @SerializedName("dishes")
  private List<String> dishes;
  @SerializedName("cuisines")
  private List<String> cuisines;
  @SerializedName("themes")
  private List<String> themes;

  public List<String> getThemes() {
    return themes;
  }

  public void setThemes(List<String> themes) {
    this.themes = themes;
  }

  public List<String> getFoodRestriction() {
    return foodRestriction;
  }

  public void setFoodRestriction(List<String> foodRestriction) {
    this.foodRestriction = foodRestriction;
  }

  public List<String> getDishes() {
    return dishes;
  }

  public void setDishes(List<String> dishes) {
    this.dishes = dishes;
  }

  public List<String> getCuisines() {
    return cuisines;
  }

  public void setCuisines(List<String> cuisines) {
    this.cuisines = cuisines;
  }
}
