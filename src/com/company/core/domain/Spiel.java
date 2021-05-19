package com.company.core.domain;

public class Spiel {

    private final SpielerRunde spielerRunde;

    private final Kartensammlung zugStapel;
    private final Kartensammlung ablageStapel;

    private Spiel(SpielerRunde spielerRunde, Kartensammlung zugStapel, Kartensammlung ablageStapel) {
        this.spielerRunde = spielerRunde;
        this.zugStapel = zugStapel;
        this.ablageStapel = ablageStapel;
    }

    public static Spiel neuesSpiel(int spieleranzahl) {
        Kartensammlung spielKarten = Kartensammlung.gemischtesDeck();
        Kartensammlung deck2 = Kartensammlung.gemischtesDeck();
        spielKarten.hinzufuegen(deck2);

        SpielerRunde spielerRunde = SpielerRunde.mitSpieleranzahl(spieleranzahl);
        spielerRunde.teileKartenAus(spielKarten, Spieler.ANZAHL_STARTKARTEN);

        Kartensammlung ablageStapel = Kartensammlung.leereSammlung();
        ablageStapel.hinzufuegen(spielKarten.entnehmeKarten(1));

        return new Spiel(spielerRunde, spielKarten, ablageStapel);
    }

    public void legeKarte(int spielerPosition, Karte karte) {
        if (karteKannGelegtWerden(spielerPosition, karte)) {
            ablageStapel.hinzufuegen(karte);
            spielerRunde.getSpielerAmZug().vonHandkartenEntnehmen(karte);
            spielerRunde.naechsterSpieler();
            spielerRunde.spielerAmZugHatKarteGelegt = true;
        }
    }

    public void beendeZug() {
        if (!spielerRunde.spielerAmZugHatKarteGelegt) {
            spielerRunde.getSpielerAmZug().zuHandkartenHinzufuegen(zugStapel.entnehmeKarten(1)); //TODO leerer zugStapel
        }

        spielerRunde.spielerAmZugHatKarteGelegt = false;
        spielerRunde.naechsterSpieler();
    }

    public Kartensammlung ablageStapel() {
        return ablageStapel;
    }

    public Spieler getSpielerAnPosition(int position) {
        return spielerRunde.getSpieler(position);
    }

    public int getPositionAmZug() {
        return spielerRunde.getPositionIstAmZug();
    }

    private boolean karteKannGelegtWerden(int spielerPosition, Karte karte) {
        boolean istAmZug = this.spielerRunde.getPositionIstAmZug() == spielerPosition;
        boolean istGueltigerZug = karte.hatSelbesSymbol(ablageStapel.zuletztGelegteKarte())
                || karte.hatSelbenRang(ablageStapel.zuletztGelegteKarte());

        return  istAmZug && istGueltigerZug;
    }
}
