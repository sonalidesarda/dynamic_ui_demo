package com.example.dynamic_ui_demo.model;

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
public class CountryObject {
    @Id
    public ObjectId _id;
    public ArrayList<Country> countries;

    public CountryObject(){}

    public CountryObject(ObjectId _id, ArrayList<Country> countries) {
        this._id = _id;
        this.countries = countries;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}