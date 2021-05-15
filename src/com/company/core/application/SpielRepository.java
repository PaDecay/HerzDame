package com.company.core.application;

import com.company.core.domain.Spiel;

public interface SpielRepository {

    void speicherSpielAb(Spiel spiel);

    Spiel ladeSpiel();
}
