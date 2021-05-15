package com.company.core.application;

import com.company.core.application.ViewModels.ViewData;
import com.company.core.domain.Spiel;

public class GetViewData {

    private final SpielRepository repository;

    public GetViewData(SpielRepository repository) {
        this.repository = repository;
    }

    public ViewData invoke(int spielerPosition) {
        Spiel spiel = repository.ladeSpiel();

        return ViewData.zuSpieler(spiel, spielerPosition);
    }
}
