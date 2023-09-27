package ma.easybank.DTO;

import java.time.LocalDate;
import java.util.List;

public class Client extends Person{
    private int code;
    private List<Account> accounts;

    public Client(){
        super();
    }

    public Client(int code){

        super();
        this.code = code;

    }


    public Client(int code,String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber){

        super(firstName,lastName,birthDate,address,phoneNumber);

        this.code = code;

    }


    public Client(String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber){
        super(firstName,lastName,birthDate,address,phoneNumber);
    }


    public int getCode() {
        return code;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return super.toString()+"Code : "+this.code+"\n";
    }
}

