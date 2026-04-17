package ink.quietly.elementary_archery.item;

public interface AlteredBowItem {
	float getAlteredPowerForTime(float timeHeld);

	float getFullChargeTime();

	float getCritPower();

	float getArrowDeviation();
}
