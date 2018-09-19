package main.de.grzb.szeibernaeticks.szeibernaeticks.overlay;

import net.minecraft.client.gui.GuiScreen;

public abstract class SwitchGui extends GuiScreen {

    /*
     * protected void renderSwitch(int x, int y, float partialTicks,
     * EntityPlayer player, Switch $switch) {
     * 
     * this.mc.getTextureManager().bindTexture($switch.getSpriteResource());
     * 
     * float f = $switch.getAnimationsToGo() - partialTicks;
     * 
     * if(f > 0.0F) { GlStateManager.pushMatrix(); float f1 = 1.0F + f / 5.0F;
     * GlStateManager.translate(x + 8, y + 12, 0.0F); GlStateManager.scale(1.0F
     * / f1, (f1 + 1.0F) / 2.0F, 1.0F); GlStateManager.translate((-(x + 8)),
     * (-(y + 12)), 0.0F); }
     * 
     * this.itemRender.renderItemAndEffectIntoGUI(player, $switch, x, y);
     * 
     * if(f > 0.0F) { GlStateManager.popMatrix(); }
     * 
     * this.itemRender.renderItemOverlays(this.mc.fontRenderer, $switch, x, y);
     * }
     * 
     * public SwitchGui(Minecraft mc) { this.itemRender = mc.getRenderItem();
     * this.mc = mc; IArmoury armoury =
     * mc.player.getCapability(ArmouryProvider.ARMOURY_CAP, null);
     * 
     * if(armoury != null) { ConductiveVeins veins = (ConductiveVeins)
     * armoury.getSzeibernaetick(ConductiveVeins.class); if(veins != null) { int
     * cEnergy = veins.getCurrentEnergy(); int mEnergy = veins.getMaxEnergy();
     * 
     * ScaledResolution scaled = new ScaledResolution(mc); int width =
     * scaled.getScaledWidth(); // int height = scaled.getScaledHeight();
     * 
     * drawCenteredString(mc.fontRenderer, cEnergy + "/" + mEnergy, width / 2,
     * 0, Integer.parseInt("FFAA00", 16)); } }
     * 
     * float partialTicks = 10; ResourceLocation WIDGETS_TEX_PATH = null;
     * ScaledResolution sr = new ScaledResolution(mc);
     * 
     * GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
     * mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH); EntityPlayer
     * entityplayer = (EntityPlayer) mc.getRenderViewEntity(); ItemStack
     * itemstack = entityplayer.getHeldItemOffhand(); EnumHandSide enumhandside
     * = entityplayer.getPrimaryHand().opposite(); int i = sr.getScaledWidth() /
     * 2; float f = this.zLevel; int j = 182; int k = 91; this.zLevel = -90.0F;
     * this.drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182,
     * 22); this.drawTexturedModalRect(i - 91 - 1 +
     * entityplayer.inventory.currentItem * 20, sr.getScaledHeight() - 22 - 1,
     * 0, 22, 24, 22);
     * 
     * if(!itemstack.isEmpty()) { if(enumhandside == EnumHandSide.LEFT) {
     * this.drawTexturedModalRect(i - 91 - 29, sr.getScaledHeight() - 23, 24,
     * 22, 29, 24); } else { this.drawTexturedModalRect(i + 91,
     * sr.getScaledHeight() - 23, 53, 22, 29, 24); } }
     * 
     * this.zLevel = f; GlStateManager.enableRescaleNormal();
     * GlStateManager.enableBlend();
     * GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.
     * SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
     * GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
     * RenderHelper.enableGUIStandardItemLighting();
     * 
     * for(int l = 0; l < 9; ++l) { int i1 = i - 90 + l * 20 + 2; int j1 =
     * sr.getScaledHeight() - 16 - 3; this.renderSwitch(i1, j1, partialTicks,
     * entityplayer, entityplayer.inventory.mainInventory.get(l)); }
     * 
     * if(!itemstack.isEmpty()) { int l1 = sr.getScaledHeight() - 16 - 3;
     * 
     * if(enumhandside == EnumHandSide.LEFT) { this.renderSwitch(i - 91 - 26,
     * l1, partialTicks, entityplayer, itemstack); } else { this.renderSwitch(i
     * + 91 + 10, l1, partialTicks, entityplayer, itemstack); } }
     * 
     * if(mc.gameSettings.attackIndicator == 2) { float f1 =
     * mc.player.getCooledAttackStrength(0.0F);
     * 
     * if(f1 < 1.0F) { int i2 = sr.getScaledHeight() - 20; int j2 = i + 91 + 6;
     * 
     * if(enumhandside == EnumHandSide.RIGHT) { j2 = i - 91 - 22; }
     * 
     * mc.getTextureManager().bindTexture(Gui.ICONS); int k1 = (int) (f1 *
     * 19.0F); GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
     * this.drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
     * this.drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1); } }
     * 
     * RenderHelper.disableStandardItemLighting();
     * GlStateManager.disableRescaleNormal(); GlStateManager.disableBlend(); }
     */

}
