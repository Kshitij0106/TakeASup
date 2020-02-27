package com.TAS.takeasup.Model;

import java.util.List;

public class UsersPastOrders {

    private String OrderId,Status,TotalOrder;
    private List<Order> pastOrdersList;

    public UsersPastOrders() {
    }

    public UsersPastOrders(String orderId, String status, String totalOrder, List<Order> pastOrdersList) {
        OrderId = orderId;
        Status = status;
        TotalOrder = totalOrder;
        this.pastOrdersList = pastOrdersList;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalOrder() {
        return TotalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        TotalOrder = totalOrder;
    }

    public List<Order> getPastOrdersList() {
        return pastOrdersList;
    }

    public void setPastOrdersList(List<Order> pastOrdersList) {
        this.pastOrdersList = pastOrdersList;
    }
}
