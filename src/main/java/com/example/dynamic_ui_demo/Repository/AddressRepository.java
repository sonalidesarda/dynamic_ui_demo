package com.example.dynamic_ui_demo.Repository;

import com.example.dynamic_ui_demo.model.Address;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {

    /*List<Address> findAddressByNameAndAddress_oneAndAddress_twoAndAddress_threeAndCityAndPostalAndStateAndCountry(@Optional String name,
                                                                                                                  String address_one,
                                                                                                                  String address_two,
                                                                                                                  String address_three,
                                                                                                                  String city,
                                                                                                                  String postal,
                                                                                                                  String state,
                                                                                                                  String country);
*/
//    AddressFormat findAddressFormatByCountry(String country);

}
