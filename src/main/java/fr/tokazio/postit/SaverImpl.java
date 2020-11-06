package fr.tokazio.postit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.tokazio.postit.api.Saver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class SaverImpl implements Saver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaverImpl.class);

    @Inject
    private ObjectMapper mapper;

    @Override
    public void save(final String filename, final List<Postit> items) throws IOException {
        final File f = new File(filename);
        if (!f.exists()) {
            f.createNewFile();
        }
        mapper.writeValue(f, items);
        LOGGER.info("saved " + items.size() + " to " + f.getAbsolutePath());
    }

    @Override
    public List<Postit> load(final String filename) throws IOException {
        final File f = new File(filename);
        if (f.exists()) {
            List<Postit> items = mapper.readValue(f, new TypeReference<List<Postit>>() {
            });
            LOGGER.info("loaded " + items.size() + " from " + f.getAbsolutePath());
            return items;
        }
        return new LinkedList<>();
    }
}
