package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {
    public List<City> findByStateAndCountry(String state, String country);
}

