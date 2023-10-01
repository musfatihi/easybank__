package ma.easybank.DAO.Intrfcs;

import ma.easybank.DTO.Operation;
import java.util.Optional;

public interface OperationDAO {

    Operation save(Operation operation);

    Optional<Operation> findBy(Operation operation);

    Boolean delete(Operation operation);

}
