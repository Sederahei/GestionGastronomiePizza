package org.alherendro.dao;

public interface CrudOperationIngredient<E> {
    E save(E entity);
    E update(E entity);
    E delete(E entity);
    E getAll();
}
