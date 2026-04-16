package ink.quietly.elementary_archery.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.entity.HeavyArrow;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class HeavyArrowRenderer extends ArrowRenderer<HeavyArrow, ArrowRenderState> {
	public static final Identifier HEAVY_ARROW_LOCATION = ElementaryArchery.id("textures/entity/projectile/arrow_heavy.png");

	public HeavyArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return HEAVY_ARROW_LOCATION;
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
