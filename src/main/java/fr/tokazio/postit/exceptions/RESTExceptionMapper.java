package fr.tokazio.postit.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class RESTExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<PostItException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTExceptionMapper.class);

    @Override
    public Response toResponse(PostItException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return Response.status(exception.getResponseCode()).entity(exception.getClass().getName() + ": " + exception.getMessage()).build();
    }

}