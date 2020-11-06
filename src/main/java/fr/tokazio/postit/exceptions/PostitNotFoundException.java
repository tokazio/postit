package fr.tokazio.postit.exceptions;

import fr.tokazio.postit.api.ResponseCode;

public class PostitNotFoundException extends PostItException {

    public PostitNotFoundException() {
        super("Postit not found");
    }

    @Override
    public int getResponseCode() {
        return ResponseCode.NOT_FOUND;
    }
}
