package ink.quietly.archery_gimmicks.item;

import ink.quietly.archery_gimmicks.entity.BreadArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BreadArrowItem extends ArrowItem {
	public BreadArrowItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull AbstractArrow createArrow(@NonNull Level level, @NonNull ItemStack itemStack, @NonNull LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		AbstractArrow arrow = new BreadArrow(level, owner, itemStack.copyWithCount(1), firedFromWeapon);
		arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
		return arrow;
	}

	@Override
	public @NonNull Projectile asProjectile(@NonNull Level level, @NonNull Position position, @NonNull ItemStack itemStack, @NonNull Direction direction) {
		AbstractArrow arrow = new BreadArrow(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
		arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
		return arrow;
	}
}
