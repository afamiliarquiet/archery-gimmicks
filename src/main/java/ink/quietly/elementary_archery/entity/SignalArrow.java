package ink.quietly.elementary_archery.entity;

import ink.quietly.elementary_archery.basics.Bestiary;
import ink.quietly.elementary_archery.basics.ItemBag;
import ink.quietly.elementary_archery.basics.MoteCatalog;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SignalArrow extends AbstractArrow implements AlteredArrow {
	private static final EntityDataAccessor<Boolean> SIGNAL_ACTIVE = SynchedEntityData.defineId(SignalArrow.class, EntityDataSerializers.BOOLEAN);
	/// remains true even after signal is no longer active. only burns once
	protected boolean signalStarted = false;
	protected short remainingActiveTicks = -1;
	private boolean rushOrder = false;

	public SignalArrow(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
		setBaseDamage(getDefaultBaseDamage());
	}

	public SignalArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.SIGNAL_ARROW, owner, level, pickupItemStack, firedFromWeapon);
		setBaseDamage(getDefaultBaseDamage());
	}

	public SignalArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.SIGNAL_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
		setBaseDamage(getDefaultBaseDamage());
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.SIGNAL_ARROW.getDefaultInstance();
	}

	@Override
	public void tick() {
		// i guess... theoretically SynchedData isn't necessary, since this is all on client too?
		// not entirely true actually. if client loads it late, things would prob get weird.
		// ok! server only you go, then.
		if (!level().isClientSide()) {
			if (signalStarted) {
				if (isSignalActive()) {
					remainingActiveTicks--;
					if (remainingActiveTicks == 0 || isInGround()) { // inground check mostly because it'd be annoying otherwise
						setSignalActive(false);
					}
				}
			} else if (this.tickCount > 40 && this.getDeltaMovement().y < 0) {
				setSignalActive(true);
				this.signalStarted = true;
				this.remainingActiveTicks = 1200; // one minute?
				this.pickup = Pickup.DISALLOWED; // burnt out. nothin left
			}
		} else {
			// having such a long-lived rarely-spawned particle means sometimes it takes a while for it to first spawn.
			// kinda annoying. fix is.. rushOrder! that works. waits just a lil for the arrow to slow down to speed, then makes one
			// causes a slight flicker 1-19 ticks later then.. but i prefer that.
			// if these become dyed in the future, then maybe initial light could be a different color so it feels ~intentional~
			if (isSignalActive() && (tickCount % 20 == 0 || rushOrder && this.getDeltaMovement().lengthSqr() < (getGravity() * getGravity() * 1.6f))) {
				Vec3 move = this.getDeltaMovement();
				level().addAlwaysVisibleParticle(MoteCatalog.SIGNAL_PARTICLE, true, getX(), getY() - 0.125, getZ(), move.x, move.y, move.z);
				rushOrder = false;
			}
		}
		super.tick();
	}

	@Override
	public void onSyncedDataUpdated(@NonNull EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);
		if (data == SIGNAL_ACTIVE && isSignalActive()) {
			rushOrder = true;
		}
	}

	@Override
	public float getAirInertia() {
		return isSignalActive() ? 0.2f : this.signalStarted ? 0.8f : 0.97f; // in the industry they call this a "parachute", whatever that means
	}

	@Override
	public float getDefaultBaseDamage() {
		return AlteredArrow.super.getDefaultBaseDamage() * 0.5f; // heh! funny trick.
	}

	@Override
	public boolean isCritArrow() {
		return false;
	}

	public boolean isSignalActive() {
		return entityData.get(SIGNAL_ACTIVE);
	}

	protected void setSignalActive(boolean active) {
		entityData.set(SIGNAL_ACTIVE, active);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SIGNAL_ACTIVE, false);
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("signalStarted", signalStarted);
		output.putShort("remainingSignalTicks", remainingActiveTicks); // ohhh.. that's what the s data type is in entity data
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		signalStarted = input.getBooleanOr("signalStarted", false); // Careful! Dynamo has Refresher!!!!
		remainingActiveTicks = (short) input.getShortOr("remainingSignalTicks", (short) -1); // it's getting cast either way.. why. damned if i int, damned if i short
		setSignalActive(signalStarted && remainingActiveTicks > 0);
	}
}
