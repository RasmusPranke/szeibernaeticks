package de.grzb.szeibernaeticks.render;

import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityArrowFake;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FakeRenderFactory implements IRenderFactory<EntityArrowFake> {

    @Override
    public Render<EntityArrowFake> createRenderFor(RenderManager manager) {
        return new FakeRender<>(manager);
    }

}
