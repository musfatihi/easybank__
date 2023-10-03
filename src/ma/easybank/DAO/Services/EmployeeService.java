package ma.easybank.DAO.Services;

import ma.easybank.DTO.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService {

    private Connection connection;

    private static final String SAVE_EMPLOYEE = "insert into employees (firstname,lastname, birthdate, address, phonenumber, rcrtmntdate, mailaddress) values (?,?,?,?,?,?,?) returning mtrcltnbr";

    private static final String UPDATE_EMPLOYEE = "update employees set firstname=?,lastname=?,birthdate=?,address=?,phonenumber=?,rcrtmntdate=?,mailaddress=? where mtrcltnbr=?";

    private static final String DELETE_EMPLOYEE = "update employees set deleted=true where mtrcltNbr=?";

    private static final String FIND_EMPLOYEE_MTRCL = "select * from employees where mtrcltNbr=?";

    private static final String FIND_ALL_EMPLOYEES = "select * from employees where deleted=false";

    private static final String CHECK_EMPLOYEE = "select count(*) from employees where mtrcltnbr=? and deleted=false";






    //Constructor
    public EmployeeService(Connection connection) {
        this.connection = connection;
    }

    //Employee Saving
    public Employee saveEmployee(Employee employee) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(SAVE_EMPLOYEE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(employee.getBirthDate()));
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getPhoneNumber());
            stmt.setDate(6, java.sql.Date.valueOf(employee.getRcrtmntDate()));
            stmt.setString(7, employee.getMailAddress());

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();

            employee.setMtrcltNbr(resultSet.getInt(1));


        } catch (Exception e) {
            System.out.println(e);
        }

        return employee;

    }

    //Employee Saving
    public Employee updateEmployee(Employee employee) {

        int updatedRows = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_EMPLOYEE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(employee.getBirthDate()));
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getPhoneNumber());
            stmt.setDate(6, java.sql.Date.valueOf(employee.getRcrtmntDate()));
            stmt.setString(7, employee.getMailAddress());

            stmt.setInt(8,employee.getMtrcltNbr());

            updatedRows = stmt.executeUpdate();

            if(updatedRows>0){
                return employee;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;

    }

    //Employee Deletion
    public Boolean deleteEmployee(Employee employee){

        int rowsUpdated = 0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(DELETE_EMPLOYEE);

            stmt.setInt(1,employee.getMtrcltNbr());

            rowsUpdated = stmt.executeUpdate();

        }
        catch (Exception e) {

            System.out.println(e);

        }

        return rowsUpdated>0;

    }

    //Employee Finding
    public Optional<Employee> findEmployeeByMtrcl(int mtrcl) {

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_EMPLOYEE_MTRCL, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, mtrcl);

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();

            Employee employee = new Employee(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDate(4).toLocalDate(),resultSet.getString(5),resultSet.getString(6),resultSet.getDate(7).toLocalDate(),resultSet.getString(8));

            return Optional.of(employee);


        }
        catch(Exception e){

            System.out.println(e);

        }

        return Optional.empty();

    }


    //Find All Employees
    public List<Employee> findAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        try {

            PreparedStatement stmt = this.connection.prepareStatement(FIND_ALL_EMPLOYEES, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {

                Employee employee = new Employee(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), resultSet.getDate(4).toLocalDate(),resultSet.getString(5),resultSet.getString(6),resultSet.getDate(7).toLocalDate(),resultSet.getString(8));

                employees.add(employee);

            }

        }
        catch(Exception e){

            System.out.println(e);

        }

        return employees;

    }

    //Check employee
    public Boolean exists(Employee employee){

        int count=0;

        try {

            PreparedStatement stmt = this.connection.prepareStatement(CHECK_EMPLOYEE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, employee.getMtrcltNbr());

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();

            count = resultSet.getInt(1);

        }
        catch(Exception e){

            System.out.println(e);

        }

        return count>0;

    }

}
