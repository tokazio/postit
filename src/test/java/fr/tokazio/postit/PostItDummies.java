package fr.tokazio.postit;

public class PostItDummies {

    private PostItDummies() {
        super();
    }

    public static Postit postitOne() {
        final Postit postit = new Postit("one", userA().getUser(), Categories.COMMENCER);
        postit.id = "abcd";
        return postit;
    }

    public static Postit postitTwo() {
        final Postit postit = new Postit("two", userB().getUser(), Categories.ARRETER);
        postit.id = "efgh";
        postit.likedBy(userA().getUser());
        return postit;
    }

    public static Postit edition() {
        final Postit postit = postitTwo();
        postit.setText("two edited");
        postit.setCategorie(Categories.CONTINUER);
        postit.id = "efgh";
        return postit;
    }

    public static User userA() {
        return new User("A");
    }

    public static User userB() {
        return new User("B");
    }

}
