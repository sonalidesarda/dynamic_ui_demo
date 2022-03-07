package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.AddressFormat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AddressFormatRepository extends MongoRepository<AddressFormat, String> {

    AddressFormat findAddressFormatByCountry(String country);
}
