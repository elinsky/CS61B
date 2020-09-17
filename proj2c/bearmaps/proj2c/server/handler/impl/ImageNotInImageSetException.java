package bearmaps.proj2c.server.handler.impl;

public class ImageNotInImageSetException extends Exception {
    public ImageNotInImageSetException(String errorMessage) {
        super(errorMessage);
    }
}
