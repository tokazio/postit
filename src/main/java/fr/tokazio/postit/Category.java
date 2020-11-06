package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    //These field are important as is
    //There are used by the quasar framework to be displayd/used in a select component
    private String value;
    private String label;

    @JsonCreator
    public Category(final @JsonProperty("value") String value, @JsonProperty("label") final String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    /*
    public Category setValue(final String value) {
        this.value = value;
        return this;
    }


     */
    public String getLabel() {
        return label;
    }
/*
    public Category setLabel(String label) {
        this.label = label;
        return this;
    }

 */
}
