package edu.mbortnichuk.newphonebook.dao;

import edu.mbortnichuk.newphonebook.model.Address;
import edu.mbortnichuk.newphonebook.model.Person;
import edu.mbortnichuk.newphonebook.model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariana on 07-May-17.
 */
public class RecordDAO extends AbstractDAO<Record> {

//    private PersonDAO personDAO = new PersonDAO();
//    private AddressDAO addressDAO = new AddressDAO();


    @Override
    public List<Record> read(String key, String value) {

        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT rec.id AS recordID, pers.id AS personID, pers.name AS name, pers.phone AS phone, " +
                    "adr.id AS addressID, adr.country AS country, adr.city AS city " +
                    "FROM new_phonebook.record rec " +
                    "JOIN new_phonebook.person pers ON rec.person=pers.id " +
                    "JOIN new_phonebook.address adr ON rec.address=adr.id WHERE " + key + " = '" + value + "' ";  // +
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql); //executeupdate

            List<Record> recordList = new ArrayList<>();
            while (resultSet.next()) {

                int id = resultSet.getInt("recordID");  //+
                int personId = resultSet.getInt("personID");
                String name = resultSet.getString("name"); //+
                String phonenumber = resultSet.getString("phone");//+
                int addressId = resultSet.getInt("addressID");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");

                Person person = new Person(personId, name, phonenumber);//+
                Address address = new Address(addressId, country, city);

                Record record = new Record(id, person, address);

                recordList.add(record); //+
            }
            return recordList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }

        return null;
    }

    @Override
    public List<Record> readALL() {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT rec.id AS recordID, pers.id AS personID, pers.name AS name, pers.phone AS phone, " +
                    "adr.id AS addressID, adr.country AS country, adr.city AS city " +
                    "FROM new_phonebook.record rec " +
                    "JOIN new_phonebook.person pers ON rec.person=pers.id " +
                    "JOIN new_phonebook.address adr ON rec.address=adr.id";  // +
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql); //executeupdate

            List<Record> recordList = new ArrayList<>();
            while (resultSet.next()) {

                int id = resultSet.getInt("recordID");  //+
                int personId = resultSet.getInt("personID");
                String name = resultSet.getString("name"); //+
                String phonenumber = resultSet.getString("phone");//+
                int addressId = resultSet.getInt("addressID");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");

                Person person = new Person(personId, name, phonenumber);//+
                Address address = new Address(addressId, country, city);

                Record record = new Record(id, person, address);

                recordList.add(record); //+
            }
            return recordList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return null;
    }

    @Override
    public Record create(Record record) {

//        Person person = personDAO.create(record.getPerson());
//        Address address = addressDAO.create(record.getAddress());

        Person person = record.getPerson();
        Address address = record.getAddress();
        if(person.getId() == 0) throw new RuntimeException("Insufficient personID");
        if(address.getId() == 0) throw new RuntimeException("Insufficient addressID");

        Connection connect = null;
        try {
            connect = getConnection();
            String SQL_INSERT = "INSERT INTO new_phonebook.record (person, address) VALUES(?, ?)";
            PreparedStatement statement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, person.getId());
            statement.setLong(1, person.getId());
            statement.setLong(2, address.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    record.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
            return new Record(record.getId(), record.getPerson(), record.getAddress());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }

        return null;
    }

    @Override
    public int update(Record record, String key, String value) {
//        Person person = personDAO.create(record.getPerson());
//        Address address = addressDAO.create(record.getAddress());
        Person person = record.getPerson();
        Address address = record.getAddress();
        if(person.getId() == 0) throw new RuntimeException("Insufficient personID");
        if(address.getId() == 0) throw new RuntimeException("Insufficient addressID");


        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "UPDATE new_phonebook.record SET " + " person = " + person.getId() + " , " + " address = " + address.getId() +
                     " WHERE " + key + " = '" + value + "' ";

            //phone+rec.getPhone, name+rec.getName, country+rec.getCountry, city+rec.getCity

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
            String sql = "DELETE FROM new_phonebook.record WHERE " + key + " = '" + value + "' ";
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

        RecordDAO rec = new RecordDAO();
//        List<Record> read = rec.read("name", "Kat");
//        System.out.println(read);

      //  System.out.println(rec.create(new Record(new Person("Iola", "343"), new Address("France", "Paris"))));

     //   System.out.println(rec.readALL());
//
//          System.out.println(rec.update(rec, "phone", "022"));

        System.out.println(rec.delete("id", "7"));
    }
}
