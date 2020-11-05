package fr.tokazio.postit;

public class Categorie {

    private String value;
    private String label;

    private Categorie() {
        //jackson
    }

    public Categorie(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public Categorie setValue(String value) {
        this.value = value;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Categorie setLabel(String label) {
        this.label = label;
        return this;
    }
}
