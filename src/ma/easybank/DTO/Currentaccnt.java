package ma.easybank.DTO;

import java.time.LocalDate;

public class Currentaccnt extends Account{

    private double overdraft;

    public Currentaccnt(){

    }

    public Currentaccnt(Employee employee,Client client,double overdraft){

        super(employee,client);
        this.overdraft = overdraft;

    }


    public Currentaccnt(int nbr, float balance, LocalDate crtnDate, State state, Client client, Employee createdBy,double overdraft){

        super(nbr, balance, crtnDate, state, client, createdBy);

        this.overdraft = overdraft;

    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return super.toString()+"Decouvert : "+this.overdraft+"\n";
    }
}
