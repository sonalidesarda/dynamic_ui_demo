package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.StateObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StateRepository extends MongoRepository<StateObject, String> {

    StateObject findByCountry(String country);
}
