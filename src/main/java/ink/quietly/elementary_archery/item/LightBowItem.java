package ink.quietly.elementary_archery.item;

import net.minecraft.world.item.BowItem;

public class LightBowItem extends BowItem implements AlteredBowItem {
	public LightBowItem(Properties properties) {
		super(properties);
	}

	public float getAlteredPowerForTime(final float timeHeld) {
		float pow = timeHeld / 20.0F;
		pow = pow * 2.0F;
		if (pow > 0.5F) {
			pow = 0.5F;
		}
		return pow;
	}

	@Override
	public float getFullChargeTime() {
		return 0.25f;
	}

	@Override
	public float getCritPower() {
		return 1f; // not reachable. no crit for you
	}

	@Override
	public float getArrowDeviation() {
		return 2.5f;
	}
}
