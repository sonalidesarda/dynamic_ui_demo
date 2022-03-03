package com.example.dynamic_ui_demo.service;

import com.example.dynamic_ui_demo.DynamicUiDemoApplication;
import com.example.dynamic_ui_demo.model.AddressFormat;
import com.example.dynamic_ui_demo.model.StateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressFormatServiceImpl implements AddressFormatService{
    private List<AddressFormat> addressFormatList = new ArrayList<>();

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

}
