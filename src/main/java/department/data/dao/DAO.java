package department.data.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T getById(int entityId) throws SQLException;

    T getByName(String entityName) throws SQLException;

    void delete(int entityId) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(T entity) throws SQLException;

    void close();
}
