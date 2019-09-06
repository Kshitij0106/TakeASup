package com.TAS.takeasup.Model;

public class FavouritesRestaurantsList {

    private String RName,RType,RCost;

    public FavouritesRestaurantsList(String RName, String RType, String RCost) {
        this.RName = RName;
        this.RType = RType;
        this.RCost = RCost;
    }

    public FavouritesRestaurantsList() {

    }

    public String getRName() {
        return RName;
    }

    public void setRName(String RName) {
        this.RName = RName;
    }

    public String getRType() {
        return RType;
    }

    public void setRType(String RType) {
        this.RType = RType;
    }

    public String getRCost() {
        return RCost;
    }

    public void setRCost(String RCost) {
        this.RCost = RCost;
    }
}
