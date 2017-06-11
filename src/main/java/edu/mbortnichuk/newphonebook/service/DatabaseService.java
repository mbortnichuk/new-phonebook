package edu.mbortnichuk.newphonebook.service;

import edu.mbortnichuk.newphonebook.dao.AddressDAO;
import edu.mbortnichuk.newphonebook.dao.PersonDAO;
import edu.mbortnichuk.newphonebook.dao.RecordDAO;
import edu.mbortnichuk.newphonebook.model.Address;
import edu.mbortnichuk.newphonebook.model.Person;
import edu.mbortnichuk.newphonebook.model.Record;
import edu.mbortnichuk.newphonebook.model.SQLOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mariana on 14-May-17.
 */
public class DatabaseService {

    private PersonDAO personDAO = new PersonDAO();
    private AddressDAO addressDAO = new AddressDAO();
    private RecordDAO recordDAO = new RecordDAO();

    public Record createRecord(Record record) {
        Person person = personDAO.create(record.getPerson());
        Map<String, String> map = new HashMap<>();
        map.put("country", record.getAddress().getCountry());
        map.put("city", record.getAddress().getCity());
//        List<Address> adrId = addressDAO.read("id", "" + record.getAddress().getId());
        List<Address> addressList = addressDAO.read(map, SQLOperator.AND);
//        List<Address> adrCounrty = addressDAO.read("country", "" + record.getAddress().getCountry());
//        List<Address> adrCity = addressDAO.read("city", "" + record.getAddress().getCity());
        Address address;
        if(addressList.size() == 0){
             address = addressDAO.create(record.getAddress());
        }else{
            address = addressList.get(0);
        }

        Record intermidiateRecord = new Record(person, address);
        Record persistantRecord = recordDAO.create(intermidiateRecord);
        return persistantRecord;
    }

    public void updateRecord(Record record){
        personDAO.update(record.getPerson(), "id", "" + record.getPerson().getId());
        addressDAO.update(record.getAddress(), "id", "" + record.getAddress().getId());
        recordDAO.update(record, "id", "" + record.getId());
    }

    public void deleteRecord(Record record){
        recordDAO.delete("id", "" + record.getId());
        personDAO.delete("id", "" + record.getPerson().getId());
    }

    public void deletePerson(Person person){
        personDAO.delete("id", "" + person.getId());
    }

    public Person createPerson(Person person){
       return personDAO.create(person);
    }

    public Address createAddress(Address address){
        return addressDAO.create(address);
    }

    public List<Record> readRecords(String key, String value){
        return recordDAO.read(key, value);
    }

    public Record readRecordById(long id){
//        return recordDAO.read("rec.id", "" + id);
        List<Record> recList = recordDAO.read("rec.id", "" + id);
     return (recList.size() > 0) ? recList.get(0) : null;
    }

    public List<Record> readAllRecords(){
        return recordDAO.readALL();
    }


    public static void main(String[] args) {
        DatabaseService dbps = new DatabaseService();
//        Record record = new Record(new Person("PO", "21212"), new Address("Ukraine", "Kyiv"));
//        Record persistedRecord = dbps.createRecord(record);
//        System.out.println(persistedRecord);
//        persistedRecord.getAddress().setCity("Srakpil");
//        dbps.updateRecord(persistedRecord);
//        System.out.println(dbps.readRecordById(persistedRecord.getId()));
//        dbps.deleteRecord(persistedRecord);

//        dbps.deletePerson(new Person(14, "PO", "21212"));

//        dbps.deleteRecord(new Record(10, new Person(9, "", ""), new Address(9, "", "")));

        dbps.createRecord(new Record(new Person("HO", "44444"), new Address("Ukraine", "Kyiv")));

    }
}

//write all remaining methods for Person, Address.
//methods to ReadById. for Person and Address
//(optional) Multiparam read
