package edu.mbortnichuk.newphonebook.dao;

import edu.mbortnichuk.newphonebook.model.Address;
import edu.mbortnichuk.newphonebook.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariana on 07-May-17.
 */
public class AddressDAO extends AbstractDAO<Address> {

    @Override
    public List<Address> read(String key, String value) {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT * FROM new_phonebook.address WHERE " + key + " = '" + value + "' ";
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
    public List<Address> readALL() {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT * FROM new_phonebook.address;";
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
         Address adress = new Address("USA", "Seattle");
//        System.out.println(adr.create(adress));
        System.out.println(adr.delete("id", "7"));
    }
}
