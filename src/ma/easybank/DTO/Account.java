package ma.easybank.DTO;

import ma.easybank.UTILS.Helpers;

import java.time.*;
import java.util.List;

public class Account {

    private int nbr;
    private double balance;
    private LocalDate crtnDate;
    private State state;
    private Client client;
    private Employee createdBy;
    private List<Operation> operations;

    public Account(){

    }

    public Account(int nbr){
        this.nbr = nbr;
    }

    public Account(Employee createdBy,Client client){

        this.createdBy = createdBy;
        this.client = client;

    }

    public Account(int nbr,double balance,LocalDate crtnDate,State state,Client client,Employee createdBy){

        this.nbr = nbr;
        this.balance = balance;
        this.crtnDate = crtnDate;
        this.state = state;
        this.client = client;
        this.createdBy = createdBy;

    }

    public Account(int nbr,double balance,LocalDate crtnDate,Client client,Employee createdBy,State state){

        this.nbr = nbr;
        this.balance = balance;
        this.crtnDate = crtnDate;
        this.state = state;
        this.client = client;
        this.createdBy = createdBy;

    }

    public int getNbr() {
        return nbr;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCrtnDate() {
        return crtnDate;
    }

    public State getState() {
        return state;
    }

    public Client getClient() {
        return client;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCrtnDate(LocalDate crtnDate) {
        this.crtnDate = crtnDate;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "Numéro de Compte : "+this.nbr+"\n"+"Date de création : "+ Helpers.localDateToStr(this.crtnDate)+"\n"+"Solde : "+this.balance+"\n"+
                "Client : "+this.client.getCode()+"\n"+"Employé : "+this.createdBy.getMtrcltNbr()+"\n"+"Etat : "+this.state+"\n";
    }

}
