package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.network.C2SFaithPayload;
import ink.quietly.archery_gimmicks.network.S2CFlingPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class Messenger {
	public static void summon() {
		PayloadTypeRegistry.clientboundPlay().register(S2CFlingPayload.TYPE, S2CFlingPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(C2SFaithPayload.TYPE, C2SFaithPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(C2SFaithPayload.TYPE, C2SFaithPayload::handle);
	}
}
