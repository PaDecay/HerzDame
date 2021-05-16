package com.company.core.domain;

import java.util.Collections;
import java.util.LinkedList;

public class Kartensammlung {

    private final LinkedList<Karte> karten;

    private Kartensammlung(LinkedList<Karte> karten) {
        this.karten = karten;
    }

    public static Kartensammlung gemischtesDeck() {
        LinkedList<Karte> karten = new LinkedList<>();

        for (Symbol symbol : Symbol.values()) {
            for (Rang rang : Rang.values()) {
                karten.add(new Karte(symbol, rang));
            }
        }

        Kartensammlung deck = new Kartensammlung(karten);
        deck.kartenMischen();

        return deck;
    }

    public static Kartensammlung leereSammlung() {
        LinkedList<Karte> karten = new LinkedList<>();
        return new Kartensammlung(karten);
    }

    public Karte zuletztGelegteKarte() {
        return this.karten.getLast();
    }

    public void hinzufuegen(Karte karte) {
        this.karten.add(karte);
    }

    public void hinzufuegen(Kartensammlung kartenSammlung) {
        this.karten.addAll(0, kartenSammlung.karten);
    }

    public void entnehmeKarte(Karte karte) {
        karten.removeIf(k -> k.hatSelbesSymbol(karte) && k.hatSelbenRang(karte));
    }

    public Kartensammlung entnehmeKarten(int anzahl) {
        Kartensammlung zuEntnehmendeKarten = Kartensammlung.leereSammlung();
        for (int i = 0; i < anzahl; i++) {
            zuEntnehmendeKarten.hinzufuegen(this.karten.getLast());         //TODO was ist wenn zu wenig Karten vorhanden
            this.karten.removeLast();
        }

        return zuEntnehmendeKarten;
    }

    public void kartenMischen() {
        Collections.shuffle(this.karten);
    }

    public LinkedList<Karte> getKarten() {
        return karten;
    }
}


