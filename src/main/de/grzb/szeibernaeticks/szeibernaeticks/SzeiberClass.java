package main.de.grzb.szeibernaeticks.szeibernaeticks;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.ISzeibernaetickEventHandler;

/**
 * Marks
 * 
 * @author DemRat
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface SzeiberClass {
    Class<? extends ISzeibernaetickEventHandler>[] handler();

    Class<? extends SzeibernaetickItem> item();

    @Retention(RUNTIME)
    @Target(FIELD)
    public @interface Identifier {

    }

    @Retention(RUNTIME)
    @Target(FIELD)
    public @interface ItemInject {

    }

}
