package ink.quietly.archery_gimmicks.client.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.client.entity.*;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class BestiarySketches {
	public static final ModelLayerLocation MESSENGER_ARROW = createMainLayer("messenger_arrow");
	public static final ModelLayerLocation TNT_ARROW = createMainLayer("tnt_arrow");
	public static final ModelLayerLocation BIGGER_ARROW = createMainLayer("bigger_arrow");

	public static void fill() {
		EntityRenderers.register(Bestiary.WEIGHTED_ARROW, WeightedArrowRenderer::new);
		EntityRenderers.register(Bestiary.SIGNAL_ARROW, SignalArrowRenderer::new);
		EntityRenderers.register(Bestiary.MESSENGER_ARROW, MessengerArrowRenderer::new);
		EntityRenderers.register(Bestiary.TNT_ARROW, TNTArrowRenderer::new);
		EntityRenderers.register(Bestiary.BIGGER_ARROW, BiggerArrowRenderer::new);

		ModelLayerRegistry.registerModelLayer(MESSENGER_ARROW, MessengerArrowModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(TNT_ARROW, TNTArrowModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(BIGGER_ARROW, BiggerArrowModel::createBodyLayer);
	}

	private static ModelLayerLocation createMainLayer(String name) {
		return new ModelLayerLocation(ArcheryGimmicks.id(name), "main");
	}
}
