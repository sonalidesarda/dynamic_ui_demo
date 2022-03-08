package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "address")
@Getter
@Setter
@ToString
public class Address {

    @Id
    public ObjectId _id;
    private String name;
    private String address_one;
    private String address_two;
    private String address_three;
    private String city;
    private String postal;
    private String country;
    @Field(value = "state_or_province")
    private String state;
}
