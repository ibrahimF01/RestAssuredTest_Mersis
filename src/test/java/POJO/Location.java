package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Location {
    private String postcode;
    private String country;
    private String countryabbrevation;
    private ArrayList<Place> places;

    public String getPostcode() {
        return postcode;
    }
    @JsonProperty("post code")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryabbrevation() {
        return countryabbrevation;
    }
    @JsonProperty("country abbreviation")
    public void setCountryabbrevation(String countryabbrevation) {
        this.countryabbrevation = countryabbrevation;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Location{" +
                "postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", countryabbrevation='" + countryabbrevation + '\'' +
                ", places=" + places +
                '}';
    }
}
