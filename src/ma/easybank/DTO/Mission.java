package ma.easybank.DTO;

import java.util.List;

public class Mission {

    private int code;
    private String name;
    private String description;
    private List<Employee> employees;


    public Mission(){

    }

    public Mission(int code){

        this.code = code;

    }

    public Mission(String name,String description){

        this.name = name;
        this.description = description;

    }



    public Mission(int code,String name,String description){

        this.code = code;
        this.name = name;
        this.description = description;

    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Code : "+this.code+"\n"+"Nom : "+this.name+"\n"+"Description : "+this.description+"\n";
    }

}
