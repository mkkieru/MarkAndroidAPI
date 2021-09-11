package dao;

import models.Property;

import java.util.List;
import java.util.Properties;

public interface propertyDao {
    //create
    void add(Property property);
    //read
    List<Property> getAll();
    Property findById(int id);
    List<Property>  getAllPropertiesByValue(int value);

    //update
    //void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    //delete
    void deleteById(int id);
    void clearAll();
}