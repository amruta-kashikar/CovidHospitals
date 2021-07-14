package com.example.covidhospitals.model;

public class ControlRoomModel {
    String cemail,pwd;

    public ControlRoomModel(String cemail){

    }
    public ControlRoomModel(String cemail, String pwd) {
        this.cemail = cemail;
        this.pwd = pwd;
    }

    public String getEmail() {
        return cemail;
    }

    public void setEmail(String cemail) {
        this.cemail = cemail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
