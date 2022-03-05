package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.Repository.CountryRepository;
import com.example.dynamic_ui_demo.DynamicUiDemoApplication;
import com.example.dynamic_ui_demo.controller.UIController;
import com.example.dynamic_ui_demo.model.AddressFormat;
import com.example.dynamic_ui_demo.model.Country;
import com.example.dynamic_ui_demo.model.StateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressFormatServiceImpl implements AddressFormatService{
    private List<AddressFormat> addressFormatList = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(AddressFormatServiceImpl.class);
    @Autowired
    CountryRepository countryRepository;

    public AddressFormatServiceImpl(CountryRepository countryRepository)
    {
        this.countryRepository = countryRepository;
    }
    public void addAddressFormat(AddressFormat addressFormat) {
        addressFormatList.add(addressFormat);
    }

    @Override
    public List<AddressFormat> findAll() {

        return addressFormatList;
    }

    @Override
    public List<StateFormat> findStateList() {
        List<StateFormat> stateFormatList = null;
        // create Object Mapper
        ObjectMapper mapper = new ObjectMapper();
        // read JSON file and map/convert to java POJO
        try {
            ClassLoader classLoader = DynamicUiDemoApplication.class.getClassLoader();

            stateFormatList = mapper.readValue(
                    new File(classLoader.getResource("States.json").getFile()),
                    new TypeReference<List<StateFormat>>(){});

            stateFormatList.forEach(x -> System.out.println(x.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stateFormatList;
    }

    @Override
    public List<Object> getCountries()
    {
        log.info("AddressFormatServiceImpl :: getCountries called");
        List<Country> countries = countryRepository.findAll();
        log.info("AddressFormatServiceImpl :: getCountries result "+ countries);
        return  countries.get(0).getCountries();
    }
}
