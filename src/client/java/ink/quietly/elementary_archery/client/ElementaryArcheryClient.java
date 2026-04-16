package ink.quietly.elementary_archery.client;

import ink.quietly.elementary_archery.client.basics.BestiarySketches;
import ink.quietly.elementary_archery.client.basics.MessengersBody;
import net.fabricmc.api.ClientModInitializer;

public class ElementaryArcheryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BestiarySketches.fill();
		MessengersBody.bury();
	}
}
