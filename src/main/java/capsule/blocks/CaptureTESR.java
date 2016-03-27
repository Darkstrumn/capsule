package capsule.blocks;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;

public class CaptureTESR extends TileEntitySpecialRenderer<TileEntityCapture> {

	public CaptureTESR() {
	}

	@Override
	public void renderTileEntityAt(TileEntityCapture tileEntityCapture, double relativeX, double relativeY, double relativeZ,
			float partialTicks, int blockDamageProgress) {

		if (tileEntityCapture == null)
			return;

		int size = tileEntityCapture.getSize();
		if (size == 0)
			return;
		int extendSize = (size - 1) / 2;

		int color = tileEntityCapture.getColor();
		CaptureTESR.drawCaptureZone(relativeX, relativeY, relativeZ, size, extendSize, color);

	}

	public static void drawCaptureZone(double relativeX, double relativeY, double relativeZ, int size, int extendSize,
			int color) {
		Color c = new Color(color);
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		int alpha = 150;

		GL11.glLineWidth(3.0F);
		
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(relativeX, relativeY, relativeZ);

		AxisAlignedBB boundingBox = new AxisAlignedBB(-extendSize - 0.01, 1.01, -extendSize - 0.01,
				extendSize + 1.01, size + 1.01, extendSize + 1.01);
		
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

		GlStateManager.popMatrix();

		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		
		GL11.glLineWidth(1.0F);
	}
	
	

}