package ink.quietly.archery_gimmicks.mixin;

import ink.quietly.archery_gimmicks.entity.AlteredArrow;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AlteredAbstractArrowMixin {
	// you dont want to piss me of.. ill inject a 355 char name method into yuor code
	// in case you don't want to read the full name, this is primarily for /summon
	// other arrow creations should be fine reading from their constructor. they'd better be fine. Machine..
	@ModifyArg(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ValueInput;getDoubleOr(Ljava/lang/String;D)D"), index = 1)
	private double whyWouldYouDoThisToMeIJustWantedToHaveAReallyBigArrowDoReallyBigDamageAndNowIHaveToMakeAMixinToAdjustTheDamageBecauseTheSummonCommandIsJustFrickinWeirdAndWontRespectAnOrdinaryConstructorBecauseNooooYouHaveToJustUseTheDefaultsOfLoadingATerribleLittleEmptyNBTForTheEntityDontYouIsntThatJustLovelyWellISayNOIHaveHadItWithThisYouWillRespectMyDefaultBaseDamage(double defaultValue) {
		if (this instanceof AlteredArrow aarrow) {
			return aarrow.getDefaultBaseDamage();
		} else {
			return defaultValue;
		}
	}

	@ModifyArg(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;applyInertia(F)V"),
		slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;isCritArrow()Z"))
	)
	private float useMineTryItTryMineUseMyNumberInsteadGiveItAGoComeOnTryIt(float inertia) {
		if (this instanceof AlteredArrow aarrow) {
			return aarrow.getAirInertia();
		} else {
			return inertia;
		}
	}

	// wow. i never really appreciated until now how convenient mixin can be.
	// i could copy paste the whole code block and find each of the discards myself and replace em for tntarrow
	// or... i make a beautifully elegant @At(INVOKE) that also allows other arrows to get involved. splendid.
	@Inject(
		method = "onHitEntity",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;discard()V"),
		slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;"))
	)
	private void somethingOminousLiesInWait(EntityHitResult hitResult, CallbackInfo ci) {
		if (this instanceof AlteredArrow aarrow) {
			aarrow.beforeEntityHitDiscard(hitResult);
		}
	}
}
