package ink.quietly.elementary_archery.client.basics;

import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.basics.Bestiary;
import ink.quietly.elementary_archery.client.entity.BiggerArrowModel;
import ink.quietly.elementary_archery.client.entity.BiggerArrowRenderer;
import ink.quietly.elementary_archery.client.entity.HeavyArrowRenderer;
import ink.quietly.elementary_archery.client.entity.SignalArrowRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class BestiarySketches {
	public static final ModelLayerLocation BIGGER_ARROW = createMainLayer("bigger_arrow");

	public static void fill() {
		EntityRenderers.register(Bestiary.HEAVY_ARROW, HeavyArrowRenderer::new);
		EntityRenderers.register(Bestiary.SIGNAL_ARROW, SignalArrowRenderer::new);
		EntityRenderers.register(Bestiary.BIGGER_ARROW, BiggerArrowRenderer::new);

		ModelLayerRegistry.registerModelLayer(BIGGER_ARROW, BiggerArrowModel::createBodyLayer);
	}

	private static ModelLayerLocation createMainLayer(String name) {
		return new ModelLayerLocation(ElementaryArchery.id(name), "main");
	}
}
