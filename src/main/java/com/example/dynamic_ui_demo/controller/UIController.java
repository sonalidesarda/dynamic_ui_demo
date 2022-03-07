package com.example.dynamic_ui_demo.controller;

import com.example.dynamic_ui_demo.Repository.AddressFormatRepository;
import com.example.dynamic_ui_demo.Repository.CityRepository;
import com.example.dynamic_ui_demo.Repository.CountryRepository;
import com.example.dynamic_ui_demo.Repository.StateRepository;
import com.example.dynamic_ui_demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UIController {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final AddressFormatRepository addressFormatRespository;

    private static final Logger log = LoggerFactory.getLogger(UIController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UIController(ObjectMapper objectMapper, HttpServletRequest request, CountryRepository countryRepository,
                        CityRepository cityRepository, StateRepository stateRepository, AddressFormatRepository addressFormatRespository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.addressFormatRespository = addressFormatRespository;
    }

    @RequestMapping("/addressFormat")
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
    String getAddressFormat(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "state", required = false) String state,
                             Model page)
    {
        List<Country> countries = countryRepository.findAll().get(0).getCountries();
        List<String> countriesList = countries.stream().map(c -> c.getName())
                .collect(Collectors.toList());
        if(country != null)
        {
            countriesList.remove(country);
            StateObject stateObject = stateRepository.findByCountry(country);
            List<String> stateList = stateObject.getState_or_province().stream()
                    .map(s -> s.getName()).collect(Collectors.toList());

            page.addAttribute("states", stateList);
            page.addAttribute("country",country);

            List<CityObject> cityList;
            if(state != null)
            {
                if(stateList != null && stateList.contains(state))
                {
                    stateList.remove(state);
                }
                page.addAttribute("state",state);
                cityList = cityRepository.findByState(state);
            }
            else
            {
                cityList = cityRepository.findByCountry(country);
            }
            page.addAttribute("cities",cityList);
        }

        page.addAttribute("countries",countriesList);
        return "India_Address.html";
    }


    @RequestMapping(value = "/getAddressFormat1", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<AddressFormat> getAddressFormat1( @RequestParam(value = "country", required = true) String country) {


        AddressFormat addressFormat = (AddressFormat) addressFormatRespository.getAddressFormat(country).get(0);

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
        List<String> stateList = stateObject.getState_or_province().stream().map(s -> s.getName()).collect(Collectors.toList());
        if(stateList != null){
            return new ResponseEntity<>(stateList, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getCities",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<CityObject>> getCities( @RequestParam(value = "state", required = false) String state,
                                            @RequestParam(value = "country", required = false) String country) {
        List<CityObject> cityList;
        if(country == null){
            cityList = cityRepository.findByState(state);
        } else {
            cityList = cityRepository.findByCountry(country);
        }

        if(cityList != null){
            return new ResponseEntity<>(cityList, HttpStatus.ACCEPTED);
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