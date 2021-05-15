package com.company.core.application;

import com.company.core.domain.Spiel;

public class BeendeZug {

    private final SpielRepository repository;

    public BeendeZug(SpielRepository repository) {
        this.repository = repository;
    }

    public void invoke() {
        Spiel spiel = this.repository.ladeSpiel();
        spiel.zugBeenden();
        this.repository.speicherSpielAb(spiel);
    }
}


