package shadows.apotheosis.ench.altar;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

@SuppressWarnings("deprecation")
public class SeaAltarRenderer extends TileEntityRenderer<SeaAltarTile> {

	public SeaAltarRenderer(TileEntityRendererDispatcher terd) {
		super(terd);
	}

	@Override
	public void render(SeaAltarTile te, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buf, int p_225616_5_, int p_225616_6_) {
		if (this.renderDispatcher.renderInfo != null && te.getPos().distanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z, true) < 128d) {

			matrix.push();
			boolean thirdPerson = Minecraft.getInstance().getRenderManager().options.func_243230_g() == PointOfView.THIRD_PERSON_FRONT;
			float viewerYaw = Minecraft.getInstance().renderViewEntity.getYaw(partialTicks);
			float angleRotateItem = !thirdPerson ? -viewerYaw : -viewerYaw % 360 + 180;

			double[][] offsets = { { 3 / 16D, 3 / 16D }, { 3 / 16D, 13 / 16D }, { 13 / 16D, 3 / 16D }, { 13 / 16D, 13 / 16D } };
			float scale = 0.2F;
			double yOffset = 0.75;

			for (int i = 0; i < 4; i++) {
				matrix.push();
				matrix.translate(offsets[i][0], yOffset, offsets[i][1]);
				matrix.rotate(new Quaternion(new Vector3f(0, 1, 0), angleRotateItem, true));
				matrix.scale(scale, scale, scale);
				ItemStack s = te.getInv().getStackInSlot(i);
				if (!s.isEmpty()) Minecraft.getInstance().getItemRenderer().renderItem(s, TransformType.FIXED, p_225616_5_, OverlayTexture.NO_OVERLAY, matrix, buf);
				matrix.pop();
			}

			if (!te.getInv().getStackInSlot(4).isEmpty()) {
				matrix.push();
				matrix.translate(0.5, 0.4, 0.5);
				matrix.rotate(new Quaternion(new Vector3f(0, 1, 0), angleRotateItem, true));
				matrix.scale(scale, scale, scale);
				ItemStack s = te.getInv().getStackInSlot(4);
				Minecraft.getInstance().getItemRenderer().renderItem(s, TransformType.FIXED, p_225616_5_, OverlayTexture.NO_OVERLAY, matrix, buf);
				matrix.pop();
			}

			matrix.pop();
		}
	}
}
