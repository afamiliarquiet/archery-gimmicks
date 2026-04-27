package ink.quietly.archery_gimmicks.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

// (real)
public interface ExtendsArrowItem extends ProjectileItem {
	@NonNull AbstractArrow createArrow(final @NonNull Level level, final @NonNull ItemStack itemStack, final @NonNull LivingEntity owner, @Nullable final ItemStack firedFromWeapon);
}
