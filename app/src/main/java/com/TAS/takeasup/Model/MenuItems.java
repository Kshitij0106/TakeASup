package com.TAS.takeasup.Model;

public class MenuItems {
//    private String menuId;
    private String name;
    private String price;
    private String type;
    private int No=0;
//    private String category;

    public MenuItems(String name, String price, String type){//,String category,String menuId) {
//        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.type = type;
//        this.category = category;
    }

    public MenuItems() {

    }
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getMenuId() {
//        return menuId;
//    }
//
//    public void setMenuId(String menuId) {
//        this.menuId = menuId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
