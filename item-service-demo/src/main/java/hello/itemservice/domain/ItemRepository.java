package hello.itemservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
public class ItemRepository {

    private DataSource dataSource;
    public ItemRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    public List<Item> selectAll(){
        String sql = "select id,item_name,price,quantity,register_id,register_name from item";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<Item> items = new ArrayList<>();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getString("id"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getInt("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setRegisterId(rs.getString("register_id"));
                item.setRegisterName(rs.getString("register_name"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    public void deleteAll(){
        String sql = "DELETE item";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    public void deleteItemById(String id){
        String sql =  "DELETE item WHERE id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            close(con,pstmt,null);
        }

    }

    public Item insertItem(Item item){
        item.setId(UUID.randomUUID().toString());
        String sql = "insert into item (id, item_name, price, quantity, open, item_type,delivery_code, register_id, register_name) values(?,?,?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,item.getId());
            pstmt.setString(2,item.getItemName());
            pstmt.setInt(3,item.getPrice());
            pstmt.setInt(4,item.getQuantity());
            pstmt.setBoolean(5,item.getOpen());
            pstmt.setString(6,item.getItemType().name());
            pstmt.setString(7,item.getDeliveryCode().name());
            pstmt.setString(8,item.getRegisterId());
            pstmt.setString(9,item.getRegisterName());
            pstmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    public void insertItemRegionById(String id, Item item){
        String sql = "insert into item_region(item_id, region_name) values (?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            List<String> regions = item.getRegions();
            log.info("regions: {}", regions);
            for (int i = 0; i < regions.size(); i++) {
                String region = regions.get(i);
                pstmt.setString(1, id);
                pstmt.setString(2, region);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    public void updateItem(String id,Item find,Item updateItem){
        String sql = "update item set item_name = ?, price = ?, quantity = ?, open = ?, item_type = ?,  delivery_code = ? where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, (find.getItemName().equals(updateItem.getItemName())? find.getItemName() : updateItem.getItemName()));
            pstmt.setLong(2,(find.getPrice().equals(updateItem.getPrice())? find.getPrice(): updateItem.getPrice()));
            pstmt.setInt(3,(find.getQuantity().equals(updateItem.getQuantity())? find.getQuantity() : updateItem.getQuantity()));
            pstmt.setBoolean(4,(find.getOpen().equals(updateItem.getOpen())? find.getOpen() : updateItem.getOpen()));
            pstmt.setString(5,(find.getItemType().equals(updateItem.getItemType())? find.getItemType().name() : updateItem.getItemType().name()));
            pstmt.setString(6,(find.getDeliveryCode().equals(updateItem.getDeliveryCode())? find.getDeliveryCode().name() : updateItem.getDeliveryCode().name()));
            pstmt.setString(7,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    public Item selectItemById(String id){
        String sql = "select * from item where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            Item findItem = new Item();
            if(rs.next()){
                findItem.setId(rs.getString("id"));
                findItem.setItemName(rs.getString("item_name"));
                findItem.setPrice(rs.getInt("price"));
                findItem.setQuantity(rs.getInt("quantity"));
                findItem.setOpen(rs.getBoolean("open"));
                findItem.setItemType(ItemType.valueOf(rs.getString("item_type")));
                findItem.setDeliveryCode(DeliveryCode.valueOf(rs.getString("delivery_code")));
                findItem.setRegisterId(rs.getString("register_id"));
                findItem.setRegisterName(rs.getString("register_name"));
            }
            return findItem;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,rs);
        }
    }

    public ArrayList<String> selectItemRegionById(String id){
        String sql = "select * from item_region where item_id = ?";
        ArrayList<String> regions = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);;
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                regions.add(rs.getString("region_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,rs);
        }
        return regions;
    }


    public void deleteItemRegionById(String id){
        String sql= "delete from item_region where item_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con,pstmt,null);
        }
    }

    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);
        DataSourceUtils.releaseConnection(con,dataSource);
    }
}
