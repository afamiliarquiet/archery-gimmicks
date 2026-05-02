package ink.quietly.archery_gimmicks.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.entity.EnchantedArrow;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class EnchantedArrowRenderer extends EntityRenderer<EnchantedArrow, ArrowRenderState> {
	public static final Identifier ENCHANTED_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_enchanted.png");

	private final ArrowModel model;

	public EnchantedArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new EnchantedArrowModel(context.bakeLayer(BestiarySketches.ENCHANTED_ARROW));
	}

	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return ENCHANTED_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	public void submit(ArrowRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
		// ArrowRenderer
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
		submitNodeCollector.order(1).submitModel(
			this.model, state, poseStack, this.getTextureLocation(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
		);
		submitNodeCollector.order(2).submitModel(model, state, poseStack, RenderTypes.entityGlint(), state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	public void extractRenderState(final EnchantedArrow entity, final ArrowRenderState state, final float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.shake = entity.shakeTime - partialTicks;
	}
}
