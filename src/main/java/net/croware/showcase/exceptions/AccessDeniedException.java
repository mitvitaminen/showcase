package net.croware.showcase.exceptions;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException() {
        super("foo");
    }

}
