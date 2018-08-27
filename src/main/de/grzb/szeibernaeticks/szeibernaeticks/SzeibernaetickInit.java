package main.de.grzb.szeibernaeticks.szeibernaeticks;

import java.io.InvalidClassException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import main.de.grzb.szeibernaeticks.item.ModItems;
import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.ArchersEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.ConductiveVeins;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.DynamoJoints;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.GeneratorStomach;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.MetalBones;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RadarEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RunnersLegs;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.SyntheticEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.ISzeibernaetickEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

public class SzeibernaetickInit {
    // TODO: This entire class is disgusting
    private static final String baseError = "Could not %s the %s for the Szeibernaetick %s.";

    public static void init() throws InvalidClassException {
        loadSzeibernaetick(MetalBones.class);
        loadSzeibernaetick(ConductiveVeins.class);
        loadSzeibernaetick(DynamoJoints.class);
        loadSzeibernaetick(SyntheticEyes.class);
        loadSzeibernaetick(GeneratorStomach.class);
        loadSzeibernaetick(ArchersEyes.class);
        loadSzeibernaetick(RadarEyes.class);
        loadSzeibernaetick(RunnersLegs.class);
    }

    public static void loadSzeibernaetick(Class<? extends ISzeibernaetick> szeiberClass) throws InvalidClassException {
        Szeibernaetick at = szeiberClass.getAnnotation(Szeibernaetick.class);

        SzeibernaetickIdentifier identifier = findContentOfStaticFieldWithTypeAndAnnotation(szeiberClass,
                SzeibernaetickIdentifier.class, Szeibernaetick.Identifier.class);

        Field itemInjectField = findFieldWithAnnotation(szeiberClass, Szeibernaetick.ItemInject.class);

        registerSzeibernaetick(szeiberClass, at.item(), at.handler(), identifier, itemInjectField);
    }

    private static Field findFieldWithAnnotation(Class<?> source, Class<? extends Annotation> at)
            throws InvalidClassException {
        Field ret = null;
        Field[] fields = source.getDeclaredFields();
        for(Field f : fields) {
            if(f.isAnnotationPresent(at)) {
                if(ret == null) {
                    ret = f;
                }
                else {
                    throw new InvalidClassException(
                            "Class " + source.getName() + " contains multiple fields with annotation " + at.toString());
                }
            }
        }

        if(ret == null) {
            throw new InvalidClassException(
                    "Class " + source.getName() + " contains no fields with annotation " + at.toString());
        }
        return ret;
    }

    private static <T> T findContentOfStaticFieldWithTypeAndAnnotation(Class<?> source, Class<T> type,
            Class<? extends Annotation> at) throws InvalidClassException {
        T ret = null;
        Field f = findFieldWithAnnotation(source, at);
        if(type.isAssignableFrom(f.getType())) {
            try {
                @SuppressWarnings("unchecked")
                T warningWrapper = (T) f.get(null);
                // We know that it is assignable due to the check
                // earlier.
                ret = warningWrapper;
            }
            catch(IllegalArgumentException | IllegalAccessException e) {
                throw new InvalidClassException("Class " + source.getName() + " contains a field with annotation "
                        + at.toString() + " of type " + type.getName() + " that is not public and static.");
            }
        }
        else {
            throw new InvalidClassException("Class " + source.getName() + " contains a field with annotation "
                    + at.toString() + " that is not of type " + type.getName() + " but is instead "
                    + f.getType().getName() + ".");
        }

        return ret;
    }

    private static void registerSzeibernaetick(Class<? extends ISzeibernaetick> szeiber,
            Class<? extends SzeibernaetickItem> itemClass, Class<? extends ISzeibernaetickEventHandler>[] handlers,
            SzeibernaetickIdentifier identifier, Field itemInjectField) throws InvalidClassException {

        int i = 0;
        try {
            while(i < handlers.length) {
                Class<? extends ISzeibernaetickEventHandler> h = handlers[i];
                MinecraftForge.EVENT_BUS.register(h.newInstance());
                i++;
            }
        }
        catch(InstantiationException e) {
            throw new InvalidClassException(String.format(baseError, "instantiate",
                    "handler " + handlers[i].getSimpleName(), szeiber.getSimpleName()));
        }
        catch(IllegalAccessException e) {
            throw new InvalidClassException(
                    String.format(baseError, "access", "handler " + handlers[i].getName(), szeiber.getName()));
        }

        SzeibernaetickItem itemInstance;
        try {
            itemInstance = itemClass.newInstance();
        }
        catch(InstantiationException e) {
            throw new InvalidClassException(
                    String.format(baseError, "instantiate", "item " + itemClass.getName(), szeiber.getName()));
        }
        catch(IllegalAccessException e) {
            throw new InvalidClassException(
                    String.format(baseError, "access", "item " + itemClass.getName(), szeiber.getName()));
        }

        ModItems.register(itemInstance);
        SzeibernaetickMapper.INSTANCE.register(szeiber, itemInstance, identifier);

        try {
            EnumHelper.setFailsafeFieldValue(itemInjectField, null, itemInstance);
        }
        catch(Exception e) {
            throw new InvalidClassException(
                    String.format(baseError, "inject", "item " + itemClass.getName(), szeiber.getName()));
        }
    }

}