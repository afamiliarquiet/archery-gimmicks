package ink.quietly.archery_gimmicks.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.entity.WeightedArrow;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class WeightedArrowRenderer extends ArrowRenderer<WeightedArrow, ArrowRenderState> {
	public static final Identifier WEIGHTED_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_weighted.png");

	public WeightedArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return WEIGHTED_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	public void submit(ArrowRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(1.5f, 1.5f, 1.5f);
		super.submit(state, poseStack, submitNodeCollector, camera);
		poseStack.popPose();
	}
}
