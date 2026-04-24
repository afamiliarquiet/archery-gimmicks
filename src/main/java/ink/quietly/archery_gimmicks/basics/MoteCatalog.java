package ink.quietly.archery_gimmicks.basics;

import com.mojang.serialization.MapCodec;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public class MoteCatalog {
	public static final ParticleType<ColorParticleOption> SIGNAL_PARTICLE = register("signal", false, ColorParticleOption::codec, ColorParticleOption::streamCodec);

	public static void peruse() {

	}

	private static <T extends ParticleType<?>> T register(String name, T particle) {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ArcheryGimmicks.id(name), particle);
		return particle;
	}

	@SuppressWarnings("SameParameterValue")
	private static <T extends ParticleOptions> ParticleType<T> register(
		final String name,
		final boolean overrideLimiter,
		final Function<ParticleType<T>, MapCodec<T>> codec,
		final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec
	) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, ArcheryGimmicks.id(name), new ParticleType<T>(overrideLimiter) {
			@Override
			public @NonNull MapCodec<T> codec() {
				return codec.apply(this);
			}

			@Override
			public @NonNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return streamCodec.apply(this);
			}
		});
	}
}
