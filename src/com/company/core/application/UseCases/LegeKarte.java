package com.company.core.application.UseCases;

import com.company.core.application.SpielRepository;
import com.company.core.domain.Karte;
import com.company.core.domain.Spiel;

public class LegeKarte {

    private final SpielRepository repository;

    public LegeKarte(SpielRepository repository) {
        this.repository = repository;
    }

    public void invoke(int spielerPosition, Karte karte) {
        Spiel spiel = repository.ladeSpiel();
        spiel.legeKarte(spielerPosition, karte);
        repository.speicherSpielAb(spiel);
    }
}
