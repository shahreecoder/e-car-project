package com.example.e_carservices;

public class clsConnection {
    String conn;

    public clsConnection() {
        this.conn = "https://ecar.shahreecoder.com/api/";
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn;
    }
}
