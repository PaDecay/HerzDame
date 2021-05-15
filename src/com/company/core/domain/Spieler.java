package com.company.core.domain;

public class Spieler {

    public static int ANZAHL_STARTKARTEN = 15;
    private final Kartensammlung handkarten;

    public Spieler() {
        this.handkarten = Kartensammlung.leereSammlung();
    }

    public void zuHandkartenHinzufuegen(Kartensammlung kartensammlung) {
        this.handkarten.hinzufuegen(kartensammlung);
    }

    public void vonHandkartenEntnehmen(Karte karte) {
        this.handkarten.entnehmeKarte(karte);
    }

    public Kartensammlung getHandkarten() {
        return this.handkarten;
    }
}
