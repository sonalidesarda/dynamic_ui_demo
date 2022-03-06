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

public class State {

    @Id
    private ObjectId _id;

    private String country;
    private ArrayList<Object> state_or_province;

    public State(){}

    public State(ObjectId _id, ArrayList<Object> states, String country) {
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


    public ArrayList<Object> getStates() {
        return state_or_province;
    }

    public void setStates(ArrayList<Object> states) {
        this.state_or_province = states;
    }
}
