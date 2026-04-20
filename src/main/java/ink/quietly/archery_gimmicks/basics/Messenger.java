package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.network.S2CFlingPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class Messenger {
	public static void summon() {
		PayloadTypeRegistry.clientboundPlay().register(S2CFlingPayload.TYPE, S2CFlingPayload.CODEC);
	}
}
