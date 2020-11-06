package fr.tokazio.postit;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

/**
 * https://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html
 */
public class PostitAssert extends AbstractAssert<PostitAssert, Postit> {

    public PostitAssert(Postit actual) {
        super(actual, PostitAssert.class);
    }

    public static PostitAssert assertThat(Postit actual) {
        return new PostitAssert(actual);
    }

    public PostitAssert hasText(String text) {
        isNotNull();
        if (!Objects.equals(actual.getText(), text)) {
            failWithMessage("Expected postit's text to be <%s> but was <%s>", text, actual.getText());
        }
        return this;
    }

    public PostitAssert hasCategorie(Category category) {
        isNotNull();
        if (!Objects.equals(actual.getCategory(), category)) {
            failWithMessage("Expected postit's category to be <%s> but was <%s>", category, actual.getCategory());
        }
        return this;
    }
}
