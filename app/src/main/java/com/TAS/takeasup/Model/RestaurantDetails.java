package com.TAS.takeasup.Model;

public class RestaurantDetails {

    String RestaurantFacebook,RestaurantInstagram,RestaurantPhoneNo,RestaurantWebsite,OpeningHours,
            RestaurantAddress,RestaurantCost, RestaurantCuisines,RestaurantSpeciality,RestaurantType;

    public RestaurantDetails(String restaurantFacebook, String restaurantInstagram, String restaurantPhoneNo,
                             String restaurantWebsite, String openingHours, String restaurantAddress,
                             String restaurantCost, String restaurantCuisines, String restaurantSpeciality,String restaurantType) {
        RestaurantFacebook = restaurantFacebook;
        RestaurantInstagram = restaurantInstagram;
        RestaurantPhoneNo = restaurantPhoneNo;
        RestaurantWebsite = restaurantWebsite;
        OpeningHours = openingHours;
        RestaurantAddress = restaurantAddress;
        RestaurantCost = restaurantCost;
        RestaurantCuisines = restaurantCuisines;
        RestaurantSpeciality = restaurantSpeciality;
        RestaurantType = restaurantType;
    }

    public RestaurantDetails() {

    }

    public String getRestaurantFacebook() {
        return RestaurantFacebook;
    }

    public void setRestaurantFacebook(String restaurantFacebook) {
        RestaurantFacebook = restaurantFacebook;
    }

    public String getRestaurantInstagram() {
        return RestaurantInstagram;
    }

    public void setRestaurantInstagram(String restaurantInstagram) {
        RestaurantInstagram = restaurantInstagram;
    }

    public String getRestaurantPhoneNo() {
        return RestaurantPhoneNo;
    }

    public void setRestaurantPhoneNo(String restaurantPhoneNo) {
        RestaurantPhoneNo = restaurantPhoneNo;
    }

    public String getRestaurantWebsite() {
        return RestaurantWebsite;
    }

    public void setRestaurantWebsite(String restaurantWebsite) {
        RestaurantWebsite = restaurantWebsite;
    }

    public String getOpeningHours() {
        return OpeningHours;
    }

    public void setOpeningHours(String openingHours) {
        OpeningHours = openingHours;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public String getRestaurantCost() {
        return RestaurantCost;
    }

    public void setRestaurantCost(String restaurantCost) {
        RestaurantCost = restaurantCost;
    }

    public String getRestaurantCuisines() {
        return RestaurantCuisines;
    }

    public void setRestaurantCuisines(String restaurantCuisines) {
        RestaurantCuisines = restaurantCuisines;
    }

    public String getRestaurantSpeciality() {
        return RestaurantSpeciality;
    }

    public void setRestaurantSpeciality(String restaurantSpeciality) {
        RestaurantSpeciality = restaurantSpeciality;
    }

    public String getRestaurantType() {
        return RestaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        RestaurantType = restaurantType;
    }
}
