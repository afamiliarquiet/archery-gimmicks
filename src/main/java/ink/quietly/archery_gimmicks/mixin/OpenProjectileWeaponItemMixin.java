package ink.quietly.archery_gimmicks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ink.quietly.archery_gimmicks.entity.ExtendsArrowItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class OpenProjectileWeaponItemMixin {
//	@WrapMethod(method = "createProjectile")
//	private Projectile useMyArrows(Level level, LivingEntity shooter, ItemStack weapon, ItemStack projectile, boolean isCrit, Operation<Projectile> original) {
//		if (projectile.getItem() instanceof ExtendsArrowItem arrowItemReal) {
//			AbstractArrow arrow = arrowItemReal.createArrow(level, projectile, shooter, weapon);
//			if (isCrit) {
//				arrow.setCritArrow(true);
//			}
//
//			return arrow;
//		} else {
//			return original.call(level, shooter, weapon, projectile, isCrit);
//		}
//	}

	// hmm. probably doesn't really matter, but this way it's kinda like a redirector when replacing and friendly otherwise?
	// tries to let other wraps change out the args first?
	@WrapOperation(method = "createProjectile", order=9999, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArrowItem;createArrow(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;"))
	private AbstractArrow useMyArrows(ArrowItem instance, Level level, ItemStack itemStack, LivingEntity owner, ItemStack firedFromWeapon, Operation<AbstractArrow> original) {
		if (itemStack.getItem() instanceof ExtendsArrowItem arrowItemReal) {
			return arrowItemReal.createArrow(level, itemStack, owner, firedFromWeapon);
		} else {
			return original.call(instance, level, itemStack, owner, firedFromWeapon);
		}
	}
}
