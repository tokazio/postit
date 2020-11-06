package fr.tokazio.postit;

import java.util.Arrays;
import java.util.List;

public interface Categories {

    Category NONE = new Category("NONE", "Aucune");
    Category ARRETER = new Category("ARRETER", "Arrêter de");
    Category CONTINUER = new Category("CONTINUER", "Continuer à");
    Category MOINS = new Category("MOINS", "Faire moins");
    Category PLUS = new Category("PLUS", "Faire plus");
    Category COMMENCER = new Category("COMMENCER", "Commencer à");

    List<Category> ALL = Arrays.asList(ARRETER, CONTINUER, MOINS, PLUS, COMMENCER);

    static Category from(final Category category) {
        for (Category c : ALL) {
            if (c.getValue().equals(category.getValue())) {
                return c;
            }
        }
        return NONE;
    }
}
