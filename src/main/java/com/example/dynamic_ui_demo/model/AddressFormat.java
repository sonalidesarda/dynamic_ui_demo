package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressFormat {
    private String fullName;
    private Integer pincode;
    private AddressFormatObject address1;
    private AddressFormatObject address2;
    private AddressFormatObject address3;
    private AddressFormatObject city;
    private AddressFormatObject postal;
    private AddressFormatObject state;
    private AddressFormatObject country;

}
