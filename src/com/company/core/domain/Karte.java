package com.company.core.domain;

public class Karte {

    private final Symbol symbol;
    private final Rang rang;

    public Karte(Symbol symbol, Rang rang) {
        this.symbol = symbol;
        this.rang = rang;
    }

    public boolean hatSelbesSymbol(Karte karte) {
        return this.symbol == karte.symbol;
    }

    public boolean hatSelbenRang(Karte karte) {
        return this.rang == karte.rang;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Rang getRang() {
        return rang;
    }
}