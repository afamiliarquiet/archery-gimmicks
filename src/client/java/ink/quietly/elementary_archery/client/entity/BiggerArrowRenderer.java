package ink.quietly.elementary_archery.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.client.basics.BestiarySketches;
import ink.quietly.elementary_archery.client.mixin.ArrowRendererAccessor;
import ink.quietly.elementary_archery.entity.BiggerArrow;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class BiggerArrowRenderer extends ArrowRenderer<BiggerArrow, ArrowRenderState> {
	public static final Identifier BIGGER_ARROW_LOCATION = ElementaryArchery.id("textures/entity/projectile/arrow_bigger.png");

	public BiggerArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		((ArrowRendererAccessor)this).setModel(new BiggerArrowModel(context.bakeLayer(BestiarySketches.BIGGER_ARROW)));
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return BIGGER_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	public void submit(ArrowRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(300f, 300f, 300f);
		poseStack.translate(0, 0, 0);
		super.submit(state, poseStack, submitNodeCollector, camera);
		poseStack.popPose();
	}

	@Override
	protected boolean affectedByCulling(BiggerArrow entity) {
		// could probably do math for getBoundingBoxForCulling...
		// or could just not cull it.
		// if you're summoning enough of these for that to be a problem, that's on you
		return false;
	}
}
