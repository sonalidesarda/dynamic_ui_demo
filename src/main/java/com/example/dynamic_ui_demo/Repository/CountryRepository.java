package com.example.dynamic_ui_demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.dynamic_ui_demo.model.CountryObject;

public interface CountryRepository extends MongoRepository<CountryObject, String> {


}
