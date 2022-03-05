package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.model.AddressFormat;
import com.example.dynamic_ui_demo.model.Country;
import com.example.dynamic_ui_demo.model.StateFormat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressFormatService {
    public List<AddressFormat> findAll();
    public void addAddressFormat(AddressFormat addressFormat);
    public List<StateFormat> findStateList();

    List<Object> getCountries();
}
