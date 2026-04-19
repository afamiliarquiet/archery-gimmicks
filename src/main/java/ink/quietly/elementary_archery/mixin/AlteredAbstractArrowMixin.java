package ink.quietly.elementary_archery.mixin;

import ink.quietly.elementary_archery.entity.AlteredArrow;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

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
}
