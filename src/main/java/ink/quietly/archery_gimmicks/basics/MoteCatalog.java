package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class MoteCatalog {
	public static final SimpleParticleType SIGNAL_PARTICLE = register("signal", FabricParticleTypes.simple());

	public static void peruse() {

	}

	private static <T extends ParticleType<?>> T register(String name, T particle) {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ArcheryGimmicks.id(name), particle);
		return particle;
	}
}
