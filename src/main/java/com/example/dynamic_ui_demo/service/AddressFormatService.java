package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressFormatService {
    public List<AddressFormat> findAll();
    public void addAddressFormat(AddressFormat addressFormat);
    public List<StateFormat> findStateList();

    List<Country> getCountries();
    List<State> getStates(String country);
    List<City> getCities(String state, String country);
}
