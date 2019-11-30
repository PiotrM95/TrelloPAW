package server.TestRecive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import server.TestRecive.Person;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/people")
public class PersonController {


    //w cmd wklejasz ten kod:
    // curl -d {\"firstName\":\"value\"} -H "Content-Type: application/json" -X POST "http://localhost:8080/people"
    //  wiecej info masz w tym linku co ci wysłałem punkt  +/- 6.12
    Map<String, Person> inMemoryDatastore = new ConcurrentHashMap<>();

    @Post
    public HttpResponse<Person> save(@Body Person person) {
        inMemoryDatastore.put(person.getFirstName(), person);
        return HttpResponse.created(person);
    }

    //-----------------------------------------------
    //Pierwszy POST nizej dziala zaraz pod commentem
    //-----------------------------------------------
    /*@Post
    public HttpResponse<String> save(@Body String person) {
        //inMemoryDatastore.put(person.getFirstName(), person);
        Person car = new Person();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            car = mapper.readValue(person, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpResponse.created(car.getFirstName());
    }*/







    //@Post
    //public HttpResponse index() {
    //    return HttpResponse.created("asdasdasdsa");
    //}

    /*@Post()
    public HttpResponse<String> save(@Body Person person) {
                    return HttpResponse.created(person.getFirstName()+person.getLastName());


    }
    */

}