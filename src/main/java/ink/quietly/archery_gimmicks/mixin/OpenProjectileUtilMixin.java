package ink.quietly.archery_gimmicks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ink.quietly.archery_gimmicks.entity.ExtendsArrowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileUtil.class)
public class OpenProjectileUtilMixin {
	@WrapOperation(method = "getMobArrow", order = 9999, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;"))
	private static AbstractArrow useMyArrows(ArrowItem instance, Level level, ItemStack itemStack, LivingEntity owner, ItemStack firedFromWeapon, Operation<AbstractArrow> original) {
		if (itemStack.getItem() instanceof ExtendsArrowItem arrowItemReal) {
			return arrowItemReal.createArrow(level, itemStack, owner, firedFromWeapon);
		} else {
			return original.call(instance, level, itemStack, owner, firedFromWeapon);
		}
	}
}
