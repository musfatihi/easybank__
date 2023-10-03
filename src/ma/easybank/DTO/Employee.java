package ma.easybank.DTO;

import ma.easybank.UTILS.Helpers;

import java.time.LocalDate;
import java.util.List;

public class Employee extends Person{

    private int mtrcltNbr;
    private LocalDate rcrtmntDate;
    private String mailAddress;
    private List<Assignment> assignments;
    private List<Account> createdAccounts;
    private List<Operation> operations;

    public Employee(){

    }

    public Employee(int mtrcltNbr){

        super();
        this.mtrcltNbr = mtrcltNbr;

    }

    public Employee(String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber, LocalDate rcrtmntDate,String mailAddress){

        super(firstName,lastName,birthDate,address,phoneNumber);
        this.rcrtmntDate = rcrtmntDate;
        this.mailAddress = mailAddress;

    }

    public Employee(int mtrcltNbr,String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber, LocalDate rcrtmntDate,String mailAddress){

        super(firstName,lastName,birthDate,address,phoneNumber);
        this.mtrcltNbr = mtrcltNbr;
        this.rcrtmntDate = rcrtmntDate;
        this.mailAddress = mailAddress;

    }

    public int getMtrcltNbr() {
        return mtrcltNbr;
    }

    public LocalDate getRcrtmntDate() {
        return rcrtmntDate;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public List<Assignment> getAssignments() {return assignments;}

    public List<Account> getCreatedAccounts() {
        return createdAccounts;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setMtrcltNbr(int mtrcltNbr) {
        this.mtrcltNbr = mtrcltNbr;
    }

    public void setRcrtmntDate(LocalDate rcrtmntDate) {
        this.rcrtmntDate = rcrtmntDate;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setAssignments(List<Assignment> assignments) {this.assignments = assignments;}

    public void setCreatedAccounts(List<Account> createdAccounts) {
        this.createdAccounts = createdAccounts;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return super.toString()+"matricule : "+this.mtrcltNbr+"\n"+"Date de recrutement : "+ Helpers.localDateToStr(this.rcrtmntDate)+"\n"+"Adresse mail: "+this.mailAddress+"\n";
    }
}
