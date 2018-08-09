package de.grzb.szeibernaeticks.szeibernaeticks.entity;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBlockMarker extends Entity {

    public EntityBlockMarker(World worldIn) {
        super(worldIn);
        Log.log("Creating Block Marker Entity!", LogType.INSTANTIATION, LogType.DEBUG);
    }

    @Override
    protected void entityInit() {
        this.isImmuneToFire = true;
        this.glowing = true;
        this.setNoGravity(true);
        this.setSilent(true);
        this.setSize(1, 1);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(ticksExisted > 1) {
            this.setDead();
        }
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

}
