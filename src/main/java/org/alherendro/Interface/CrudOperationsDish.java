package org.alherendro.Interface;

import org.alherendro.entity.Ingredient;

import java.util.List;

public interface CrudOperationsDish<E> {
    E findById(int id);
    E save(E entity);
    E update(E entity);
    E delete(E entity);
    List<Ingredient> getAll();

    List<Ingredient> filterSortPaginateIngredients(String sau, String g, double v, double v1, Object o, Object o1, String unitPrice, int i, int i1);

}
