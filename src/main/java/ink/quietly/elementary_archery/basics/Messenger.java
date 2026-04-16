package ink.quietly.elementary_archery.basics;

import ink.quietly.elementary_archery.network.S2CFlingPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class Messenger {
	public static void summon() {
		PayloadTypeRegistry.clientboundPlay().register(S2CFlingPayload.TYPE, S2CFlingPayload.CODEC);
	}
}
