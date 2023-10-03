package ma.easybank.DTO;

import ma.easybank.UTILS.Helpers;

import java.time.LocalDate;

public class Assignment {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Mission mission;
    private Employee employee;

    public Assignment(){

    }
    public Assignment(int id){

        this.id = id;

    }

    public Assignment(Mission mission,LocalDate startDate,LocalDate endDate){

        this.mission = mission;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Assignment(Mission mission,Employee employee,LocalDate startDate,LocalDate endDate){
        this.mission = mission;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Assignment(Mission mission,Employee employee,LocalDate startDate){

        this.mission = mission;
        this.employee = employee;
        this.startDate = startDate;

    }

    public Assignment(Mission mission,Employee employee){

        this.mission = mission;
        this.employee = employee;

    }


    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public Mission getMission() {
        return mission;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return "Mission : "+this.mission.getCode()+"\n"+"Opérateur : "+this.employee.getMtrcltNbr()+"\n"+
                "Du : "+ Helpers.localDateToStr(this.startDate)+"\n"+"Au : "+(this.endDate!=null?Helpers.localDateToStr(this.endDate):"--")+"\n";

    }

}
