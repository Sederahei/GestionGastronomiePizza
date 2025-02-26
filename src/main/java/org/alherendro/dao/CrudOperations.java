package org.alherendro.dao;

import java.sql.SQLException;

public interface CrudOperations  <E> {
    E findById(long id) throws SQLException;
    E save(E entity);
    E update(E entity);
    E delete(E entity);
    E getAll();
    E hot_dog_const_ingredient_55000();

}
