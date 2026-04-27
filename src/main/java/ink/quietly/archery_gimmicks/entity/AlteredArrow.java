package ink.quietly.archery_gimmicks.entity;

import net.minecraft.world.phys.EntityHitResult;

public interface AlteredArrow {
	default float getAirInertia() {
		return 0.99f;
	}

	default float getDefaultBaseDamage() {
		return 2f;
	}

	default void beforeEntityHitDiscard(EntityHitResult hitResult) {
		// nothing to worry about. an easy life
	}
}
