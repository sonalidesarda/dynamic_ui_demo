package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {
    //public List<City> findByStateAndCountry(String state, String country);

    @Query(value="{ 'state_or_province' : ?0 }", fields = "{ 'country': 1, 'state_or_province' : 1 , 'cities' : 1}")
    public List<Object> findByState(String state);

    @Query(value="{ 'country' : ?0 }", fields = "{ 'country': 1, 'state_or_province' : 1 , 'cities' : 1}")
    public List<Object> findByCountry(String country);

}

