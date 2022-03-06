package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.Repository.CountryRepository;
import com.example.dynamic_ui_demo.Repository.CityRepository;
import com.example.dynamic_ui_demo.DynamicUiDemoApplication;
import com.example.dynamic_ui_demo.Repository.StateRepository;
import com.example.dynamic_ui_demo.controller.UIController;
import com.example.dynamic_ui_demo.model.AddressFormat;
import com.example.dynamic_ui_demo.model.Country;
import com.example.dynamic_ui_demo.model.City;
import com.example.dynamic_ui_demo.model.State;
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
import java.util.Collections;
import java.util.List;

@Service
public class AddressFormatServiceImpl implements AddressFormatService{
    private List<AddressFormat> addressFormatList = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(AddressFormatServiceImpl.class);
    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;
    @Autowired
    CityRepository cityRepository;

    public AddressFormatServiceImpl(CountryRepository countryRepository, StateRepository stateRepository,CityRepository cityRepository)
    {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
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

    @Override
    public List<Object> getStates(String country)
    {
        log.info("AddressFormatServiceImpl :: getCountries called");
        List<State> states = stateRepository.findAll();
        log.info("Count***"+states.size());
        log.info("AddressFormatServiceImpl :: getCountries result "+ states);
        for(State s: states){
            if(s.getCountry().equals(country)){
                return s.getStates();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Object> getCities(String state,String country)
    {
        List<City> cities = cityRepository.findAll();
        for(City c: cities){
            if(c.getCountry().equals(country) && c.getState().equals(state)){
                return c.getCities();
            }
        }
        return Collections.emptyList();
    }


}
