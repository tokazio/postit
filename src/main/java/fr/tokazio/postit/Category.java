package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    public String getLabel() {
        return label;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return value.equals(category.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + "(" + label + ")";
    }
}
