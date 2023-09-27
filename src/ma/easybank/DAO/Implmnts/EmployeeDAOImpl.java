package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.EmployeeDAO;
import ma.easybank.DAO.Services.EmployeeService;
import ma.easybank.DTO.Employee;

import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {

    public static EmployeeService employeeService;

    public EmployeeDAOImpl(EmployeeService employeeService){
        EmployeeDAOImpl.employeeService = employeeService;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @Override
    public Optional<Employee> findBy(Employee employee) {
        return employeeService.findEmployeeByMtrcl(employee.getMtrcltNbr());
    }

    @Override
    public Boolean delete(Employee employee) {
        return employeeService.deleteEmployee(employee);
    }
}
