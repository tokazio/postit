package fr.tokazio.postit.exceptions;

import fr.tokazio.postit.api.ResponseCode;

public class NoUserException extends PostItException {
    public NoUserException() {
        super("You doesn't provide a user");
    }

    @Override
    public int getResponseCode() {
        return ResponseCode.BAD_ARGUMENT;
    }
}
