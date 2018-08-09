package de.grzb.szeibernaeticks.render;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.render.model.ModelBlock;
import de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBlockMarker extends RenderEntity {

    protected ModelBase model;
    private ResourceLocation transparentTexture;

    protected RenderBlockMarker(RenderManager renderManager) {
        super(renderManager);
        Log.log("Creating Block Marker Renderer!", LogType.RENDER, LogType.DEBUG);
        model = new ModelBlock();
        transparentTexture = new ResourceLocation(
                Szeibernaeticks.MOD_ID, "textures/entity/blockMarker/transparentTexture.png");
        bindTexture(transparentTexture);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return transparentTexture;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        Log.log("Rendering " + toString(), LogType.RENDER, LogType.SPAMMY, LogType.DEBUG);
        /*
         * GL11.glPushMatrix(); GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
         * model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1F);
         * GL11.glPopMatrix();
         */
    }

}
