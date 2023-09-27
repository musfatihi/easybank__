package ma.easybank.DTO;

import java.time.LocalDate;

public class Savingsaccnt extends Account{

    private float interestrate;

    public Savingsaccnt(){

    }

    public Savingsaccnt(Employee employee,Client client,float interestrate){

        super(employee,client);
        this.interestrate = interestrate;

    }



    public Savingsaccnt(int nbr, float balance, LocalDate crtnDate, State state, Client client, Employee createdBy, float interestrate){

        super(nbr, balance, crtnDate, state, client, createdBy);

        this.interestrate = interestrate;

    }

    public float getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(float interestrate) {
        this.interestrate = interestrate;
    }

    @Override
    public String toString() {
        return super.toString()+"Taux d'interet : "+this.interestrate+"\n";
    }
}
