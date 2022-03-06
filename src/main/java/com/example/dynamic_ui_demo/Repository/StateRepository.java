package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateRepository extends MongoRepository<State, String> {
    //List<State> findCountry(String Name);
}
