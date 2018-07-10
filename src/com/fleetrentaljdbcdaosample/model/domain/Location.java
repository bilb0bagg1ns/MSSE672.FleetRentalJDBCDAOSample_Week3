package com.fleetrentaljdbcdaosample.model.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Messages generated by MyEclipse - Hibernate Tools
 */
public class Location implements java.io.Serializable {

    // Fields    
    private Integer locationId;
    private String city;

    /**
     * Holds the cars that are available in this location As part of the
     * Hibernate mapping that there are one-to-many association between a
     * Location and a Car.
      *
     */
    private Set<Car> carSet = new HashSet<Car>();

    // Constructors
    /**
     * default constructor
     */
    public Location() {
    }

    public Location(String city) {
        this.city = city;
    }

    // Property accessors
    public Integer getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set getCarSet() {
        return carSet;
    }

    public void setCarSet(Set<Car> carsSet) {
        this.carSet = carsSet;
    }

    /**
     * Set the location of the car to this location and add car to the Set.
     *
     * @param car
     */
    public void addCar(Car car) {
        car.setLocation(this);
        carSet.add(car);
    }

    /**
     *
     * @return @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\nLocation[");
        buffer.append("\t\ncity = ").append(city);
        buffer.append("\t\nlocationId = ").append(locationId);
        for (Car c : carSet) {
            buffer.append(c);
        }
        buffer.append("\t\n]");

        return buffer.toString();
    }

}