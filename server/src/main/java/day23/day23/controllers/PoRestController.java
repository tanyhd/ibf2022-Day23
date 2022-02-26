package day23.day23.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day23.day23.models.LineItem;
import day23.day23.models.Po;
import day23.day23.repositories.PoRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin
public class PoRestController {
    
    @Autowired
    PoRepository poRepository;

    @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> placeOrder(@RequestBody byte[] payload) {
        Po po = new Po();
        try(InputStream is = new ByteArrayInputStream(payload)) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            System.out.println(result);
            po.setName(result.getString("name"));
            po.setEmail(result.getString("email"));

            List<LineItem> lineItems = new ArrayList<>();

            JsonArray readings = result.getJsonArray("listOfItems");
            for(JsonValue v: readings) {
                LineItem lineItem = new LineItem();
                JsonObject obj = (JsonObject) v;
                lineItem.setName(obj.getString("name"));
                lineItem.setQuantity(obj.getInt("quantity"));
                lineItem.setPrice(obj.getInt("price"));
                lineItems.add(lineItem);
                System.out.println(lineItem.getName());
                System.out.println(lineItem.getPrice());
            }
            po.setLineItems(lineItems.toArray());
            poRepository.add(po);
            System.out.println(po.toString());
        
        } catch (Exception e) {}

        JsonObjectBuilder returnMessage = Json.createObjectBuilder();
        returnMessage.add("message", "ok created");

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
    }
}
