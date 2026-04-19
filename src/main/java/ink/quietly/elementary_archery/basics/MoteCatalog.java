package ink.quietly.elementary_archery.basics;

import ink.quietly.elementary_archery.ElementaryArchery;
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
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ElementaryArchery.id(name), particle);
		return particle;
	}
}
