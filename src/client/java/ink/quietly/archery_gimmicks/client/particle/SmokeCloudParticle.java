package ink.quietly.archery_gimmicks.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class SmokeCloudParticle extends BaseAshSmokeParticle {
	protected SmokeCloudParticle(
		final ClientLevel level,
		final double x,
		final double y,
		final double z,
		final double xa,
		final double ya,
		final double za,
		final float scale,
		final SpriteSet sprites
	) {
		super(level, x, y, z, (float) xa, (float) ya, (float) za, xa, ya, za, scale, sprites, 0F, 0, 0F, true);
		float greyizer = this.random.nextFloat() * 0.2f + 0.6f;

		this.lifetime = (int)(3 / (this.random.nextFloat() * 0.5 + 1.3) * scale);
		this.rCol = greyizer;
		this.gCol = greyizer;
		this.bCol = greyizer;
	}

	@Override
	public float getQuadSize(final float a) {
		return this.quadSize/* * Mth.clamp((this.age + a) / this.lifetime * 32.0F, 0.0F, 1.0F)*/;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(final SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(
			final SimpleParticleType options,
			final @NonNull ClientLevel level,
			final double x,
			final double y,
			final double z,
			final double xAux,
			final double yAux,
			final double zAux,
			final @NonNull RandomSource random
		) {
			return new SmokeCloudParticle(level, x, y, z, xAux, yAux, zAux, 30.0F, this.sprites);
		}
	}
}
