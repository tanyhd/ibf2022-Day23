package day23.day23.models;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import jakarta.json.Json;
import jakarta.json.JsonValue;

public class LineItem {
    int itemId;
    String name = "";
    int quantity = 0;
    float price = 0;

    public LineItem() {
    }

    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public LineItem populate(SqlRowSet rs) {
        LineItem lineItem = new LineItem();
        lineItem.setItemId(rs.getInt("item_id"));
        lineItem.setName(rs.getString("name"));
        lineItem.setQuantity(rs.getInt("quantity"));
        lineItem.setPrice(rs.getFloat("price"));
        return lineItem;
    }

    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("item_id", itemId)
                .add("name", name)
                .add("quantity", quantity)
                .add("price", price)
                .build();
    }

}
