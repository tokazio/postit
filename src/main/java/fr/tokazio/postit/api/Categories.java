package fr.tokazio.postit.api;

import fr.tokazio.postit.Category;


public interface Categories {

    Category NONE = new Category("NONE", "Aucune");
    Category ARRETER = new Category("ARRETER", "Arrêter de");
    Category CONTINUER = new Category("CONTINUER", "Continuer à");
    Category MOINS = new Category("MOINS", "Faire moins");
    Category PLUS = new Category("PLUS", "Faire plus");
    Category COMMENCER = new Category("COMMENCER", "Commencer à");

}
