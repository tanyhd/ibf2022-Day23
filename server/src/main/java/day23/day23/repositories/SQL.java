package day23.day23.repositories;

public class SQL {
    public static final String SQL_ADD_PO = "INSERT INTO po (name, email) VALUES(?, ?)";
    public static final String SQL_GET_PO_ID = "SELECT ord_id from po order by ord_id desc limit 1";
    public static final String SQL_ADD_LINEITEM = "INSERT INTO line_item (name, quantity, price, ord_id) VALUES(?, ?, ?, ?)";

}
