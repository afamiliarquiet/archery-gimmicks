package ink.quietly.archery_gimmicks.entity;

import ink.quietly.archery_gimmicks.basics.Bestiary;
import ink.quietly.archery_gimmicks.basics.ItemBag;
import ink.quietly.archery_gimmicks.network.S2CFlingPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class WeightedArrow extends AbstractArrow {
	public WeightedArrow(EntityType<? extends WeightedArrow> type, Level level) {
		super(type, level);
	}

	public WeightedArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.WEIGHTED_ARROW, owner, level, pickupItemStack, firedFromWeapon);
	}

	public WeightedArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.WEIGHTED_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.WEIGHTED_ARROW.getDefaultInstance();
	}

	@Override
	protected double getDefaultGravity() {
		return super.getDefaultGravity() * 3;
	}

	@Override
	public void shoot(double xd, double yd, double zd, float power, float uncertainty) {
		super.shoot(xd, yd, zd, power * 0.8f, uncertainty);

		if (this.getOwner() != null) {
			Entity owner = this.getOwner();

			// my evil convoluted mass numifier
			float ownerMassFactor = 1.3f / (0.31f + Math.clamp(Math.max(owner.getBbWidth(), owner.getBbHeight()), 0.25f, 4f));
			float adjustedPower = power * ownerMassFactor * (owner.isCrouching() ? 0.25f : 1f);
			Vec3 fling = new Vec3(xd, yd, zd).reverse().normalize().scale(adjustedPower);

			if (owner instanceof ServerPlayer player) {
				if (player.isFallFlying() && adjustedPower > 1) {
					// without this it's kinda silly how easily you can fly with weighted arrows
					// like this, it feels.. reasonably viable and fun
					fling = fling.scale(1 / Mth.sqrt(adjustedPower));
				}
				ServerPlayNetworking.send(player, new S2CFlingPayload(fling));
			}

			owner.push(fling);
		}
	}
}
