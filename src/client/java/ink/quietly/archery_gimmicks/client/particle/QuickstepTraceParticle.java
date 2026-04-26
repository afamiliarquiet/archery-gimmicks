package ink.quietly.archery_gimmicks.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class QuickstepTraceParticle extends SingleQuadParticle {

	protected QuickstepTraceParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, TextureAtlasSprite sprite) {
		super(level, x+vx*2, y+vy*2, z+vz*2, 0, 0, 0, sprite);

		this.xd = vx;
		this.yd = vy;
		this.zd = vz;

		this.gravity = 0;
		this.friction = 0.99f;

		this.lifetime += 13;
	}

	@Override
	protected @NonNull Layer getLayer() {
		return Layer.OPAQUE;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public @Nullable Particle createParticle(SimpleParticleType options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
			QuickstepTraceParticle mote = new QuickstepTraceParticle(
				level,
				x, y, z,
				0.025 - random.nextDouble() * 0.05,
				0,
				0.025 - random.nextDouble() * 0.05,
				spriteSet.first()
			);
//			mote.setAlpha(random.nextFloat() * 0.5f + 0.4f);
			float gray = random.nextFloat() * 0.2f + 0.7f;
			mote.setColor(gray, gray, gray);
			mote.scale(random.nextFloat() * 0.3f + 1f);
			return mote;
		}
	}
}
