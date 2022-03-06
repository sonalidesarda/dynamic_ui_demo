package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "stateOrProvince")
@Getter
@Setter
@ToString

public class StateObject {

    @Id
    private ObjectId _id;

    private String country;
    private ArrayList<State> state_or_province;

    public StateObject(){}

    public StateObject(ObjectId _id, ArrayList<State> states, String country) {
        this._id = _id;
        this.country = country;
        this.state_or_province = states;
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


    public ArrayList<State> getStates() {
        return state_or_province;
    }

    public void setStates(ArrayList<State> states) {
        this.state_or_province = states;
    }
}
