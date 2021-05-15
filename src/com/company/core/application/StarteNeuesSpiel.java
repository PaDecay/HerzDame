package com.company.core.application;

import com.company.core.domain.Spiel;

public class StarteNeuesSpiel {

    private final SpielRepository repository;

    public StarteNeuesSpiel(SpielRepository repository) {
        this.repository = repository;
    }

    public void invoke(int spieleranzahl) {
        Spiel neuesSpiel = Spiel.neuesSpiel(spieleranzahl);
        this.repository.speicherSpielAb(neuesSpiel);
    }
}