package ma.easybank.DAO.Intrfcs;

import java.util.Optional;

public interface GenericInterface<T> {
    T save(T t);

    T update(T t);

    Optional<T> findBy(T t);

    Boolean delete(T t);

}
