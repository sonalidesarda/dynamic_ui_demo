package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private String lastName;
    private String firstName;
    private String address1;
    private String address2;
    private String address3;
    private CityObject cityObject;

}
