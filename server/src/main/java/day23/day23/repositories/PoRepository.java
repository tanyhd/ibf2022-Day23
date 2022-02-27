package day23.day23.repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day23.day23.models.LineItem;
import day23.day23.models.Po;
import static day23.day23.repositories.SQL.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PoRepository {

    @Autowired
    private JdbcTemplate template;

    int id = 0;

    public boolean add(Po po) {
        int added = template.update(SQL_ADD_PO, po.getName(), po.getEmail());
        SqlRowSet rs = template.queryForRowSet(SQL_GET_PO_ID);

        while(rs.next()) {
            id = rs.getInt("ord_id");
        }

        List<Object[]> lineItemArray = po.getLineItems().stream()
                                        .map(lineitem -> new Object[] {lineitem.getName(), lineitem.getQuantity(), lineitem.getPrice(), id})
                                        .collect(Collectors.toList());
        int addedArray[] = template.batchUpdate(
                        SQL_ADD_LINEITEM, lineItemArray);
    
        return added + addedArray.length > 0;
    }

    public List<Po> getAllPo() {
        List<Po> pos = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_PO);

        while(rs.next()) {
            int orderId = rs.getInt("ord_id");
            SqlRowSet rsLineItem = template.queryForRowSet(SQL_GET_LINEITEM_FROM_PO, orderId);
            List<LineItem> lineItems = new LinkedList<>();
            while(rsLineItem.next()) {
                LineItem lineItem = new LineItem();
                lineItems.add(lineItem.populate(rsLineItem));
            }
            SqlRowSet rsLineItemTotal = template.queryForRowSet(SQL_GET_TOTAL_PO_COST, orderId);
            Double totalCost = (double) 0;
            while(rsLineItemTotal.next()) {
                totalCost = (double) rsLineItemTotal.getFloat("total");
            }
            Po po = new Po();
            totalCost = (Double) (Math.round(totalCost * 100.00) / 100.00);
            pos.add(po.populate(rs, lineItems, totalCost));
        }
        return pos;
    }
    
}
