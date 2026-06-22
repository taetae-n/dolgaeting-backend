package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Pick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int roundNo;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private GameSession gameSession;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Idol winner;

    @ManyToOne
    @JoinColumn(name = "loser_id")
    private Idol loser;
}