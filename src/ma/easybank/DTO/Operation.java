package ma.easybank.DTO;

import ma.easybank.UTILS.Helpers;

import java.time.LocalDate;

public class Operation {

    private int nbr;
    private Type type;
    private LocalDate crtnDate;
    private double amount;
    private Account account;
    private Employee employee;

    public Operation(){

    }
    public Operation(int nbr){

        this.nbr = nbr;

    }

    public Operation(Type type,double amount,Account account,Employee employee){

        this.type = type;
        this.amount = amount;
        this.account = account;
        this.employee = employee;

    }


    public Operation(int nbr,LocalDate crtnDate, Type type,double amount,Account account,Employee employee){

        this.nbr = nbr;
        this.type = type;
        this.crtnDate = crtnDate;
        this.amount = amount;
        this.account = account;
        this.employee = employee;

    }


    public int getNbr() {
        return nbr;
    }

    public Type getType() {
        return type;
    }

    public LocalDate getCrtnDate() {
        return crtnDate;
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCrtnDate(LocalDate crtnDate) {
        this.crtnDate = crtnDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Numéro d'opération : "+this.nbr+"\n"+"Type : "+ this.type+"\n"+"Date : "+ Helpers.localDateToStr(this.crtnDate)+"\n"+
                "Montant : "+this.amount+"\n"+"Compte : "+this.account.getNbr()+"\n"+"Opérateur : "+this.employee.getMtrcltNbr()+"\n";
    }
}
