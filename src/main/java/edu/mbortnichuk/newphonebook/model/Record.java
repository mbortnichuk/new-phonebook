package edu.mbortnichuk.newphonebook.model;

/**
 * Created by Mariana on 30-Apr-17.
 */
public class Record {

    private long id = 0;
    private Person person;
    private Address address;

    public Record(long id, Person person, Address address) {
        this.id = id;
        this.person = person;
        this.address = address;
    }

    public Record(Person person, Address address) {
        this(0, person, address);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", person=" + person +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (id != record.id) return false;
        if (person != null ? !person.equals(record.person) : record.person != null) return false;
        return address != null ? address.equals(record.address) : record.address == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
