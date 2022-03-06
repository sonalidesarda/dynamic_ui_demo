package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.AddressFormat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AddressFormatRepository extends MongoRepository<AddressFormat, String> {
    @Query(value="{ 'country' : ?0 }", fields = "{ 'country': 1, 'address_one' : 1 , 'address_two' : 1," +
                                                "'address_three' : 1, 'city' : 1, 'state_or_province' : 1," +
                                                "'postal' : 1}")
    public List<Object> getAddressFormat(String country);
}
