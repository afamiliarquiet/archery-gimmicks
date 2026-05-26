package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.Soundscape;
import ink.quietly.archery_gimmicks.mixin.AbstractArrowAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BreadArrow extends AncientArrow {
	public BreadArrow(EntityType<? extends AncientArrow> type, Level level) {
		super(type, level);
	}

	public BreadArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(Bestiary.BREAD_ARROW, level, owner, pickupItemStack, firedFromWeapon);
	}

	public BreadArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(Bestiary.BREAD_ARROW, level, x, y, z, pickupItemStack, firedFromWeapon);
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= 3600) { // 3 minutes
			this.discard();
		}
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		everyTimeYouDropABreadABugGainsItsWings();
		super.onHitBlock(hitResult);
	}

	@Override
	public void beforeEntityHitDiscard(EntityHitResult hitResult) {
		everyTimeYouDropABreadABugGainsItsWings();
	}

	public void everyTimeYouDropABreadABugGainsItsWings() {
		if (this.level() instanceof ServerLevel serverLevel) {
			// whether to \ or /
//			int slant = serverLevel.getRandom().nextBoolean() ? -1 : 1;
//			for (int i = -1; i <= 1; i ++) {
//				Vec3 offset = new Vec3(i, 0, i * slant).scale(16);
			Vec3 target = this.position()/*.add(offset)*/;

			Vec3 startingPoint = this.getDeltaMovement()
				.normalize()
				.reverse()
				.scale(ArcheryGimmicks.CONFIG.ancientArrowCallingRadius)
//					.with(Direction.Axis.Y, 0)
				.add(target)
				.add(0, ArcheryGimmicks.CONFIG.ancientArrowCallingHeight, 0);
			Vec3 flightPath = target.subtract(startingPoint);

			BugArrow arrow = new BugArrow(level(), startingPoint.x, startingPoint.y, startingPoint.z, this.getPickupItem(), this.getWeaponItem(), this.getUUID());
			arrow.setOwner(this.getOwner());
			arrow.pickup = Pickup.DISALLOWED;
			Projectile.spawnProjectileUsingShoot(
				arrow,
				serverLevel,
				this.getPickupItem(),
				flightPath.x, flightPath.y, flightPath.z,
				ArcheryGimmicks.CONFIG.ancientArrowCallingPower, 0
			);

			serverLevel.playSeededSound(
				null, this.getX(), this.getY(), this.getZ(), Soundscape.ANCIENT_CALLING, SoundSource.PLAYERS, 4F, 0.5F + this.random.nextFloat() * 0.1F, random.nextLong()
			);
			serverLevel.playSeededSound(null, position().x, position().y, position().z, Soundscape.ENCHANTED_ARROW_HIT, SoundSource.PLAYERS, 0.7f + random.nextFloat() * 0.1f, 0.9f + random.nextFloat() * 0.15f, random.nextLong());
//			}
		}
	}
}
