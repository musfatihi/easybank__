package ma.easybank.DTO;

import java.time.LocalDate;

public class Currentaccnt extends Account{

    private float overdraft;

    public Currentaccnt(){

    }

    public Currentaccnt(Employee employee,Client client,float overdraft){

        super(employee,client);
        this.overdraft = overdraft;

    }


    public Currentaccnt(int nbr, float balance, LocalDate crtnDate, State state, Client client, Employee createdBy,float overdraft){

        super(nbr, balance, crtnDate, state, client, createdBy);

        this.overdraft = overdraft;

    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return super.toString()+"Decouvert : "+this.overdraft+"\n";
    }
}
