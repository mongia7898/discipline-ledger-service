package com.mongia.discpline.ledger.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "habits")
@Table
public class Habit {
    @Id
    private int id;

    private String habitName;
    private String description;


}
