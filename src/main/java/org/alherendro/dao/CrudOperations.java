package org.alherendro.dao;

import java.sql.SQLException;

public interface CrudOperations  <E> {
    E findById(long id) throws SQLException;
    E save(E entity);
    E update(E entity);
    E delete(E entity);

}
