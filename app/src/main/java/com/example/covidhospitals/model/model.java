package com.example.covidhospitals.model;

import android.util.Log;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class model {
    String name,age,gender,symptoms,time,phone,id;
    ArrayList<String> images;

    public model(){
        //Log.e("okE","name"+name+" age"+age+"gen "+ gender+"condition "+condition+" time" +time+" phone "+phone+" iid"+id);

    }


    public model(String name, String age, String gender, String symptoms, String time, String phone,ArrayList<String> images,String id) {
        //Log.e("oooooo","name"+name+" age"+age+"gen "+ gender+"condition "+condition+" time" +time+" phone "+phone+" iid"+id);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.symptoms = symptoms;
        this.time = time;
        this.phone = phone;
        this.images = new ArrayList<>(images);
        this.id = id;
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

    public String getPhone() {
        return phone;
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
}
