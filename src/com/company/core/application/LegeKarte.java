package com.company.core.application;

import com.company.core.domain.Karte;
import com.company.core.domain.Spiel;

public class LegeKarte {

    private final SpielRepository repository;

    public LegeKarte(SpielRepository repository) {
        this.repository = repository;
    }

    public void invoke(Karte karte) {
        Spiel spiel = repository.ladeSpiel();
        spiel.karteLegen(karte);
        repository.speicherSpielAb(spiel);
    }
}
