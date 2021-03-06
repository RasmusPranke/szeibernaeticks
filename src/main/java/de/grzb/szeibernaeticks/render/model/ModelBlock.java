package de.grzb.szeibernaeticks.render.model;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBlock extends ModelBase {

    public ModelRenderer block;

    public ModelBlock() {
        block = new ModelRenderer(this, 0, 0);
        block.addBox(0.5f, 0.5f, 0.5f, 1, 1, 1);
        block.setTextureSize(16, 16);
        Log.log("Creating Block Model!", LogType.RENDER, LogType.DEBUG);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Log.log("Rendering Block Model!", LogType.RENDER, LogType.DEBUG);
        block.render(scale);
    }
}
