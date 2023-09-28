package ma.easybank.DAO.Implmnts;

import ma.easybank.DAO.Intrfcs.AssignmentDAO;
import ma.easybank.DAO.Services.AssignmentService;
import ma.easybank.DTO.Assignment;
import ma.easybank.DTO.Employee;

import java.util.HashMap;
import java.util.List;

public class AssignmentDAOImpl implements AssignmentDAO {

    public static AssignmentService assignmentService;

    public AssignmentDAOImpl(AssignmentService assignmentService){

        AssignmentDAOImpl.assignmentService = assignmentService;

    }

    @Override
    public Assignment save(Assignment assignment) {
        return assignmentService.save(assignment);
    }

    @Override
    public List<Assignment> findBy(Employee employee) {
        return null;
    }

    @Override
    public Boolean delete(Assignment assignment) {
        return null;
    }

    @Override
    public HashMap<String, String> getStats() {
        return null;
    }
}
