package com.TAS.takeasup.Model;

import java.util.List;

public class Request {
    private String TableNo;
    private String TotalOrder;
//    private String RestaurantCode;
    private List<Order> foods;

    public Request() {

    }

    public Request(String tableNo, String totalOrder, List<Order> foods){//,String restaurantCode) {
        TableNo = tableNo;
        TotalOrder = totalOrder;
        this.foods = foods;
//        RestaurantCode = restaurantCode;
    }

    public String getTableNo() {
        return TableNo;
    }

    public void setTableNo(String tableNo) {
        TableNo = tableNo;
    }

    public String getTotalOrder() {
        return TotalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        TotalOrder = totalOrder;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

//    public String getRestaurantCode() {
//        return RestaurantCode;
//    }
//
//    public void setRestaurantCode(String restaurantCode) {
//        RestaurantCode = restaurantCode;
//    }
}
