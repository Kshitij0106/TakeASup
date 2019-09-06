package com.TAS.takeasup.Model;

public class RestaurantHome {

    String HomeName,HomePic;

    public RestaurantHome(String homeName, String homePic) {
        HomeName = homeName;
        HomePic = homePic;
    }

    public RestaurantHome() {

    }

    public String getHomeName() {
        return HomeName;
    }

    public void setHomeName(String homeName) {
        HomeName = homeName;
    }

    public String getHomePic() {
        return HomePic;
    }

    public void setHomePic(String homePic) {
        HomePic = homePic;
    }
}
