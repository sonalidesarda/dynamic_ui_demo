package com.example.dynamic_ui_demo.controller;

import com.example.dynamic_ui_demo.model.AddressFormat;
import com.example.dynamic_ui_demo.model.StateFormat;
import com.example.dynamic_ui_demo.service.AddressFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UIController {

    private final AddressFormatService addressFormatService;

    @Autowired
    public UIController(AddressFormatService addressFormatService)
    {
        this.addressFormatService = addressFormatService;
    }
    @RequestMapping("/addressformats")
    public String viewForms( Model page) {
        List<AddressFormat> addressFormatList = addressFormatService.findAll();
        page.addAttribute("addressformats", addressFormatList);
        return "AddressFormats.html";
    }

    @RequestMapping("/addressformat")
    public String viewForm(@RequestParam String country, Model page) {
        List<StateFormat> stateFormatList = addressFormatService.findStateList();
        page.addAttribute("states", stateFormatList);
        List<String> statesList =  stateFormatList.stream().map(state -> state.getName()).collect(Collectors.toList());
        System.out.println(statesList);
        page.addAttribute("statesList",
                statesList);

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
        AddressFormat addressFormat = new AddressFormat();
        addressFormat.setFullName(username);
        addressFormatService.addAddressFormat(addressFormat);

        List<AddressFormat> addressFormatList = addressFormatService.findAll();
        model.addAttribute("addressformats", addressFormatList);

        return "AddressFormats.html";
    }

    @RequestMapping(path = "/search",
            method = RequestMethod.POST)
    public String search(
            @ModelAttribute("addressFormat") AddressFormat addressFormat,
            Model model
    ) {

        if (addressFormat != null)
        {
            System.out.println("Address Format ::"+addressFormat.toString());
        }
        else
        {
            System.out.println("Address Format not present");
        }

        return "AddressFormats.html";
    }
}
