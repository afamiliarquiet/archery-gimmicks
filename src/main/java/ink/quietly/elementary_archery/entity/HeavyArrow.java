package ink.quietly.elementary_archery.entity;

import ink.quietly.elementary_archery.basics.Bestiary;
import ink.quietly.elementary_archery.basics.ItemBag;
import ink.quietly.elementary_archery.network.S2CFlingPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HeavyArrow extends AbstractArrow {
	public HeavyArrow(EntityType<? extends HeavyArrow> type, Level level) {
		super(type, level);
	}

	public HeavyArrow(final Level level, final LivingEntity owner, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon) {
		super(Bestiary.HEAVY_ARROW, owner, level, pickupItemStack, firedFromWeapon);
	}

	public HeavyArrow(
		final Level level, final double x, final double y, final double z, final ItemStack pickupItemStack, @Nullable final ItemStack firedFromWeapon
	) {
		super(Bestiary.HEAVY_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
	}

	@Override
	protected @NonNull ItemStack getDefaultPickupItem() {
		return ItemBag.HEAVY_ARROW.getDefaultInstance();
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

			float ownerMassFactor = 1.1f / Math.max(owner.getBbWidth(), owner.getBbHeight());
			Vec3 fling = new Vec3(xd, yd, zd).reverse().normalize().scale(power * ownerMassFactor);

			if (owner instanceof ServerPlayer player) {
				if (player.isFallFlying()) {
					// without this it's kinda silly how easily you can fly with heavy arrows
					// like this, it feels.. reasonably viable and fun
					fling = fling.scale(0.5);
				}
				ServerPlayNetworking.send(player, new S2CFlingPayload(fling));
			}

			owner.push(fling);
		}
	}
}
