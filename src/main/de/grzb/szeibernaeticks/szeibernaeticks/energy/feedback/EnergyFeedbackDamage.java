package main.de.grzb.szeibernaeticks.szeibernaeticks.energy.feedback;

import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EnergyFeedbackDamage extends DamageSource {

    private ISzeibernaetick szeiber;

    public EnergyFeedbackDamage(ISzeibernaetick szeiberCause) {
        super("energyFeedback");
        this.setDamageBypassesArmor();
        this.szeiber = szeiberCause;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        String s = "death.attack." + this.damageType;
        return new TextComponentTranslation(s, entityLivingBaseIn.getDisplayName(), this.szeiber.getIdentifier());
    }
}
