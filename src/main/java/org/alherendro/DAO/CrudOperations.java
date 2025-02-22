package org.alherendro.DAO;

import java.awt.*;

public interface CrudOperations  <E>{


    E findById(long id);

    E save(E entity);


}
