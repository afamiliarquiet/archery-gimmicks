package ink.quietly.archery_gimmicks.client.mixin;

import com.mojang.authlib.GameProfile;
import ink.quietly.archery_gimmicks.item.AlteredBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractClientPlayer.class)
public abstract class AlteredBowClientPlayerMixin extends Player {
	public AlteredBowClientPlayerMixin(Level level, GameProfile gameProfile) {
		super(level, gameProfile);
	}

	@ModifyArg(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"), index = 2)
	private float allowAlteredBows(float modifier) {
		if (this.getUseItem().getItem() instanceof AlteredBowItem abow) {
			float scale = Math.min(this.getTicksUsingItem() / 20.0F, abow.getFullChargeTime());
			return modifier * (1.0F - Mth.square(scale) * 0.15F);
		} else {
			return modifier;
		}
	}
}
