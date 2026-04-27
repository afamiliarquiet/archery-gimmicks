package ink.quietly.archery_gimmicks.client.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.client.mixin.ArrowRendererAccessor;
import ink.quietly.archery_gimmicks.entity.TNTArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class TNTArrowRenderer extends ArrowRenderer<TNTArrow, ArrowRenderState> {
	public static final Identifier TNT_ARROW_LOCATION = ArcheryGimmicks.id("textures/entity/projectile/arrow_tnt.png");

	public TNTArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
		((ArrowRendererAccessor)this).setModel(new TNTArrowModel(context.bakeLayer(BestiarySketches.TNT_ARROW)));
	}

	@Override
	protected @NonNull Identifier getTextureLocation(ArrowRenderState state) {
		return TNT_ARROW_LOCATION;
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}
}
