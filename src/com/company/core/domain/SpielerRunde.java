package com.company.core.domain;

public class SpielerRunde {

    private final Spieler[] spielerRunde;

    private int positionIstAmZug;
    public boolean spielerAmZugHatKarteGelegt;

    public SpielerRunde(Spieler[] spielerRunde) {
        this.spielerRunde = spielerRunde;
        this.positionIstAmZug = 0;
        this.spielerAmZugHatKarteGelegt = false;
    }

    public static SpielerRunde mitSpieleranzahl(int spieleranzahl) {
        Spieler[] spielerRunde = new Spieler[spieleranzahl];

        for (int i = 0; i < spieleranzahl; i++) {
            spielerRunde[i] = new Spieler(i);
        }

        return new SpielerRunde(spielerRunde);
    }

    public Spieler getSpieler(int spielerPosition) {
        return this.spielerRunde[spielerPosition];
    }

    public Spieler getSpielerAmZug() {
        return this.spielerRunde[positionIstAmZug];
    }

    public void teileKartenAus(Kartensammlung kartensammlung, int anzahlAusgeteilterKarten) {
        for (Spieler spieler: spielerRunde) {
            spieler.zuHandkartenHinzufuegen(kartensammlung.entnehmeKarten(anzahlAusgeteilterKarten));
        }
    }

    public void naechsterSpieler() {
        if(positionIstAmZug < spielerRunde.length-1) {
            positionIstAmZug++;
        } else {
            positionIstAmZug = 0;
        }
    }

    public int getPositionIstAmZug() {
        return positionIstAmZug;
    }
}
