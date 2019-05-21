package net.physiodelic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by joris on 22/04/17.
 * Simple pojo to hold address information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    private String streetOne;
    private String streetTwo;
    private String stateProvince;
    private String city;
    private String country;
    private String postalCode;
}

// That's All Folks !!
