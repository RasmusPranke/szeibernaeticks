package de.grzb.szeibernaeticks.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class FakeRender<T extends Entity> extends Render<T> {

    protected FakeRender(RenderManager renderManager) {
        super(renderManager);
    }

    // TODO: Try adding an entirely transparent Texture
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

}
