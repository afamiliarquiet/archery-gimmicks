package ink.quietly.archery_gimmicks.mixin;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CauldronInteraction.Dispatcher.class)
public interface UltimateDoomDestinyCauldronInteractionDispatcherInvokerOfDestinedDoomAndDespairOhhhhhhMyGodIDontWantToMakeThisInvoker {
	@Invoker
	void invokePut(final Item item, final CauldronInteraction interaction);
}
