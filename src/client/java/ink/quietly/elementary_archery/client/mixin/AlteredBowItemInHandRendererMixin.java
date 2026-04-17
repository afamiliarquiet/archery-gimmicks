package ink.quietly.elementary_archery.client.mixin;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import ink.quietly.elementary_archery.item.AlteredBowItem;
import ink.quietly.elementary_archery.item.HeavyBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ItemInHandRenderer.class)
public class AlteredBowItemInHandRendererMixin {
	@Expression("(? * ? + ? * 2.0) / 3.0")
	@ModifyExpressionValue(method = "renderArmWithItem", at = @At("MIXINEXTRAS:EXPRESSION"))
	private float renderArmWithItem(float original, @Local(argsOnly = true, name = "player") AbstractClientPlayer player, @Local(argsOnly = true, name = "frameInterp") float frameInterp, @Local(argsOnly = true, name = "itemStack") ItemStack itemStack) {
		if (itemStack.getItem() instanceof AlteredBowItem abow) {
			return abow.getAlteredPowerForTime(itemStack.getUseDuration(player) - (player.getUseItemRemainingTicks() - frameInterp + 1.0F));
		}
		return original;
	}

	// MIXINEXTRAS:EXPRESSION my beloved. like waving a magic wand.. anything is possible
	@Expression(value = "? > 1.0", id = "main")
	@Expression(value = "(? * ? + ? * 2.0) / 3.0", id = "from")
	@Expression(value = "?.?(? * -0.5, 0.7, 0.1)", id = "to")
	@ModifyExpressionValue(
		method = "renderArmWithItem", at = @At(value = "MIXINEXTRAS:EXPRESSION", id = "main"),
		slice = @Slice(from = @At(value = "MIXINEXTRAS:EXPRESSION", id = "from"), to = @At(value = "MIXINEXTRAS:EXPRESSION", id = "to"))
	)
	private boolean dontWorryAboutIt(boolean original, @Local(argsOnly = true, name = "itemStack") ItemStack itemStack) {
		if (itemStack.getItem() instanceof HeavyBowItem) {
			return false;
		} else {
			return original;
		}
	}
}
