package com.example.dynamic_ui_demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "country")
@Getter
@Setter
@ToString
public class Country {
    @Id
    public ObjectId _id;
    public ArrayList<Object> countries;

    public Country(){}

    public Country(ObjectId _id, ArrayList<Object> countries) {
        this._id = _id;
        this.countries = countries;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public ArrayList<Object> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Object> countries) {
        this.countries = countries;
    }
}