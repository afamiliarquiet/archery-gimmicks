package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class Soundscape {
	public static final SoundEvent QUICKSTEP = register("entity.player.quickstep");

	public static void listen() {
		// can you hear the chirping of the birds? vanished, except for the parrot.
	}

	private static SoundEvent register(String thing) {
		Identifier id = ArcheryGimmicks.id(thing);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}
}
