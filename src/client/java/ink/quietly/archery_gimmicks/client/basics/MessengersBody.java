package ink.quietly.archery_gimmicks.client.basics;

import ink.quietly.archery_gimmicks.basics.MoteCatalog;
import ink.quietly.archery_gimmicks.network.S2CFlingPayload;
import ink.quietly.archery_gimmicks.network.S2CVanishPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

// i don't believe it.. they shot the messenger !!!
public class MessengersBody {
	public static void bury() {
		ClientPlayNetworking.registerGlobalReceiver(S2CFlingPayload.TYPE, (payload, context) -> {
			context.player().push(payload.fling());
		});

		ClientPlayNetworking.registerGlobalReceiver(S2CVanishPayload.TYPE, (payload, context) -> {
			Entity grabbed = context.player().level().getEntity(payload.playerId());
			if (grabbed instanceof Player player) {
				for (int i = 0; i < 20; i++) {
//					double xa = player.getRandom().nextGaussian() * 0.02;
//					double ya = player.getRandom().nextGaussian() * 0.02;
//					double za = player.getRandom().nextGaussian() * 0.02;
//					double dd = 10.0;
					player.level().addParticle(MoteCatalog.QUICKSTEP_TRACE, player.getRandomX(0.5)/* - xa * dd*/, player.getRandomY()/* - ya * dd*/, player.getRandomZ(0.5) /*- za * dd*/, 0/*xa*/, 0/*ya*/, 0/*za*/);
				}
			}
		});
	}
}
