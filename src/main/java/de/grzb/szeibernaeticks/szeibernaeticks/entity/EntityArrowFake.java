package de.grzb.szeibernaeticks.szeibernaeticks.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityArrowFake extends EntityTippedArrow {

    private boolean hitSomething = false;
    private Entity target;

    public EntityArrowFake(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        world = worldIn;
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHit(RayTraceResult rayTraceResultIn) {
        Type type = rayTraceResultIn.typeOfHit;

        if(type != Type.MISS) {
            this.hitSomething = true;
        }

        if(type == Type.ENTITY) {
            target = rayTraceResultIn.entityHit;
            target.setGlowing(true);
        }
        else if(type == Type.BLOCK) {
            BlockPos blockpos = rayTraceResultIn.getBlockPos();

            target = new EntityBlockMarker(getEntityWorld());
            target.setPosition(blockpos.getX() + 0.5, blockpos.getY(), blockpos.getZ() + 0.5);
            target.setGlowing(true);

            this.getEntityWorld().spawnEntity(target);
            // TODO: Give BlockMarker a Model
        }
    }

    @Override
    public void onUpdate() {
        if(hitSomething) {
            this.setDead();
            if(target != null) {
                target.setGlowing(false);
            }
            return;
        }

        while(!hitSomething) {
            super.onUpdate();
        }
    }
}
