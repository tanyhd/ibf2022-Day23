package day23.day23.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import day23.day23.models.LineItem;
import day23.day23.models.Po;
import day23.day23.repositories.PoRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
public class PoRestController {
    
    @Autowired
    PoRepository poRepository;

    @PostMapping(path = "/order")
    public ResponseEntity<String> placeOrder(@RequestBody String requestBody) {

        Po po = new Po();
        boolean response = false; 
        try(InputStream is = new ByteArrayInputStream(requestBody.getBytes())) {

            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            po.setName(result.getString("name"));
            po.setEmail(result.getString("email"));

            List<LineItem> lineItems = new ArrayList<>();
            JsonArray readings = result.getJsonArray("listOfItems");

            for (int i = 0; i < readings.size(); i++) {
                LineItem lineItem = new LineItem();
                JsonObject obj = (JsonObject) readings.get(i);
                lineItem.setName(obj.getString("name"));
                lineItem.setQuantity(Integer.parseInt(obj.getString("quantity")));
                lineItem.setPrice(Float.parseFloat(obj.getString("price")));
                lineItems.add(lineItem);
            }

            po.setLineItems(lineItems);
            response = poRepository.add(po);
        
        } catch (Exception e) {}

        JsonObjectBuilder returnMessage = Json.createObjectBuilder();
        if(response == true) {
            returnMessage.add("message", "order added successfully");
        } else {
            returnMessage.add("message", "failed to add order");
        }
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(returnMessage.build().toString());
    }


    @GetMapping(path = "/orderList")
    public ResponseEntity<String> getAllPo() {
        List<Po> result = poRepository.getAllPo();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        result.stream().forEach(v -> arrBuilder.add(v.toJson()));
        return ResponseEntity.ok(arrBuilder.build().toString());
    } 
}
