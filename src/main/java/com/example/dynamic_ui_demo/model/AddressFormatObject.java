package com.example.dynamic_ui_demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressFormatObject {
    String label_content;
    Boolean display;

    public AddressFormatObject(){}

    public AddressFormatObject(String label, Boolean display) {
        label_content = label;
        this.display = display;
    }

    public String getLabel_content() {
        return label_content;
    }

    public void setLabel_content(String label_content) {
        label_content = label_content;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}
