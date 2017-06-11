package edu.mbortnichuk.newphonebook.dao;

import edu.mbortnichuk.newphonebook.model.Address;
import edu.mbortnichuk.newphonebook.model.Person;
import edu.mbortnichuk.newphonebook.model.SQLOperator;

import java.sql.*;
import java.util.*;

/**
 * Created by Mariana on 07-May-17.
 */
public class AddressDAO extends AbstractDAO<Address> {


    public List<Address> read(Map<String, String> sqlParams, SQLOperator operator) {

//        SELECT * FROM new_phonebook.address WHERE
//                country = France and
//                city = Lion

        Connection connect = null;
        try {
            connect = getConnection();
//            String sql = "SELECT * FROM new_phonebook.address WHERE " + column + " = '" + value + "' ";
            String sqlSufix;
            if(!sqlParams.isEmpty()) {

                StringBuffer sqlSuffixBuilder = new StringBuffer();
                sqlSuffixBuilder.append("WHERE ");
                for (Map.Entry<String, String> parameter : sqlParams.entrySet()) {
                    sqlSuffixBuilder.append(parameter.getKey() + " = '" + parameter.getValue() + "' " + operator + " ");
                }

                String noLastSpace = sqlSuffixBuilder.substring(0, sqlSuffixBuilder.lastIndexOf(" "));
                sqlSufix = noLastSpace.substring(0, noLastSpace.lastIndexOf(" "));
            }else{

                sqlSufix = "";
            }
                String sqlPrefix = "SELECT * FROM new_phonebook.address ";
                String sql = sqlPrefix + sqlSufix;

                Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<Address> addressList = new ArrayList<>();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");

                Address address = new Address(id, country, city);
                addressList.add(address);
            }
            return addressList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return null;
    }

    @Override
    public List<Address> read(String column, String value) {
        Map<String, String> params = new HashMap<>();
        params.put(column, value);
        return this.read(params, SQLOperator.AND);
    }

    //    @Override
//    public List<Address> read(String column, String value) {
//        Connection connect = null;
//        try {
//            connect = getConnection();
//            String sql = "SELECT * FROM new_phonebook.address WHERE " + column + " = '" + value + "' ";
//            Statement statement = connect.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            List<Address> addressList = new ArrayList<>();
//            while (resultSet.next()) {
//
//                int id = resultSet.getInt("id");
//                String country = resultSet.getString("country");
//                String city = resultSet.getString("city");
//
//                Address address = new Address(id, country, city);
//                addressList.add(address);
//            }
//            return addressList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection(connect);
//        }
//        return null;
//    }

    @Override
    public List<Address> readALL() {
        return this.read(Collections.<String, String>emptyMap(), SQLOperator.AND);
    }



//    @Override
//    public List<Address> readALL() {
//        Connection connect = null;
//        try {
//            connect = getConnection();
//            String sql = "SELECT * FROM new_phonebook.address;";
//            Statement statement = connect.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            List<Address> addressList = new ArrayList<>();
//            while (resultSet.next()) {
//
//                int id = resultSet.getInt("id");
//                String country = resultSet.getString("country");
//                String city = resultSet.getString("city");
//
//                Address address = new Address(id, country, city);
//                addressList.add(address);
//            }
//            return addressList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection(connect);
//        }
//        return null;
//    }

    @Override
    public Address create(Address address) {

        Connection connect = null;
        try {
            connect = getConnection();
            String SQL_INSERT = "INSERT INTO new_phonebook.address (country, city) VALUES(?, ?)";
            PreparedStatement statement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, person.getId());
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
            return new Address(address.getId(), address.getCountry(), address.getCity());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }

        return null;
    }

    @Override
    public int update(Address record, String key, String value) {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "UPDATE new_phonebook.address SET " + " country='" + record.getCountry() + "', " +
                    "city='" + record.getCity() + "' " + "WHERE " + key + " = '" + value + "' ";
            Statement statement = connect.createStatement();
            int rowsAffected = statement.executeUpdate(sql); //executeupdate
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return 0;
    }

    @Override
    public int delete(String key, String value) {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "DELETE FROM new_phonebook.address WHERE " + key + " = '" + value + "' ";
            Statement statement = connect.createStatement();
            int rowsAffected = statement.executeUpdate(sql); //executeupdate
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return 0;
    }


    public static void main(String[] args) {
        AddressDAO adr = new AddressDAO();
//        Address adress = new Address("USA", "Seattle");
//        System.out.println(adr.create(adress));
//        System.out.println(adr.delete("id", "7"));

        Map<String, String> map = new HashMap<>();
//        map.put("country", "France");
//        map.put("city", "Paris");

//String operator = "AND";
//        StringBuffer params = new StringBuffer();
//        for (Map.Entry<String, String> parameter : map.entrySet()) {
//            params.append(parameter.getKey() + " = '" + parameter.getValue() + "' " + operator + " ");
//        }
//        String noLastSpace = params.substring(0, params.lastIndexOf(" "));
//        String sqlSufix = noLastSpace.substring(0, noLastSpace.lastIndexOf(" "));
//        String sqlPrefix = "SELECT * FROM new_phonebook.address WHERE ";
//        String sql = sqlPrefix + sqlSufix;

//        System.out.println(sql);

//        List<Address> addresses = adr.read(map, SQLOperator.AND);
//        List<Address> addresses = adr.readALL();
        List<Address> addresses = adr.read("country", "France");
        System.out.println(addresses);
    }
}
