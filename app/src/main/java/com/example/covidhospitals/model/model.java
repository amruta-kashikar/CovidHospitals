package com.example.covidhospitals.model;

import android.util.Log;

import com.example.covidhospitals.MyUtils;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.Random;

public class model {
    String name,age,gender,symptoms,time,phone,id,genId,bedType;
    ArrayList<String> images;

    public model(){
    }


    public model(String name, String age, String gender, String symptoms, String time, String phone,ArrayList<String> images,String id,String genId,String bedType) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.symptoms = symptoms;
        this.time = time;
        this.phone = phone;
        this.images = new ArrayList<>(images);
        this.id = id;
        this.genId = genId;
        this.bedType = bedType;
    }
    public void  toStringOverload(){
        String s="name :"+this.name+" age:"+this.age+" gender:"+this.gender+" symptoms:"+this.symptoms+" time:"+this.time+" phone:"+this.phone+" id:"+this.id+" genid:"+this.genId;
        Log.d("MODAL", "modelObject is "+s);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setGenId(String id){
        genId=id;
    }
    public String getPhone() {
        return phone;
    }
    public String getGenId(){
        return genId;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = new ArrayList<>(images);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    /*    public static String generateCode(int i) {
        final String characters = "abcdefghijklmnopqrstuwxyz1234567890";
        StringBuilder result = new StringBuilder();
        while(i>0)
        {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }
        return result.toString();

    }*/
}
