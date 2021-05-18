package com.company.core.application.UseCases;

import com.company.core.application.SpielRepository;
import com.company.core.domain.Spiel;

public class BeendeZug {

    private final SpielRepository repository;

    public BeendeZug(SpielRepository repository) {
        this.repository = repository;
    }

    public void invoke() {
        Spiel spiel = this.repository.ladeSpiel();
        spiel.beendeZug();
        this.repository.speicherSpielAb(spiel);
    }
}


