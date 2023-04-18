package me.h3xadecimal.bluearchivehalo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;

public class Renderer {
    @SubscribeEvent
    public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
        if (Configuration.type == HaloType.None) return;
        doRender((float) event.x, (float) event.y, (float) event.z, Configuration.type, event);
    }

    private void doRender(float x, float y, float z, HaloType type, RenderPlayerEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.entityPlayer != mc.thePlayer) return;

        ResourceLocation res = new ResourceLocation("halo", "std/" + type.getResource() + ".png");
        ResourceLocation reverse;
        if (type.isReverse()) {
            reverse = res;
        } else {
            reverse = new ResourceLocation("halo", "reverse/" + type.getResource() + ".png");
        }
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        float yOffset = new BigDecimal(Configuration.yOffset).floatValue()/10;

        mc.renderEngine.bindTexture(res);
        GlStateManager.pushMatrix();
        GlStateManager.translate(
                x,
                y + event.entityPlayer.height + yOffset,
                z
        );
        GlStateManager.rotate(90f, 1f, 0f, 0f);
        GlStateManager.rotate(-(180-event.entityPlayer.rotationYawHead), 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(Configuration.angle, 1f, 0f, 0f);
        GlStateManager.scale(type.getSize(), type.getSize(), type.getSize());
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, 0.0).tex(0.0, 0.0).endVertex();
        worldRenderer.pos(-1.0, 1.0, 0.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(1.0, 1.0, 0.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(1.0, -1.0, 0.0).tex(1.0, 0.0).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();

        mc.renderEngine.bindTexture(reverse);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y+event.entityPlayer.height+0.2, z);
        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180-event.entityPlayer.rotationYawHead, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(Configuration.angle, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(type.getSize(), type.getSize(), type.getSize());
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-1.0, -1.0, 0.0).tex(0.0, 0.0).endVertex();
        worldRenderer.pos(-1.0, 1.0, 0.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(1.0, 1.0, 0.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(1.0, -1.0, 0.0).tex(1.0, 0.0).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }
}
