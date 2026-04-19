package ink.quietly.elementary_archery.client.entity;

import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.entity.SignalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class SignalArrowRenderer extends ArrowRenderer<SignalArrow, ArrowRenderState> {
	public static final Identifier SIGNAL_ARROW_LOCATION = ElementaryArchery.id("textures/entity/projectile/arrow_signal.png");

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
