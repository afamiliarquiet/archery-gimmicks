package ink.quietly.archery_gimmicks.item;

import ink.quietly.archery_gimmicks.entity.ExtendsArrowItem;
import ink.quietly.archery_gimmicks.entity.MessengerArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

// todo - honestly extending BundleItem and having ExtendsArrowItem is just laziness.. of a sort
//  but it might be good to.. copypaste all the bundle stuff and make it actually an ArrowItem.
//  can customize it more then.
public class MessengerArrowItem extends BundleItem implements ExtendsArrowItem {
	public MessengerArrowItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull AbstractArrow createArrow(@NonNull Level level, @NonNull ItemStack itemStack, @NonNull LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
		return new MessengerArrow(level, owner, itemStack.copyWithCount(1), firedFromWeapon);
	}

	@Override
	public @NonNull Projectile asProjectile(@NonNull Level level, @NonNull Position position, @NonNull ItemStack itemStack, @NonNull Direction direction) {
		MessengerArrow arrow = new MessengerArrow(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
		arrow.pickup = AbstractArrow.Pickup.ALLOWED;
		return arrow;
	}
}
