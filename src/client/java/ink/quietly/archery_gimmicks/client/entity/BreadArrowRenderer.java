package ink.quietly.archery_gimmicks.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class BreadArrowRenderer extends AncientArrowRenderer {
	public static final Identifier BREAD_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_bread.png");

	public BreadArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new BreadArrowModel(context.bakeLayer(BestiarySketches.BREAD_ARROW));
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return BREAD_ARROW_LOCATION;
	}

	@Override
	public void submit(ArrowRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(0.5f, 0.5f, 0.5f);
		super.submit(state, poseStack, submitNodeCollector, camera);
		poseStack.popPose();
	}
}
