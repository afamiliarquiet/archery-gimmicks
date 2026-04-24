package ink.quietly.archery_gimmicks.mixin;

import ink.quietly.archery_gimmicks.basics.ItemBag;
import ink.quietly.archery_gimmicks.item.HeavyBowItem;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronInteractions.class)
public class CauldronInteractionsMixin {
	@Shadow
	@Final
	public static CauldronInteraction.Dispatcher WATER;

	@Inject(method = "bootStrap()V", at = @At("TAIL"))
	private static void mineTooAaaaawwwwughugheeeueeeuuuuuuuWhyCantYouPlayNiceLikeDispenserQuestionMarkFabricApiPleaseFix(CallbackInfo ci) {
		// or maybe i'm mistaken and there is an event for this. let me know in the comments down below
		// wait oh crap
		// Typo: In word 'Aaaaawwwwughugheeeueeeuuuuuuu'
		// dang intellij you're right. small typo there. i meant Aaaaawwwwughugheeeueeeeuuuuuu
		((UltimateDoomDestinyCauldronInteractionDispatcherInvokerOfDestinedDoomAndDespairOhhhhhhMyGodIDontWantToMakeThisInvoker)WATER).invokePut(ItemBag.HEAVY_BOW, HeavyBowItem::cauldronInteraction);
	}
}
