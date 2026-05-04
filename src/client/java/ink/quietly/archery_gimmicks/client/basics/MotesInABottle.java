package ink.quietly.archery_gimmicks.client.basics;

import ink.quietly.archery_gimmicks.basics.MoteCatalog;
import ink.quietly.archery_gimmicks.client.particle.QuickstepTraceParticle;
import ink.quietly.archery_gimmicks.client.particle.SignalParticle;
import ink.quietly.archery_gimmicks.client.particle.SmokeCloudParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

public class MotesInABottle {
	public static void shake() {
		ParticleProviderRegistry.getInstance().register(MoteCatalog.SIGNAL_PARTICLE, SignalParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(MoteCatalog.QUICKSTEP_TRACE, QuickstepTraceParticle.Provider::new);
		ParticleProviderRegistry.getInstance().register(MoteCatalog.SMOKE_CLOUD, SmokeCloudParticle.Provider::new);
	}
}
