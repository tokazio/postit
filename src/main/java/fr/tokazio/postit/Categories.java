package fr.tokazio.postit;

import java.util.Arrays;
import java.util.List;

public interface Categories {

    Categorie NONE = new Categorie("NONE", "Aucune");
    Categorie ARRETER = new Categorie("ARRETER", "Arrêter de");
    Categorie CONTINUER = new Categorie("CONTINUER", "Continuer à");
    Categorie MOINS = new Categorie("MOINS", "Faire moins");
    Categorie PLUS = new Categorie("PLUS", "Faire plus");
    Categorie COMMENCER = new Categorie("COMMENCER", "Commencer à");

    List<Categorie> ALL = Arrays.asList(ARRETER, CONTINUER, MOINS, PLUS, COMMENCER);

    static Categorie from(Categorie categorie) {
        for (Categorie c : ALL) {
            if (c.getValue().equals(categorie.getValue())) {
                return c;
            }
        }
        return NONE;
    }
}
