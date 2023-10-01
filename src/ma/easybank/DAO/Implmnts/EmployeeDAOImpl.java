package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.GenericInterface;
import ma.easybank.DAO.Services.EmployeeService;
import ma.easybank.DTO.Employee;
import ma.easybank.UTILS.Helpers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDAOImpl implements GenericInterface<Employee> {

    public static EmployeeService employeeService;

    public EmployeeDAOImpl(EmployeeService employeeService){
        EmployeeDAOImpl.employeeService = employeeService;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @Override
    public Optional<Employee> findBy(Employee employee) {
        return employeeService.findEmployeeByMtrcl(employee.getMtrcltNbr());
    }
    @Override
    public Boolean delete(Employee employee) {
        return employeeService.deleteEmployee(employee);
    }

    public static List<Employee> findAll(EmployeeService employeeService){
        return employeeService.findAllEmployees();
    }

    public static List<Employee> findByFirstName(String firstName,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public static List<Employee> findByLastName(String lastName,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }


    public static List<Employee> findByAddress(String address,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getAddress().equals(address))
                .collect(Collectors.toList());
    }


    public static List<Employee> findByPhoneNbr(String phoneNbr,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getPhoneNumber().equals(phoneNbr))
                .collect(Collectors.toList());
    }

    public static List<Employee> findByMailAdrs(String mailAdrs,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getMailAddress().equals(mailAdrs))
                .collect(Collectors.toList());
    }


    public static List<Employee> findByBirthDate(String birthDate,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getBirthDate().equals(Helpers.strToDate(birthDate)))
                .collect(Collectors.toList());
    }

    public static List<Employee> findByRcrtmntDate(String rcrtmntDate,EmployeeService employeeService){
        return employeeService.findAllEmployees().stream()
                .filter(employee -> employee.getRcrtmntDate().equals(Helpers.strToDate(rcrtmntDate)))
                .collect(Collectors.toList());
    }

}
