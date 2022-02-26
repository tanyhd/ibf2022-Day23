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

    public boolean add(Po po) {
        int added = template.update(SQL_ADD_PO, po.getName(), po.getEmail());
        SqlRowSet rs = template.queryForRowSet(SQL_GET_PO_ID);
        int poId = rs.getInt("LAST_INSERT_ID()");

        List<LineItem> lineItems = new LinkedList<>();
        LineItem[] lineItemArray = po.getLineItems();

        for(int i = 0; i < lineItemArray.length; i++) {
            lineItems.add(lineItemArray[i]);
        }

        List<Object[]> params = lineItems.stream()
                                .map(item -> new Object[] {item.getName(), item.getQuantity(), item.getPrice(), poId})
                                .collect(Collectors.toList());

        int listAdd[] = template.batchUpdate(SQL_ADD_LINEITEM, params);
        return added > 0;
    }
    
}
