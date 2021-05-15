package com.company.infrastructure;

import com.company.core.application.SpielRepository;
import com.company.core.domain.Spiel;

public class InMemorySpielRepository implements SpielRepository {
    private Spiel gespeichertesSpiel;

    @Override
    public void speicherSpielAb(Spiel spiel) {
        this.gespeichertesSpiel = spiel;
    }

    @Override
    public Spiel ladeSpiel() {
        return this.gespeichertesSpiel;
    }
}
