package ma.easybank.DAO.Intrfcs;

import ma.easybank.DTO.Employee;

import java.util.Optional;

public interface EmployeeDAO {

    Employee save(Employee employee);

    Optional<Employee> findBy(Employee employee);

    Boolean delete(Employee employee);

}
