package ink.quietly.elementary_archery.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ink.quietly.elementary_archery.item.AlteredBowItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(BowItem.class)
public class AlteredBowItemMixin {
	@WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BowItem;getPowerForTime(I)F"))
	private float useMyPower(int timeHeld, Operation<Float> original) {
		if ((Object)this instanceof AlteredBowItem abow) {
			return abow.getAlteredPowerForTime(timeHeld);
		} else {
			return original.call(timeHeld);
		}
	}

	// modifyargs..? porbaly doesn't really matter
	@WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BowItem;shoot(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Ljava/util/List;FFZLnet/minecraft/world/entity/LivingEntity;)V"))
	private void withMyUncertaintyAndCrit(BowItem instance, ServerLevel serverLevel, LivingEntity player, InteractionHand interactionHand, ItemStack bowStack, List<ItemStack> firedProjectiles, float power, float uncertainty, boolean isCrit, LivingEntity targetOverride, Operation<Void> original, @Local(name = "pow") float pow) {
		if ((Object)this instanceof AlteredBowItem abow) {
			original.call(instance, serverLevel, player, interactionHand, bowStack, firedProjectiles, power,
				abow.getArrowDeviation(), pow == abow.getCritPower(),
				targetOverride);
		} else {
			original.call(instance, serverLevel, player, interactionHand, bowStack, firedProjectiles, power, uncertainty, isCrit, targetOverride);
		}
	}
}
