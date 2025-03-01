package org.alherendro.Interface;

import org.alherendro.entity.IngredientQuantity;

import java.util.List;

public interface CrudOperationIngredientQuantity <Q>{
    List<IngredientQuantity> findByDishId();
}
