package com.example.e_carservices.customer;

public class OrderModel {
    String orderid;
    String orderAddress;
    String payment_type;
    String total_ammount;
    String order_status;
    String order_date;
    String order_phn;

    public OrderModel(String orderid, String orderAddress, String payment_type, String total_ammount, String order_status, String order_date, String order_phn) {
        this.orderid = orderid;
        this.orderAddress = orderAddress;
        this.payment_type = payment_type;
        this.total_ammount = total_ammount;
        this.order_status = order_status;
        this.order_date = order_date;
        this.order_phn = order_phn;
    }

    public OrderModel() {

    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_ammount() {
        return total_ammount;
    }

    public void setTotal_ammount(String total_ammount) {
        this.total_ammount = total_ammount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_phn() {
        return order_phn;
    }

    public void setOrder_phn(String order_phn) {
        this.order_phn = order_phn;
    }


}
