package com.mongia.discpline.ledger.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "habits")
@Table
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String habitName;
    private String description;


}
