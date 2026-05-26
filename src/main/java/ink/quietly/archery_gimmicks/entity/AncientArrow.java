package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.MoteCatalog;
import ink.quietly.archery_gimmicks.basics.Soundscape;
import ink.quietly.archery_gimmicks.mixin.AbstractArrowAccessor;
import net.minecraft.core.SectionPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

// and if that don't work, use more arrow
public class AncientArrow extends AbstractArrow implements AlteredArrow {
	private static final EntityDataAccessor<Boolean> HEAVEN_SENT = SynchedEntityData.defineId(AncientArrow.class, EntityDataSerializers.BOOLEAN);
	private long ticketTimer = 0;

	public AncientArrow(EntityType<? extends AncientArrow> type, Level level) {
		super(type, level);
		postConstruction();
	}

	public AncientArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.ANCIENT_ARROW, owner, level, pickupItemStack, firedFromWeapon);
		postConstruction();
	}

	public AncientArrow(EntityType<? extends AncientArrow> type, final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(type, owner, level, pickupItemStack, firedFromWeapon);
		postConstruction();
	}

	public AncientArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.ANCIENT_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
		postConstruction();
	}

	public AncientArrow(
		EntityType<? extends AncientArrow> type, final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(type, x, y, z, level, pickupItemStack, firedFromWeapon);
		postConstruction();
	}

	private AncientArrow(
		final double x, final double y, final double z, final Level level, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.ANCIENT_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
		postConstruction();
		setHeavenSent(true);
	}

	// being a little weird about it.
	public static AncientArrow sendFromTheHeavens(final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		return new AncientArrow( x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	private void postConstruction() {
		setBaseDamage(getDefaultBaseDamage());
		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.getChunkSource().addTicketAndLoadWithRadius(Bestiary.ANCIENT_TICKET, this.chunkPosition(), 2);
			this.ticketTimer = Bestiary.ANCIENT_TICKET.timeout();
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(HEAVEN_SENT, false);
	}

	@Override
	public void onSyncedDataUpdated(@NonNull List<SynchedEntityData.DataValue<?>> updatedItems) {
		super.onSyncedDataUpdated(updatedItems);
	}

	public boolean isHeavenSent() {
		return this.entityData.get(HEAVEN_SENT);
	}

	public void setHeavenSent(boolean newVal) {
		this.entityData.set(HEAVEN_SENT, newVal);
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return Items.ARROW.getDefaultInstance();
	}

	@Override
	public float getDefaultBaseDamage() {
		return 10;
	}

	@Override
	public byte getPierceLevel() {
		return 100;
	}

	@Override
	protected void tickDespawn() {
		int life = ((AbstractArrowAccessor)this).getLife() + 1;
		((AbstractArrowAccessor)this).setLife(life);
		if (life >= ArcheryGimmicks.CONFIG.ancientArrowDespawnTime) { // 30 minutes
			this.discard();
		}
	}

	// no grav/'inertia' to make it easier to spawn far away and hit a target
	@Override
	public float getAirInertia() {
		return isHeavenSent() ? 1f : 0.99f;
	}

	@Override
	protected float getWaterInertia() {
		return isHeavenSent() ? 1f : 0.99f;
	}

	@Override
	protected double getDefaultGravity() {
		// could totally optimize this out. not going to
		return isHeavenSent() ? 0.0 : super.getDefaultGravity() * 3;
	}

	@Override
	protected boolean isAffectedByBlocks() {
		return false;
	}

	@Override
	protected void onHitBlock(@NonNull BlockHitResult hitResult) {
		Vec3 reverseMovement = this.getDeltaMovement().reverse();
		double mag = reverseMovement.length();
		super.onHitBlock(hitResult);
		if (this.level().isClientSide()) {
			for (int i = 0; i < mag * 5; i++) {
				Vec3 offset = reverseMovement.addLocalCoordinates(new Vec3(this.random.nextGaussian() * 0.3, this.random.nextGaussian() * 0.3, this.random.nextDouble() * (mag+3) / 5));
				Vec3 spawnPos = this.position().add(offset);
				level().addAlwaysVisibleParticle(MoteCatalog.SMOKE_CLOUD, true, spawnPos.x, spawnPos.y, spawnPos.z, offset.x, offset.y, offset.z);
			}
			for (int i = 0; i < mag * 5; i++) {
				Vec3 offset = reverseMovement.addLocalCoordinates(new Vec3(this.random.nextGaussian() * 0.7, this.random.nextGaussian() * 0.7, this.random.nextDouble() * (mag+3) / 13));
				Vec3 spawnPos = this.position().add(offset);
				level().addAlwaysVisibleParticle(MoteCatalog.SMOKE_CLOUD, true, spawnPos.x, spawnPos.y, spawnPos.z, offset.x, offset.y, offset.z);
			}

			if (isHeavenSent()) {
				this.level()
					.playLocalSound(
						this.getX(), this.getY(), this.getZ(), Soundscape.ANCIENT_REVERBERATION, SoundSource.PLAYERS, 64F, 0.5F + this.random.nextFloat() * 0.1F, false
					);
			}
			this.level()
				.playLocalSound(
					this.getX(), this.getY(), this.getZ(), Soundscape.ANCIENT_ARROW_HIT, SoundSource.PLAYERS, 8F, 0.7F + this.random.nextFloat() * 0.3F, false
				);
		} else {
			setHeavenSent(false);
		}
	}

	@Override
	public boolean deflect(@NonNull ProjectileDeflection deflection, @Nullable Entity deflectingEntity, @Nullable EntityReference<Entity> newOwner, boolean byAttack) {
		setHeavenSent(false);
		return super.deflect(deflection, deflectingEntity, newOwner, byAttack);
	}

	@Override
	protected @NonNull Collection<EntityHitResult> findHitEntities(@NonNull Vec3 from, @NonNull Vec3 to) {
		// ignore default projectile entity hit handling, it's not good enough for bigger arrow
		return List.of();
	}

	@Override
	public void tick() {
		int prevChunkX = SectionPos.blockToSectionCoord(this.position().x());
		int prevChunkZ = SectionPos.blockToSectionCoord(this.position().z());
		if (!this.isInGround()) {
			hitThemAll();
		}
		super.tick();
		if (this.level() instanceof ServerLevel serverLevel && this.isAlive() && !this.isInGround()) {
			if (--this.ticketTimer <= 0
				|| prevChunkX != SectionPos.blockToSectionCoord(this.position().x())
				|| prevChunkZ != SectionPos.blockToSectionCoord(this.position().z())
			) {
				serverLevel.getChunkSource().addTicketWithRadius(Bestiary.ANCIENT_TICKET, this.chunkPosition(), 2);
				this.ticketTimer = Bestiary.ANCIENT_TICKET.timeout();
			}
		}
	}

	protected boolean ohhImSoScared() {
		return true;
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
						// but only if im so scared
						if (ohhImSoScared() && (livingEntity.isDeadOrDying()
							|| livingEntity.getItemBlockingWith() != null
							|| livingEntity.isInvulnerableTo(serverLevel, this.damageSources().arrow(this, null))
							|| livingEntity instanceof Player player && player.getAbilities().invulnerable) // why is this a separate thing..
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
