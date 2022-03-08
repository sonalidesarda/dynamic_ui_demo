package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.Repository.CountryRepository;
import com.example.dynamic_ui_demo.Repository.CityRepository;
import com.example.dynamic_ui_demo.DynamicUiDemoApplication;
import com.example.dynamic_ui_demo.Repository.StateRepository;
import com.example.dynamic_ui_demo.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    MongoTemplate mongoTemplate;

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
    public List<Address> searchAddress(String firstName, String address_one,
                                       String address_two, String address_three,
                                       String city, String zipcode, String state,
                                       String country, String searchType){
        log.info("searchAddress service called");
        Query query = new Query();
        if(firstName != null)
            query.addCriteria(Criteria.where("name").regex(firstName,"i"));
        if(address_one != null)
            query.addCriteria(Criteria.where("address_one").regex(address_one,"i"));
        if(address_two != null)
            query.addCriteria(Criteria.where("address_two").regex(address_two,"i"));
        if(address_three != null)
            query.addCriteria(Criteria.where("address_three").regex(address_three,"i"));
        if(city != null)
            query.addCriteria(Criteria.where("city").regex(city,"i"));
        if(zipcode != null)
            query.addCriteria(Criteria.where("postal").regex(zipcode,"i"));
        if(state != null)
            query.addCriteria(Criteria.where("state").regex(state,"i"));
        if(searchType != null && searchType.equals("Within Country") && country != null)
            query.addCriteria(Criteria.where("country").regex(country,"i"));


        List<Address> addressList = mongoTemplate.find(query, Address.class);
        return addressList;
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
    public List<Country> getCountries()
    {
        log.info("AddressFormatServiceImpl :: getCountries called");
        List<CountryObject> countries = countryRepository.findAll();
        log.info("AddressFormatServiceImpl :: getCountries result "+ countries);
        return  countries.get(0).getCountries();
    }

    @Override
    public List<State> getStates(String country)
    {
        log.info("AddressFormatServiceImpl :: getCountries called");
        List<StateObject> states = stateRepository.findAll();
        log.info("Count***"+states.size());
        log.info("AddressFormatServiceImpl :: getCountries result "+ states);

        return states.get(0).getStates();
    }

    @Override
    public List<City> getCities(String state,String country)
    {
        List<CityObject> cities = cityRepository.findAll();
        for(CityObject c: cities){
            if(c.getCountry().equals(country) && c.getState().equals(state)){
                return c.getCities();
            }
        }
        return Collections.emptyList();
    }


}
