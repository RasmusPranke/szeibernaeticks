package de.grzb.szeibernaeticks.render;

import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityBlockMarker;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * @author DemRat
 *
 */
public class RenderBlockMarkerFactory implements IRenderFactory<EntityBlockMarker> {

    @Override
    public Render<Entity> createRenderFor(RenderManager manager) {
        return new RenderBlockMarker(manager);
    }

}
