package ink.quietly.elementary_archery.client.basics;

import ink.quietly.elementary_archery.basics.Bestiary;
import ink.quietly.elementary_archery.client.entity.HeavyArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class BestiarySketches {
	public static void fill() {
		EntityRenderers.register(Bestiary.HEAVY_ARROW, HeavyArrowRenderer::new);
	}
}
