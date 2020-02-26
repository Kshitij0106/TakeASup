package com.TAS.takeasup.Model;

import java.util.List;

public class UsersPastOrders {

    private String OrderNo,Status,TotalOrder;
    private List<Order> pastOrdersList;

    public UsersPastOrders() {
    }

    public UsersPastOrders(String orderNo, String status, String totalOrder, List<Order> pastOrdersList) {
        OrderNo = orderNo;
        Status = status;
        TotalOrder = totalOrder;
        this.pastOrdersList = pastOrdersList;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
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
