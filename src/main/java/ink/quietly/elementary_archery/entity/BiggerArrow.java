package ink.quietly.elementary_archery.entity;

import ink.quietly.elementary_archery.mixin.AbstractArrowAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.List;

// and if that don't work, use more arrow
public class BiggerArrow extends AbstractArrow {
	public BiggerArrow(EntityType<? extends BiggerArrow> type, Level level) {
		super(type, level);
	}

//	public BiggerArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
//		super(Bestiary.BIGGER_ARROW, owner, level, pickupItemStack, firedFromWeapon);
//	}
//
//	public BiggerArrow(
//		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
//	) {
//		super(Bestiary.BIGGER_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
//	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return Items.ARROW.getDefaultInstance();
	}

	@Override
	public byte getPierceLevel() {
		return 100;
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= 36000) { // 30 minutes
			this.discard();
		}
	}

	@Override
	protected float getWaterInertia() {
		return 0.99f;
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		if (this.level().isClientSide()) {
			// todo - explodey particles probably. beware of render dist?
		}
	}

	@Override
	protected @NonNull Collection<EntityHitResult> findHitEntities(@NonNull Vec3 from, @NonNull Vec3 to) {
		// ignore default projectile entity hit handling, it's not good enough for bigger arrow
		return List.of();
	}

	@Override
	public void tick() {
		if (!this.isInGround()) {
			hitThemAll();
		}
		super.tick();
	}

	private void hitThemAll() {
		// because the movement here isn't clipped by block collision it can hit things through blocks.
		// this is okay it's a very big arrow
		if (this.level() instanceof ServerLevel serverLevel) {
			AABB minmax = this.getBoundingBox().minmax(this.getBoundingBox().move(this.getDeltaMovement()));
			List<Entity> entities = serverLevel.getEntities(this, minmax);
			for (Entity entity : entities) {
				if (entity instanceof LivingEntity livingEntity) {
					if (this.canHitEntity(livingEntity)) { // this is where the piercing ignore ids are checked
						// ignoring things that are probably going to deflect.. because i don't want deflections to happen
						if (livingEntity.isDeadOrDying()
							|| livingEntity.getItemBlockingWith() != null
							|| livingEntity.isInvulnerableTo(serverLevel, this.damageSources().arrow(this, null))
							|| livingEntity instanceof Player player && player.getAbilities().invulnerable // why is this a separate thing..
						) {
							if (((AbstractArrowAccessor)this).getPiercingIgnoreEntityIds() != null) {
								((AbstractArrowAccessor)this).getPiercingIgnoreEntityIds().add(livingEntity.getId());
							}
						} else {
							this.onHit(new EntityHitResult(livingEntity));
						}
					}
				}
			}
		}
	}
}
