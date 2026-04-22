package ink.quietly.archery_gimmicks.client.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SignalParticle extends SingleQuadParticle {
	protected SignalParticle(
		final ClientLevel level, final double x, final double y, final double z, final double xd, final double yd, final double zd, final TextureAtlasSprite sprite
	) {
		super(level, x, y, z, sprite);
		this.lifetime = 19;
		this.quadSize = 7;
		this.friction = 1f;
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;
	}

	@Override
	protected @NonNull Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	@Override
	public void extract(@NonNull QuadParticleRenderState particleTypeRenderState, @NonNull Camera camera, float partialTickTime) {
//		this.setAlpha(this.age >= 5 ? 0.2f - 0.04f * (this.age + partialTickTime - 5) : 0.04f * (this.age + partialTickTime));
		this.setAlpha(Math.clamp(0.05f * Mth.sin(Math.PI * (this.age + partialTickTime) / 10) + 0.75f, 0.7f, 0.8f));
		super.extract(particleTypeRenderState, camera, partialTickTime);
	}

	@Override
	public float getQuadSize(float a) {
		return super.getQuadSize(a);
//		return Mth.sin((this.age + a) * 0.6F * (float) Math.PI) + Mth.sin((this.age + a) * 0.6F * (float) Math.PI) + 6;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public @Nullable Particle createParticle(SimpleParticleType options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
			SignalParticle particle = new SignalParticle(level, x, y, z, xAux, yAux, zAux, this.spriteSet.get(random));
			particle.setColor(1f, 0.3f, 0f);
			particle.setAlpha(0.8f);
			return particle;
		}
	}

	@Override
	protected int getLightCoords(float a) {
		return 15728880;
	}
}
