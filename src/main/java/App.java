import com.google.gson.Gson;
import dao.Sql2oPropertyDao;
import models.Property;
import org.sql2o.Sql2o;
import java.sql.Connection;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Sql2oPropertyDao propertyDao;
        Connection conn;
        Gson gson = new Gson();


        //String connectionString = "jdbc:postgresql://localhost:5432/properties"; //
        //Sql2o sql2o = new Sql2o(connectionString, "damark", "password");

        //URI   postgres://apsutlkdxtcscd:bf1553539470d83ed123c17698c8486dad106a52ef410caae786ca559a4b9388@ec2-44-198-100-81.compute-1.amazonaws.com:5432/d9dk7m476aeruh

        String connectionString = "jdbc:postgresql://ec2-44-198-100-81.compute-1.amazonaws.com:5432/d9dk7m476aeruh"; //
        Sql2o sql2o = new Sql2o(connectionString, "apsutlkdxtcscd", "bf1553539470d83ed123c17698c8486dad106a52ef410caae786ca559a4b9388");

        propertyDao = new Sql2oPropertyDao(sql2o);

        get("/properties", "application/json", (req, res) -> {
            System.out.println(propertyDao.getAll());
            if(propertyDao.getAll().size() > 0){
                return gson.toJson(propertyDao.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no properties are currently listed in the database.\"}";
            }
        });
        //CREATE NEW PROPERTY
        post("/properties/new", "application/json", (req, res) -> {
            Property property = gson.fromJson(req.body(), Property.class);
            propertyDao.add(property);
            res.status(201);
            return gson.toJson(property);
        });

    }
}
