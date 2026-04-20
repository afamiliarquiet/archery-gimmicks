package ink.quietly.archery_gimmicks.client.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.entity.SignalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class SignalArrowRenderer extends ArrowRenderer<SignalArrow, ArrowRenderState> {
	public static final Identifier SIGNAL_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_signal.png");

	public SignalArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return SIGNAL_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}
}
