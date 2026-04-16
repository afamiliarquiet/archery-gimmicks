package ink.quietly.elementary_archery.client.basics;

import ink.quietly.elementary_archery.network.S2CFlingPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

// i don't believe it.. they shot the messenger !!!
public class MessengersBody {
	public static void bury() {
		ClientPlayNetworking.registerGlobalReceiver(S2CFlingPayload.TYPE, (payload, context) -> {
			context.player().push(payload.fling());
		});
	}
}
