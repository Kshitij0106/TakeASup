package com.TAS.takeasup.Model;

import java.util.List;

public class Request {
    private String TableNo;
    private String TotalOrder;
    private String Status;
    private String orderId;
    private String customerId;
    private List<Order> foods;

    public Request() {

    }

    public Request(String tableNo, String totalOrder, List<Order> foods,String status,String orderId,String customerId) {
        TableNo = tableNo;
        TotalOrder = totalOrder;
        this.foods = foods;
        Status = status;
        this.orderId = orderId;
        this.customerId = customerId;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
