package org.alherendro.entity;

import java.time.LocalDateTime;

public class DishOrderStatus {
    private int id;
    private DishOrderStatusEnum status; // Statut du plat dans la commande (ENUM)
    private LocalDateTime statusDate;


}