package com.example.dynamic_ui_demo.controller;

import com.example.dynamic_ui_demo.Repository.CityRepository;
import com.example.dynamic_ui_demo.Repository.CountryRepository;
import com.example.dynamic_ui_demo.Repository.StateRepository;
import com.example.dynamic_ui_demo.model.*;
import com.example.dynamic_ui_demo.service.AddressFormatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UIController {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    private static final Logger log = LoggerFactory.getLogger(UIController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UIController(ObjectMapper objectMapper, HttpServletRequest request, CountryRepository countryRepository,
                        CityRepository cityRepository, StateRepository stateRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }



    @RequestMapping("/addressformats")
    public String viewForms( Model page) {
//        List<AddressFormat> addressFormatList = addressFormatService.findAll();
//        page.addAttribute("addressformats", addressFormatList);
        return "AddressFormats.html";
    }

    @RequestMapping("/addressformat")
    public String viewForm(@RequestParam String country, Model page) {
//        List<StateFormat> stateFormatList = addressFormatService.findStateList();
//        page.addAttribute("states", stateFormatList);
//        List<String> statesList =  stateFormatList.stream().map(state -> state.getName()).collect(Collectors.toList());
//        System.out.println(statesList);
//        page.addAttribute("statesList",
//                statesList);

        return String.format("%s_Address.html",country);
    }

    @RequestMapping(path = "/addressformat",
            method = RequestMethod.POST)
    public String addProduct(
            @RequestParam String country,
            @RequestParam Integer pincode,
            @RequestParam String city,
            @RequestParam String username,
            Model model
    ) {
//        AddressFormat addressFormat = new AddressFormat();
//        addressFormat.setFullName(username);
//        addressFormatService.addAddressFormat(addressFormat);
//
//        List<AddressFormat> addressFormatList = addressFormatService.findAll();
//        model.addAttribute("addressformats", addressFormatList);

        return "AddressFormats.html";
    }


    @RequestMapping(value = "/getAddressFormat", method = RequestMethod.GET)
    ResponseEntity<List<String>> getAddressFormat( @RequestParam(value = "country", required = false) String country, Model page)
    {
        String accept = request.getHeader("Accept");
        List<String> countries = addressFormatService.getCountries();
        page.addAttribute("countries", countries);
        return new ResponseEntity<>(countries, HttpStatus.ACCEPTED);
    }

<<<<<<< Updated upstream
    @RequestMapping(value = "/getCountries",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Object>> getCountries() {

        List<Object> countries = countryRepository.findAll().get(0).getCountries();


        if (countries != null) {
            return new ResponseEntity<List<Object>>(countries, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
    }
=======
    @RequestMapping(value = "/getCities",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Object>> getCities( @RequestParam(value = "state", required = false) String state,
                                          @RequestParam(value = "country", required = false) String country)
    {
        List<Object> cityList = addressFormatService.getCities(state,country);
        if(cityList != null){
            return new ResponseEntity<List<Object>>(cityList, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
    }


>>>>>>> Stashed changes
    @RequestMapping(value = "/getStateOrProvince",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Object>> getStateOrProvince(
            @RequestParam(value = "country", required = false) String country) {

        List<Object> stateList = stateRepository.findStates(country).get(0).getState_or_province();

        if(stateList != null){
            return new ResponseEntity<List<Object>>(stateList, HttpStatus.ACCEPTED);
        } else
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getCities",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Object>> getCities( @RequestParam(value = "state", required = false) String state,
                                            @RequestParam(value = "country", required = false) String country) {
        List<Object> cityList;
        if(country == null){
            cityList = cityRepository.findByState(state);
        } else {
            cityList = cityRepository.findByCountry(country);
        }

        if(cityList != null){
            return new ResponseEntity<List<Object>>(cityList, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/searchForAddress",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<List<Address>> getAddress(
            @RequestBody Address inputAddress) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<List<Address>>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<List<Address>>(HttpStatus.NOT_IMPLEMENTED);
    }


}
