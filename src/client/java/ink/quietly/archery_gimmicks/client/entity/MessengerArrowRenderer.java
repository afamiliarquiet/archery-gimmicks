package ink.quietly.archery_gimmicks.client.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.client.mixin.ArrowRendererAccessor;
import ink.quietly.archery_gimmicks.entity.MessengerArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class MessengerArrowRenderer extends ArrowRenderer<MessengerArrow, ArrowRenderState> {
	public static final Identifier MESSENGER_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_messenger.png");

	public MessengerArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		((ArrowRendererAccessor)this).setModel(new MessengerArrowModel(context.bakeLayer(BestiarySketches.MESSENGER_ARROW)));
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return MESSENGER_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}
}
