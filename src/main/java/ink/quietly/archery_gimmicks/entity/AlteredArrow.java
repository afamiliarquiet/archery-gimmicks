package ink.quietly.archery_gimmicks.entity;

public interface AlteredArrow {
	default float getAirInertia() {
		return 0.99f;
	}

	default float getDefaultBaseDamage() {
		return 2f;
	}
}
