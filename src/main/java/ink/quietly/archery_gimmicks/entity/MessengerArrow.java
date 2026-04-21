package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.ItemBag;
import ink.quietly.archery_gimmicks.mixin.AbstractArrowAccessor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MessengerArrow extends AbstractArrow {
	public MessengerArrow(EntityType<? extends MessengerArrow> type, Level level) {
		super(type, level);
	}

	public MessengerArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.MESSENGER_ARROW, owner, level, pickupItemStack, firedFromWeapon);
		// let creative players fire gifts. shouldn't affect infinity arrows
		if (this.pickup == Pickup.CREATIVE_ONLY && owner.hasInfiniteMaterials()) {
			this.pickup = Pickup.ALLOWED;
			ItemStack pickupItem = this.getPickupItem();
			pickupItem.remove(DataComponents.INTANGIBLE_PROJECTILE);
			this.setPickupItemStack(pickupItem);
		}
	}

	public MessengerArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.MESSENGER_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.MESSENGER_ARROW.getDefaultInstance();
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= 1200) { // could also make the arrow take 4 more minutes to despawn instead. i think i prefer this way? could do both as well.
			BundleContents contents = this.getPickupItem().get(DataComponents.BUNDLE_CONTENTS);
			if (contents != null) {
				this.getPickupItem().set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
				if (!level().isClientSide()) {
					contents.itemCopyStream().forEach(stack -> level().addFreshEntity(new ItemEntity(level(), getX(), getY(), getZ(), stack)));
				}
			}
			this.discard();
		}
	}

	// kinda a sneaky trick to avoid the arrow stuck in entity thing
	@Override
	public byte getPierceLevel() {
		return 100;
	}

	@Override
	protected void onHitEntity(@NonNull EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (!this.isRemoved()) {
			this.deflect(ProjectileDeflection.REVERSE, hitResult.getEntity(), this.owner, false);
			this.setDeltaMovement(this.getDeltaMovement().scale(0.2));
			if (this.level() instanceof ServerLevel level && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
				if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
					this.spawnAtLocation(level, this.getPickupItem(), 0.1F);
				}

				this.discard();
			}
		}
	}
}
