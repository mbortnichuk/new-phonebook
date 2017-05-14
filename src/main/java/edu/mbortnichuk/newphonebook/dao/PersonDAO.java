package edu.mbortnichuk.newphonebook.dao;

import edu.mbortnichuk.newphonebook.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariana on 07-May-17.
 */
public class PersonDAO extends AbstractDAO<Person> {

    @Override
    public List<Person> read(String key, String value) {

        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT * FROM new_phonebook.person WHERE " + key + " = '" + value + "' ";  // +
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql); //executeupdate

            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");  //+
                String phonenumber = resultSet.getString("phone");  //+
                String name = resultSet.getString("name"); //+

                Person person = new Person(id, phonenumber, name); //+
                personList.add(person); //+
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }

        return null;
    }

    @Override
    public List<Person> readALL() {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "SELECT * FROM new_phonebook.person;";
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String phonenumber = resultSet.getString("phone");
                String name = resultSet.getString("name");

                Person person = new Person(id, phonenumber, name);
                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return null;
    }

    @Override
    public Person create(Person person) {
        Connection connect = null;
        try {
            connect = getConnection();
            String SQL_INSERT = "INSERT INTO new_phonebook.person (phone, name) VALUES(?, ?)";
            PreparedStatement statement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, person.getId());
            statement.setString(1, person.getPhone());
            statement.setString(2, person.getName());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
            return new Person(person.getId(), person.getPhone(), person.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connect);
        }
        return null;
    }

    @Override
    public int update(Person record, String key, String value) {
        Connection connect = null;
        try {
            connect = getConnection();
            String sql = "UPDATE new_phonebook.person SET " + " phone='" + record.getPhone() + "', name='" + record.getName() + "' " + "WHERE " + key + " = '" + value + "' ";
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
            String sql = "DELETE FROM new_phonebook.person WHERE " + key + " = '" + value + "' ";
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

        PersonDAO pers = new PersonDAO();
        List<Person> read = pers.readALL();
        System.out.println(read);

//        Person per = pers.create(new Person("Ref", "033"));
//        System.out.println(per);
    }
}