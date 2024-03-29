package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document(collection = "city")
@Getter
@Setter
@ToString

public class CityObject {

    @Id
    private ObjectId _id;

    private String country;

    @Field(value = "state_or_province")
    private String state;
    private ArrayList<City> cities;

    public CityObject(){}

    public CityObject(ObjectId _id, ArrayList<City> cities, String country, String state) {
        this._id = _id;
        this.country = country;
        this.state = state;
        this.cities = cities;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getCountry(){
        return country;
    }

    public String getState(){
        return state;
    }


    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
