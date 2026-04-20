package ink.quietly.archery_gimmicks.item;

public interface AlteredBowItem {
	float getAlteredPowerForTime(float timeHeld);

	float getFullChargeTime();

	float getCritPower();

	float getArrowDeviation();
}
