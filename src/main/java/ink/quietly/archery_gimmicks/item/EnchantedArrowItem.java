package ink.quietly.archery_gimmicks.item;

import ink.quietly.archery_gimmicks.entity.EnchantedArrow;
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

public class EnchantedArrowItem extends ArrowItem {
	public EnchantedArrowItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull AbstractArrow createArrow(@NonNull Level level, @NonNull ItemStack itemStack, @NonNull LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		return new EnchantedArrow(level, owner, itemStack.copyWithCount(1), firedFromWeapon);
	}

	@Override
	public @NonNull Projectile asProjectile(@NonNull Level level, @NonNull Position position, @NonNull ItemStack itemStack, @NonNull Direction direction) {
		EnchantedArrow arrow = new EnchantedArrow(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
		arrow.pickup = AbstractArrow.Pickup.ALLOWED;
		return arrow;
	}
}
