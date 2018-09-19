package main.de.grzb.szeibernaeticks.szeibernaeticks;

public class InvalidSzeibernaetickException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -9083528883833787202L;

    public InvalidSzeibernaetickException(Class<? extends ISzeibernaetick> cls, String message) {
        super("[" + cls.getSimpleName() + "] " + message);
    }
}
