package org.me.leo_s.particleletterns.components.exceptions;

public class TextFormattedInvalid extends Exception{
    public TextFormattedInvalid(String message) {
        super(message);
    }
    public TextFormattedInvalid(String message, Throwable cause) {
        super(message, cause);
    }
}
