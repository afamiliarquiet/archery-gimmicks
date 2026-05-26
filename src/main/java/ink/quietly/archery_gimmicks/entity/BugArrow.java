package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.mixin.AbstractArrowAccessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class BugArrow extends AncientArrow {
	public static final ResourceKey<DamageType> BUG_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ArcheryGimmicks.id("bug"));
	public static final EntityDataAccessor<Boolean> DESTINED_FOR_BREAD = SynchedEntityData.defineId(BugArrow.class, EntityDataSerializers.BOOLEAN);

	@Nullable
	private UUID destinedBread;

	public BugArrow(EntityType<? extends AncientArrow> type, Level level) {
		super(type, level);
	}

	public BugArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(Bestiary.BUG_ARROW, level, owner, pickupItemStack, firedFromWeapon);
	}

	public BugArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(Bestiary.BUG_ARROW, level, x, y, z, pickupItemStack, firedFromWeapon);
	}

	public BugArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon, @Nullable UUID destinedBread) {
		super(Bestiary.BUG_ARROW, level, x, y, z, pickupItemStack, firedFromWeapon);
		this.destinedBread = destinedBread;
		setHeavenSent(true);
		setDestinedForBread(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DESTINED_FOR_BREAD, false);
	}

	public boolean isDestinedForBread() {
		return entityData.get(DESTINED_FOR_BREAD);
	}

	protected void setDestinedForBread(boolean destinedForBread) {
		entityData.set(DESTINED_FOR_BREAD, destinedForBread);
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= 1200) { // one minute
			this.discard();
		}
	}

	@Override
	protected boolean ohhImSoScared() {
		return false;
	}

	@Override
	public ResourceKey<DamageType> getDamageType() {
		return BUG_DAMAGE;
	}

	@Override
	protected double getDefaultGravity() {
		return super.getDefaultGravity() / 3;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide() && !this.isInGround() && isDestinedForBread()/* && this.tickCount % 3 == 0*/) {
			this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EAT.value(), SoundSource.PLAYERS, 10f, 0.9f + this.random.nextFloat() * 0.2f, false);
		}
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		if (this.destinedBread != null) {
			if (this.level().getEntity(this.destinedBread) instanceof BreadArrow myBread) {
				if (myBread.position().distanceToSqr(this.position()) < 64) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 12F, 0.95F + this.random.nextFloat() * 0.1F);
					myBread.discard();
				}
			} else {
				// wtf is the point.
				this.destinedBread = null;
				setDestinedForBread(false);
			}
		}
	}
}
