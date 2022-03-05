package com.example.dynamic_ui_demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Country")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Country {
    @Id
    private String id;
    private String Name;

}