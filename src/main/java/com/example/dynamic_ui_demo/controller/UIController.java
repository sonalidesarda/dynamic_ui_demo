package com.example.dynamic_ui_demo.controller;

import com.example.dynamic_ui_demo.Repository.*;
import com.example.dynamic_ui_demo.model.*;
import com.example.dynamic_ui_demo.service.AddressFormatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UIController {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final AddressFormatRepository addressFormatRespository;
    private final AddressRepository addressRepository;

    @Autowired
    AddressFormatService addressFormatService;
    private static final Logger log = LoggerFactory.getLogger(UIController.class);


    @org.springframework.beans.factory.annotation.Autowired
    public UIController( CountryRepository countryRepository,
                        CityRepository cityRepository, StateRepository stateRepository, AddressFormatRepository addressFormatRepository
            , AddressRepository addressRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.addressFormatRespository = addressFormatRepository;
        this.addressRepository = addressRepository;
    }

    @RequestMapping(value = "/getAddressFormat", method = RequestMethod.GET)
    String getAddressFormat(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "state", required = false) String state,
                             Model page)
    {
        List<Country> countries = countryRepository.findAll().get(0).getCountries();
        List<String> countriesList = countries.stream().map(c -> c.getName())
                .collect(Collectors.toList());
        AddressFormat addressFormat ;
        if(country != null) {
            addressFormat = addressFormatRespository.findAddressFormatByCountry(country);
        }
        else
        {
            addressFormat = addressFormatRespository.findAddressFormatByCountry("STANDARD");
        }
        if(addressFormat != null)
        {
            if(addressFormat.getAddress_one().getDisplay())
            {
                page.addAttribute("address_one_label",addressFormat.getAddress_one().getLabel_content());
            }
            if(addressFormat.getAddress_two().getDisplay())
            {
                page.addAttribute("address_two_label",addressFormat.getAddress_two().getLabel_content());
            }
            if(addressFormat.getAddress_three().getDisplay())
            {
                page.addAttribute("address_three_label",addressFormat.getAddress_three().getLabel_content());
            }
            if(addressFormat.getCity().getDisplay())
            {
                page.addAttribute("city_label",addressFormat.getCity().getLabel_content());
            }
            if(addressFormat.getPostal().getDisplay())
            {
                page.addAttribute("postal_label",addressFormat.getPostal().getLabel_content());
            }
            if(addressFormat.getState().getDisplay())
            {
                page.addAttribute("state_label",addressFormat.getState().getLabel_content());
            }

        }
        if(country != null) {


            countriesList.remove(country);
            StateObject stateObject = stateRepository.findByCountry(country);
            List<String> stateList = null;
            if(stateObject != null && stateObject.getStates() != null) {
                stateList = stateObject.getStates().stream()
                        .map(s -> s.getName()).collect(Collectors.toList());
            }
            if(stateList != null)
                page.addAttribute("states", stateList);
            page.addAttribute("country", country);

            CityObject cityObject = null;
            List<CityObject> cityObjects = null;
            List<String> cityList = null;
            if (state != null) {
                if (stateList != null && stateList.contains(state)) {
                    stateList.remove(state);
                }
                page.addAttribute("state", state);
                cityObject = cityRepository.findByCountryAndState(country, state);

            } else if(addressFormat != null && addressFormat.getState() != null && !addressFormat.getState().getDisplay()){
                cityObjects = cityRepository.findByCountry(country);
            }

            if (cityObject != null && cityObject.getCities() != null) {
                cityList = cityObject.getCities().stream().map(c -> c.getName()).collect(Collectors.toList());
            }
            if (cityObjects != null) {

                cityList = cityObjects.stream().filter(c -> c.getCities() != null)
                        .flatMap(cityObject1 -> cityObject1.getCities().stream())
                        .map(city ->
                                city.getName()).collect(Collectors.toList());
            }
            if(cityList != null)
            {
                Collections.sort(cityList);
                page.addAttribute("cities", cityList);
            }
        }


        page.addAttribute("countries",countriesList);
        return "Address_Search_Form.html";
    }


    @RequestMapping(value = "/getAddressFormat1", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<AddressFormat> getAddressFormat1( @RequestParam(value = "country", required = true) String country){

        AddressFormat addressFormat = addressFormatRespository.findAddressFormatByCountry(country);


        if (addressFormat != null) {
            return new ResponseEntity<AddressFormat>(addressFormat, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<AddressFormat>(new AddressFormat(), HttpStatus.ACCEPTED);
        }
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<AddressFormat>(objectMapper.readValue("{  \"bytes\": [],  \"empty\": true}", AddressFormat.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<AddressFormat>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<AddressFormat>(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(value = "/getStateOrProvince",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<String>> getStateOrProvince(
            @RequestParam(value = "country", required = true) String country) {

        StateObject stateObject = stateRepository.findByCountry(country);
        List<String> stateList = stateObject.getStates().stream().map(s -> s.getName()).collect(Collectors.toList());
        if(stateList != null){
            return new ResponseEntity<>(stateList, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getCities",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<String>> getCities( @RequestParam(value = "state", required = false) String state,
                                            @RequestParam(value = "country", required = true) String country) {
        List<String> cityList = null;
        CityObject cityObject = null;
        List<CityObject> cityObjects = null;
        if(state == null){
            cityObjects = cityRepository.findByCountry(country);
        } else {
            cityObject = cityRepository.findByCountryAndState(country, state);
        }
        if (cityObjects != null)
        {

                cityList = cityObjects.stream().filter(c -> c.getCities() != null)
                        .flatMap(cityObject1 -> cityObject1.getCities().stream())
                        .map(city ->
                                city.getName()).collect(Collectors.toList());
        }

        if(cityObject != null && cityObject.getCities() != null)
        {
            cityList = cityObject.getCities().stream().map(c -> c.getName()).collect(Collectors.toList());
            Collections.sort(cityList);
        }
        if(cityList != null)
        {
             return new ResponseEntity<>(cityList, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/searchForAddress",
            method = RequestMethod.POST)
    ResponseEntity<List<Address>> getAddress(
            @RequestPart(required = false) String firstName,
            @RequestPart(required = false) String country,
            @RequestPart(required = false) String zipcode,
            @RequestPart(required = false) String address_one,
            @RequestPart(required = false) String address_two,
            @RequestPart(required = false) String address_three,
            @RequestPart(required = false) String state,
            @RequestPart(required = false) String city,
            @RequestPart(required = false) String searchType) {

        List<Address> addressList =  addressFormatService.searchAddress(firstName,address_one,address_two,address_three,city,zipcode,state,country,searchType);
        if(addressList != null)
            return new ResponseEntity<>(addressList,HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}