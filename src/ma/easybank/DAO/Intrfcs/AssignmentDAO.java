package ma.easybank.DAO.Intrfcs;

import ma.easybank.DTO.Assignment;
import ma.easybank.DTO.Employee;

import java.util.HashMap;
import java.util.List;

public interface AssignmentDAO {

    Assignment save(Assignment assignment);

    List<Assignment> findBy(Employee employee);

    Boolean delete(Assignment assignment);

}
