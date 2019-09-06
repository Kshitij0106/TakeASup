package com.TAS.takeasup.Model;

public class RestaurantList {

    private String RestPic,RestName,RestType,RestCostForTwo;

    public RestaurantList(String restPic, String restName, String restType, String restCostForTwo) {
        RestPic = restPic;
        RestName = restName;
        RestType = restType;
        RestCostForTwo = restCostForTwo;
    }

    public RestaurantList(){

    }

    public String getRestPic() {
        return RestPic;
    }

    public void setRestPic(String restPic) {
        RestPic = restPic;
    }

    public String getRestName() {
        return RestName;
    }

    public void setRestName(String restName) {
        RestName = restName;
    }

    public String getRestType() {
        return RestType;
    }

    public void setRestType(String restType) {
        RestType = restType;
    }

    public String getRestCostForTwo() {
        return RestCostForTwo;
    }

    public void setRestCostForTwo(String restCostForTwo) {
        RestCostForTwo = restCostForTwo;
    }
}
