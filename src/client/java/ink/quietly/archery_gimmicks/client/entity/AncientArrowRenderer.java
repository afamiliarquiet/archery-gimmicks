package ink.quietly.archery_gimmicks.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.entity.AncientArrow;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class AncientArrowRenderer extends EntityRenderer<AncientArrow, ArrowRenderState> {
	public static final Identifier ANCIENT_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_ancient.png");

	protected ArrowModel model;

	public AncientArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new AncientArrowModel(context.bakeLayer(BestiarySketches.ANCIENT_ARROW));
	}

	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return ANCIENT_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	public void submit(ArrowRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(100f, 100f, 100f);
//		poseStack.translate(0, 0, 0);

		// ArrowRenderer
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
		submitNodeCollector.order(1).submitModel(
			this.model, state, poseStack, this.getTextureLocation(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
		);
		// for glint to not look super weird, need to make BiggerArrow's model not 0px thick.
		// time to make a fancy model for it, i suppose. make texel scale 0.5 and give it a "half pixel" width.. 64x texture then i suppose
//		submitNodeCollector.order(2).submitModel(model, state, poseStack, RenderTypes.entityGlint(), state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);

		poseStack.popPose();
	}

	public void extractRenderState(final AncientArrow entity, final ArrowRenderState state, final float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.shake = entity.shakeTime - partialTicks;
	}

	@Override
	protected boolean affectedByCulling(AncientArrow entity) {
		// could probably do math for getBoundingBoxForCulling...
		// or could just not cull it.
		// if you're summoning enough of these for that to be a problem, that's on you
		return false;
	}
}
