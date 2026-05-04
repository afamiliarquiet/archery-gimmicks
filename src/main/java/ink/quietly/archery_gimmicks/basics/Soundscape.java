package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class Soundscape {
	public static final SoundEvent QUICKSTEP = register("entity.player.quickstep");

	public static final SoundEvent ENCHANTED_ARROW_HIT = register("entity.enchanted_arrow.hit_extra");
	public static final SoundEvent ANCIENT_CALLING = register("entity.ancient_arrow.call_down");
	public static final SoundEvent ANCIENT_ARROW_HIT = register("entity.ancient_arrow.hit_extra");
	public static final SoundEvent ANCIENT_REVERBERATION = register("entity.ancient_arrow.impact_reverberation");

	public static void listen() {
		// can you hear the chirping of the birds? vanished, except for the parrot.
	}

	private static SoundEvent register(String thing) {
		Identifier id = ArcheryGimmicks.id(thing);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}
}
