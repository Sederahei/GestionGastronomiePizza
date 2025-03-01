package org.alherendro.Interface;

public interface CrudOperationIngredient<E> {
    E save(E entity);
    E update(E entity);
    E delete(E entity);
    E getAll();
    E getIngredientConst();
    E findById(long id);
}
