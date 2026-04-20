package ink.quietly.archery_gimmicks.client;

import ink.quietly.archery_gimmicks.client.basics.BestiarySketches;
import ink.quietly.archery_gimmicks.client.basics.MessengersBody;
import ink.quietly.archery_gimmicks.client.basics.MotesInABottle;
import net.fabricmc.api.ClientModInitializer;

public class ArcheryGimmicksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BestiarySketches.fill();
		MessengersBody.bury();
		MotesInABottle.shake();
	}
}
