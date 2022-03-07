package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.CityObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CityRepository extends MongoRepository<CityObject, String> {

    public CityObject findByCountryAndState( String country, String state);

    public List<CityObject> findByCountry(String country);

}

