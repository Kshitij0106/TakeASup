package com.TAS.takeasup.Model;

public class Order {

    private String DishName,DishPrice,DishQuantity;
    private int ID;

    public Order() {

    }

    public Order(String dishName, String dishPrice, String dishQuantity, int Id) {
        DishName = dishName;
        DishPrice = dishPrice;
        DishQuantity = dishQuantity;
        ID = Id;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getDishQuantity() {
        return DishQuantity;
    }

    public void setDishQuantity(String dishQuantity) {
        DishQuantity = dishQuantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
