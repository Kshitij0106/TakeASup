package com.TAS.takeasup.Model;

import java.util.ArrayList;

public class Categories {
    private String dishCategory;
    private ArrayList<MenuItems> Dishes;

    public Categories() {
    }

    public Categories(String dishCategoryName, ArrayList<MenuItems> dishes) {
        this.dishCategory = dishCategoryName;
        this.Dishes = dishes;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategoryName) {
        this.dishCategory = dishCategoryName;
    }

    public ArrayList<MenuItems> getDishes() {
        return Dishes;
    }

    public void setDishes(ArrayList<MenuItems> dishes) {
        this.Dishes = dishes;
    }
}
