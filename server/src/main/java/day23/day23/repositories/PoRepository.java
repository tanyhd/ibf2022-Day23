package day23.day23.repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import day23.day23.models.Po;
import static day23.day23.repositories.SQL.*;
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
    
}
