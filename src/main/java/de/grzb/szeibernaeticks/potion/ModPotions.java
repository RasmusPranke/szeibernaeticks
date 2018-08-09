package de.grzb.szeibernaeticks.potion;

import de.grzb.szeibernaeticks.CommonProxy;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers all potion effects during pre-init.
 *
 * @author yuri
 * @see CommonProxy CommonProxy
 */
public class ModPotions {
    public static IForgeRegistry<Potion> potionRegistry;

    public static PotionRejection potionRejection;

    public static void init() {
        ModPotions.potionRegistry = GameRegistry.findRegistry(Potion.class);

        potionRejection = register(new PotionRejection());
    }

    /**
     * Registers a potion effect.
     *
     * @param potion
     * @return {@link Potion}
     */
    private static <T extends Potion> T register(T potion) {
        ModPotions.potionRegistry.register(potion);
        return potion;
    }
}
