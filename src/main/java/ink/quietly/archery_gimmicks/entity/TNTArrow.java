package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.ItemBag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TNTArrow extends AbstractArrow implements AlteredArrow {
	private float explosionPowerBase = 0.0f;
	private float explosionSpeedFactor = 0.75f;
//	private boolean die = false;

	public TNTArrow(EntityType<? extends TNTArrow> type, Level level) {
		super(type, level);
	}

	public TNTArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.TNT_ARROW, owner, level, pickupItemStack, firedFromWeapon);
	}

	public TNTArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.TNT_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
	}
	
	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.TNT_ARROW.getDefaultInstance();
	}

//	@Override
//	protected void onHitEntity(@NonNull EntityHitResult hitResult) {
//		super.onHitEntity(hitResult);
//	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		double speedSqr = this.getDeltaMovement().lengthSqr();
		super.onHitBlock(hitResult);

		if (speedSqr > 4) {
			explode(speedSqr);
		}
	}

	@Override
	public void beforeEntityHitDiscard(EntityHitResult hitResult) {
		double speedSqr = this.getDeltaMovement().lengthSqr();
		if (speedSqr > 5) { // squishy
			explode(speedSqr);
		}/* else if (this.level() instanceof ServerLevel serverLevel && this.getRandom().nextBoolean()){
			this.spawnAtLocation(serverLevel, Items.TNT.getDefaultInstance());
		}*/
	}

//	@Override
//	public void onExplosionHit(@Nullable Entity explosionCausedBy) {
//		super.onExplosionHit(explosionCausedBy);
//		die = true;
//	}
//
//	@Override
//	public void tick() {
//		super.tick();
//		if (die) {
//			explode(this.getDeltaMovement().lengthSqr());
//		}
//	}

	protected void explode(double speedSqr) {
		if (this.level() instanceof ServerLevel level) {
			if (level.getGameRules().get(GameRules.TNT_EXPLODES)) {
				DamageSource damageSource = this.damageSources().explosion(this, this.getOwner());
				double speed = Math.clamp(Math.sqrt(speedSqr), 2.0, 64); // even 64 is absurd, but just for safety
				level.explode(
					this,
					damageSource,
					null,
					this.getX(),
					this.getY(),
					this.getZ(),
					(float)(this.explosionPowerBase + this.explosionSpeedFactor * 1.5 * speed),
					false,
					Level.ExplosionInteraction.TNT
				);
				this.discard();
			}
		}
	}
}
