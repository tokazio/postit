package fr.tokazio.postit;

import fr.tokazio.postit.api.Liking;
import fr.tokazio.postit.api.PostitService;
import fr.tokazio.postit.api.Saver;
import fr.tokazio.postit.exceptions.CantLikeAPostitIOwnException;
import fr.tokazio.postit.exceptions.NoUserException;
import fr.tokazio.postit.exceptions.PostitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PostitServiceImpl implements PostitService {

    public static final String LOAD_JSON = "saved.json";
    public static final String SAVE_JSON = "saved.json";
    private static final Logger LOGGER = LoggerFactory.getLogger(PostitService.class);
    private List<Postit> items = new CopyOnWriteArrayList<>();

    @Inject
    Saver saver;

    public PostitServiceImpl() {
        load();
    }

    @Override
    public Postit get(final String id) throws PostitNotFoundException {
        final Optional<Postit> found = items.stream().filter(p -> p.id.equals(id)).findAny();
        if (found.isEmpty()) {
            throw new PostitNotFoundException();
        }
        return found.get();
    }

    @Override
    public List<Postit> all() {
        return items;
    }

    @Override
    public Postit edit(final String id, final Postit postit) throws PostitNotFoundException {
        final Postit edited = get(id);
        edited.setText(postit.getText());
        edited.setCategorie(postit.getCategory());
        save();
        return edited;
    }

    @Override
    public Postit add(final Postit postit) {
        items.add(postit);
        LOGGER.info("Added: " + postit);
        save();
        return postit;
    }

    @Override
    public Postit delete(final String id) throws PostitNotFoundException {
        final Postit postit = get(id);
        items.remove(postit);
        save();
        return postit;
    }

    @Override
    public boolean save() {
        try {
            if (saver != null) {
                saver.save(SAVE_JSON, items);
            }
            return true;
        } catch (IOException e) {
            LOGGER.error("Error saving data", e);
        }
        return false;
    }

    @Override
    public boolean load() {
        try {
            if (saver != null) {
                items = saver.load(LOAD_JSON);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Error loading data", e);
        }
        return false;
    }

    @Override
    public Liking like(final String id, final User user) throws PostitNotFoundException, CantLikeAPostitIOwnException, NoUserException {
        if (user == null || user.getUser() == null || user.getUser().isEmpty()) {
            throw new NoUserException();
        }
        final Postit edited = get(id);
        if (edited.getUser().equals(user.getUser())) {
            throw new CantLikeAPostitIOwnException();
        }
        Liking out = Liking.UNLIKE;
        if (edited.likedBy(user.getUser())) {
            out = Liking.LIKE;
        }
        save();
        return out;
    }

    @Override
    public void clear() {
        items.clear();
    }
}
