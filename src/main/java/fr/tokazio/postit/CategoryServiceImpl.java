package fr.tokazio.postit;

import fr.tokazio.postit.api.CategoryService;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

import static fr.tokazio.postit.api.Categories.*;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {

    private static final List<Category> ALL = Arrays.asList(ARRETER, CONTINUER, MOINS, PLUS, COMMENCER);

    @Override
    public List<Category> all() {
        return ALL;
    }

    public Category from(final Category category) {
        for (Category c : ALL) {
            if (c.getValue().equals(category.getValue())) {
                return c;
            }
        }
        return NONE;
    }
}
