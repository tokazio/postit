package fr.tokazio.postit.exceptions;

import fr.tokazio.postit.api.ResponseCode;

public class CantLikeAPostitIOwnException extends PostItException {

    public CantLikeAPostitIOwnException() {
        super("You can't like your own postit");
    }

    @Override
    public int getResponseCode() {
        return ResponseCode.BAD_ARGUMENT;
    }
}
