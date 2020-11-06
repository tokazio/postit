package fr.tokazio.postit.api;

import fr.tokazio.postit.Postit;

import java.io.IOException;
import java.util.List;

public interface Saver {

    void save(String filename, List<Postit> items) throws IOException;

    List<Postit> load(String filename) throws IOException;

}
