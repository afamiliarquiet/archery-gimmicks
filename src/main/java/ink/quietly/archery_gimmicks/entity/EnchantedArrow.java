package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.ItemBag;
import ink.quietly.archery_gimmicks.mixin.AbstractArrowAccessor;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class EnchantedArrow extends AbstractArrow implements AlteredArrow {
	/// enchanted arrows can only summon an ancient arrow once. this flips to true once it has summoned one.
	private boolean heavensFallingDown = false;

	public EnchantedArrow(EntityType<? extends EnchantedArrow> type, Level level) {
		super(type, level);
	}

	public EnchantedArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.ENCHANTED_ARROW, owner, level, pickupItemStack, firedFromWeapon);
		this.pickup = Pickup.DISALLOWED;
	}

	public EnchantedArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.ENCHANTED_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
		this.pickup = Pickup.DISALLOWED;
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.ENCHANTED_ARROW.getDefaultInstance();
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= 260) { // thirteen seconds?
			this.discard();
		}
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		callDownTheHeavens();
		super.onHitBlock(hitResult);
	}

	@Override
	public void beforeEntityHitDiscard(EntityHitResult hitResult) {
		callDownTheHeavens();
	}

	public void callDownTheHeavens() {
		if (!heavensFallingDown && this.level() instanceof ServerLevel serverLevel) {
			heavensFallingDown = true;
			// whether to \ or /
//			int slant = serverLevel.getRandom().nextBoolean() ? -1 : 1;
//			for (int i = -1; i <= 1; i ++) {
//				Vec3 offset = new Vec3(i, 0, i * slant).scale(16);
				Vec3 target = this.position()/*.add(offset)*/;

				Vec3 startingPoint = this.getDeltaMovement()
					.normalize()
					.reverse()
					.scale(ArcheryGimmicks.CONFIG.ancientArrowCallingRadius)
					.with(Direction.Axis.Y, 0)
					.add(target)
					.add(0, ArcheryGimmicks.CONFIG.ancientArrowCallingHeight, 0);
				Vec3 flightPath = target.subtract(startingPoint);

				AncientArrow arrow = AncientArrow.sendFromTheHeavens(level(), startingPoint.x, startingPoint.y, startingPoint.z, this.getPickupItem(), this.getWeaponItem());
				arrow.setOwner(this.getOwner());
				arrow.pickup = Pickup.DISALLOWED;
				Projectile.spawnProjectileUsingShoot(
					arrow,
					serverLevel,
					this.getPickupItem(),
					flightPath.x, flightPath.y, flightPath.z,
					ArcheryGimmicks.CONFIG.ancientArrowCallingPower, 0
				);
//			}
		}
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("heavensFallingDown", heavensFallingDown);
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		heavensFallingDown = input.getBooleanOr("heavensFallingDown", false); // remade in heaven
	}
}
