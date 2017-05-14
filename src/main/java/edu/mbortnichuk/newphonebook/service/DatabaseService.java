package edu.mbortnichuk.newphonebook.service;

import edu.mbortnichuk.newphonebook.dao.AddressDAO;
import edu.mbortnichuk.newphonebook.dao.PersonDAO;
import edu.mbortnichuk.newphonebook.dao.RecordDAO;
import edu.mbortnichuk.newphonebook.model.Address;
import edu.mbortnichuk.newphonebook.model.Person;
import edu.mbortnichuk.newphonebook.model.Record;

import java.util.List;

/**
 * Created by Mariana on 14-May-17.
 */
public class DatabaseService {

    private PersonDAO personDAO = new PersonDAO();
    private AddressDAO addressDAO = new AddressDAO();
    private RecordDAO recordDAO = new RecordDAO();

    public Record createRecord(Record record) {
        Person person = personDAO.create(record.getPerson());
        List<Address> adrId = addressDAO.read("id", "" + record.getAddress().getId());
//        List<Address> adrCounrty = addressDAO.read("country", "" + record.getAddress().getCountry());
//        List<Address> adrCity = addressDAO.read("city", "" + record.getAddress().getCity());
        Address address;
        if(adrId.size() == 0){
             address = addressDAO.create(record.getAddress());
        }else{
            address = record.getAddress();
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
        addressDAO.delete("id", "" + record.getAddress().getId());
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
        Record record = new Record(new Person("PO", "21212"), new Address("Ukraine", "Kyiv"));
        Record persistedRecord = dbps.createRecord(record);
        System.out.println(persistedRecord);
        persistedRecord.getAddress().setCity("Srakpil");
        dbps.updateRecord(persistedRecord);
        System.out.println(dbps.readRecordById(persistedRecord.getId()));
        dbps.deleteRecord(persistedRecord);


    }
}

//write all remaining methods for Person, Address.
//methods to ReadById. for Person and Address
//(optional) Multiparam read
