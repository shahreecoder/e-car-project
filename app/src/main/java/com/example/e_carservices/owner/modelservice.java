package com.example.e_carservices.owner;

public class modelservice {
    String sid, sname, sprice, sdisp, simage;

    public modelservice() {
    }

    public modelservice(String sid, String sname, String sprice, String sdisp, String simage) {
        this.sid = sid;
        this.sname = sname;
        this.sprice = sprice;
        this.sdisp = sdisp;
        this.simage = simage;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getSdisp() {
        return sdisp;
    }

    public void setSdisp(String sdisp) {
        this.sdisp = sdisp;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }
}
