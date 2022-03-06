package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StateRepository extends MongoRepository<State, String> {
    //List<State> findCountry(String Name);
    @Query(value="{ 'country' : ?0 }", fields = "{ 'country': 1, 'state_or_province' : 1 }")
    List<State> findStates(String country);
}
