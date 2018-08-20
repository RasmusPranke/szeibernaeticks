package main.de.grzb.szeibernaeticks.szeibernaeticks;

import org.codehaus.plexus.util.StringUtils;

public final class SzeibernaetickIdentifier {
    private String identifier;
    private String modId;

    public SzeibernaetickIdentifier(String modId, String identifier) {
        if(!StringUtils.isAlphanumeric(identifier)) {
            throw new IllegalArgumentException("The identifier must be alphanumeric!");
        }
        this.modId = modId;
        this.identifier = identifier;
    }

    public String getFullIdentifier() {
        return modId + ":" + identifier;
    }

    public String getShortIdentifier() {
        return identifier;
    }

    public static SzeibernaetickIdentifier fromString(String from) {
        int divideAt = from.lastIndexOf(':');
        String identifier = from.substring(divideAt) + 1;
        String modId = from.substring(0, divideAt);
        // The : is removed
        return new SzeibernaetickIdentifier(modId, identifier);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SzeibernaetickIdentifier) {
            SzeibernaetickIdentifier sId = (SzeibernaetickIdentifier) obj;
            return modId.equals(sId.modId) && identifier.equals(sId.identifier);
        }
        return false;
    }
}
