package com.company.core.application.ViewModels;

import com.company.core.domain.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ViewData implements Serializable {

    public String[] handkarten;
    public String ablagekarte;
    public int spielerAmZug;

    public final static Map<Symbol, String> SYMBOL_MAP = new HashMap<Symbol, String>() {{
        put(Symbol.HERZ, "H");
        put(Symbol.PIK, "P");
        put(Symbol.KARO, "C");
        put(Symbol.KREUZ, "K");
    }};

    public final static Map<Rang, String> RANG_MAP = new HashMap<Rang, String>() {{
        put(Rang.ASS, "A");
        put(Rang.ZWEI, "2");
        put(Rang.DREI, "3");
        put(Rang.VIER, "4");
        put(Rang.FUENF, "5");
        put(Rang.SECHS, "6");
        put(Rang.SIEBEN, "7");
        put(Rang.ACHT, "8");
        put(Rang.NEUN, "9");
        put(Rang.ZEHN, "X");
        put(Rang.BUBE, "J");
        put(Rang.DAME, "D");
        put(Rang.KOENIG, "K");
    }};

    private ViewData(String[] handkarten, String ablagekarte, int positionAmZug) {
        this.handkarten = handkarten;
        this.ablagekarte = ablagekarte;
        this.spielerAmZug = positionAmZug;
    }

    public static ViewData zuSpieler(Spiel spiel, int spielerPosition) {
        Spieler spieler = spiel.getSpielerAnPosition(spielerPosition);
        Kartensammlung kartensammlung = spieler.getHandkarten();
        LinkedList<Karte> l = kartensammlung.getKarten();
        Object[] k = l.toArray();

        String[] handkarten2 = new String[k.length];

        for (int i = 0; i < k.length; i++) {
            handkarten2[i] = getKarteViewModel((Karte) k[i]);
        }

        String ablagekarte = getKarteViewModel(spiel.ablageStapel().getKarten().getLast());

        return new ViewData(handkarten2, ablagekarte, spiel.getPositionAmZug());
    }

    private static String getKarteViewModel(Karte karte) {
        return SYMBOL_MAP.get(karte.getSymbol()) + RANG_MAP.get(karte.getRang());
    }
}
