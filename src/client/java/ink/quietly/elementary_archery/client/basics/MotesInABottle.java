package ink.quietly.elementary_archery.client.basics;

import ink.quietly.elementary_archery.basics.MoteCatalog;
import ink.quietly.elementary_archery.client.particle.SignalParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

public class MotesInABottle {
	public static void shake() {
		ParticleProviderRegistry.getInstance().register(MoteCatalog.SIGNAL_PARTICLE, SignalParticle.Provider::new);
	}
}
