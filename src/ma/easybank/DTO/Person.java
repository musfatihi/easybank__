package ma.easybank.DTO;

import ma.easybank.UTILS.Helpers;

import java.time.LocalDate;

public abstract class Person {

    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    protected String address;
    protected String phoneNumber;

    public Person(){

    }

    public Person(String firstName,String lastName,LocalDate birthDate,String address,String phoneNumber){

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Prenom : "+this.firstName+"\n"+"Nom : "+this.lastName+"\n"+"Date de naissance : "+ Helpers.localDateToStr(this.birthDate)+"\n"
                +"Adresse : "+this.address+"\n"+"N° Tel : "+this.phoneNumber+"\n";
    }

}

