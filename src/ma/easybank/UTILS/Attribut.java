package ma.easybank.UTILS;

import ma.easybank.DAO.Services.AccountService;
import ma.easybank.DAO.Services.ClientService;
import ma.easybank.DAO.Services.EmployeeService;
import ma.easybank.DAO.Services.MissionService;
import ma.easybank.DTO.Account;
import ma.easybank.DTO.Client;
import ma.easybank.DTO.Employee;
import ma.easybank.DTO.Mission;

public class Attribut {
    private String name;
    private Boolean isMandatory=true;
    private String type="string";


    public Attribut(String name){

        this.name = name;

    }

    public String getName() {
        return name;
    }

    public Boolean isMandatory() {
        return isMandatory;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMandatory() {
        this.isMandatory = true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean missionExists(Mission mission){
        return new MissionService(DBConnection.makeConnection()).exists(mission);
    }

    public Boolean clientExists(Client client){
        return new ClientService(DBConnection.makeConnection()).exists(client);
    }

    public Boolean employeeExists(Employee employee){
        return new EmployeeService(DBConnection.makeConnection()).exists(employee);
    }

    public Boolean accountExists(Account account){
        return new AccountService(DBConnection.makeConnection()).exists(account);
    }

}
