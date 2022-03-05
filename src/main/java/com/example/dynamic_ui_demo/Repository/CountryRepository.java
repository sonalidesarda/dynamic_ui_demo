package com.example.dynamic_ui_demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.dynamic_ui_demo.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepository extends MongoRepository<Country, String> {
    //List<Country> findCountry(String Name);
}
