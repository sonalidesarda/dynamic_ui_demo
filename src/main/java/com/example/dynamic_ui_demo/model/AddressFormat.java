package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "addressFormat")
@Getter
@Setter
@ToString
public class AddressFormat {
    @Id
    public Integer _id;

    private AddressFormatObject address_one;
    private AddressFormatObject address_two;
    private AddressFormatObject address_three;
    private AddressFormatObject city;
    private AddressFormatObject postal;
    private AddressFormatObject state_or_province;
    private String country;

    public AddressFormat(){}


    public AddressFormat(Integer _id, AddressFormatObject address_one, AddressFormatObject address_two, AddressFormatObject address_three, AddressFormatObject city, AddressFormatObject postal, AddressFormatObject state, String country) {
        this._id = _id;
        this.address_one = address_one;
        this.address_two = address_two;
        this.address_three = address_three;
        this.city = city;
        this.postal = postal;
        this.state_or_province = state;
        this.country = country;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public AddressFormatObject getAddress_one() {
        return address_one;
    }

    public void setAddress_one(AddressFormatObject address_one) {
        this.address_one = address_one;
    }

    public AddressFormatObject getAddress_two() {
        return address_two;
    }

    public void setAddress_two(AddressFormatObject address_two) {
        this.address_two = address_two;
    }

    public AddressFormatObject getAddress_three() {
        return address_three;
    }

    public void setAddress_three(AddressFormatObject address_three) {
        this.address_three = address_three;
    }

    public AddressFormatObject getCity() {
        return city;
    }

    public void setCity(AddressFormatObject city) {
        this.city = city;
    }

    public AddressFormatObject getPostal() {
        return postal;
    }

    public void setPostal(AddressFormatObject postal) {
        this.postal = postal;
    }

    public AddressFormatObject getState() {
        return state_or_province;
    }

    public void setState(AddressFormatObject state) {
        this.state_or_province = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
