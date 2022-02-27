package day23.day23.models;

import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Po {

    int orderId;
    String name;
    String email;
    double totalCost;
    List<LineItem> lineItems;
    
    public Po() {
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Po populate(SqlRowSet rs, List<LineItem> lineItems, double totalCost) {
        Po po = new Po();
        po.setOrderId(rs.getInt("ord_id"));
        po.setEmail(rs.getString("email"));
        po.setName(rs.getString("name"));
        po.setLineItems(lineItems);
        po.setTotalCost(totalCost);
        return po;
    }

    public JsonValue toJson() {
        return Json.createObjectBuilder()
            .add("ord_id", orderId)
            .add("name", name)
            .add("email", email)
            .add("lineItemList", lineItemsToJson())
            .add("totalCost", totalCost)
            .build();
    }

    public JsonValue lineItemsToJson() {
        JsonArrayBuilder lineItemsBuilder = Json.createArrayBuilder();
        for(int i = 0; i < this.lineItems.size(); i++) {
            lineItemsBuilder.add(this.lineItems.get(i).toJson());
        }
        return lineItemsBuilder.build();
    }

}
