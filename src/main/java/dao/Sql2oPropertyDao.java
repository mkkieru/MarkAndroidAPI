package dao;

import models.Property;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;
public class Sql2oPropertyDao implements propertyDao{
    private final Sql2o sql2o;
    public Sql2oPropertyDao(Sql2o sql2o){ this.sql2o = sql2o; }

    /*
    CREATE TABLE properties(
    id SERIAL PRIMARY KEY,
    type VARCHAR,
    location VARCHAR,
    description VARCHAR,
    value INT);*/
    @Override
    public void add(Property property) {
        String sql = "INSERT INTO properties (type,location,description,value,propertyImage) VALUES (:type,:location,:description,:value,:propertyImage)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .addParameter("type", property.getType())
                    .addParameter("location", property.getLocation())
                    .addParameter("description", property.getDescription())
                    .addParameter("value", property.getValue())
                    .addParameter("propertyImage", property.getPropertyImage())
                    .executeUpdate()
                    .getKey();
            property.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Property> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM properties;")
                    .executeAndFetch(Property.class);
        }
    }

    @Override
    public Property findById(int id) {
        return null;
    }

    @Override
    public List<Property> getAllPropertiesByValue(int value) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
