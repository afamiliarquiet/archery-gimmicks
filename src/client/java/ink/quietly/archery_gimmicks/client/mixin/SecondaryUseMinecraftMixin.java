package ink.quietly.archery_gimmicks.client.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import ink.quietly.archery_gimmicks.basics.Spellbook;
import ink.quietly.archery_gimmicks.network.C2SFaithPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.PiercingWeapon;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Minecraft.class)
public class SecondaryUseMinecraftMixin {
	@Shadow
	@Nullable
	public LocalPlayer player;

	@Definition(id = "piercingWeapon", local = @Local(type = PiercingWeapon.class, name = "piercingWeapon"))
	@Expression("piercingWeapon != null")
	@Inject(
		method = "startAttack",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"),
		slice = @Slice(to = @At(value = "MIXINEXTRAS:EXPRESSION"))
	)
	private void thisTooIsUse(CallbackInfoReturnable<Boolean> cir, @Local(name = "heldItem") ItemStack heldItem) {
		if (this.player != null && EnchantmentHelper.has(heldItem, Spellbook.QUICKSTEP_POWER)) {
			Optional<Vec3> targetPos = Spellbook.quickstep(this.player);
			ClientPlayNetworking.send(new C2SFaithPayload(targetPos, Optional.empty()));
		}
	}
}
