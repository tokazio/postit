package fr.tokazio.postit.exceptions;

public abstract class PostItException extends Throwable {

    public PostItException(String message) {
        super(message);
    }

    public abstract int getResponseCode();

}
