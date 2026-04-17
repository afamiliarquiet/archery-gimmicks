package ink.quietly.elementary_archery.item;

import net.minecraft.world.item.BowItem;

public class HeavyBowItem extends BowItem implements AlteredBowItem {
	public HeavyBowItem(Properties properties) {
		super(properties);
	}

	public float getAlteredPowerForTime(final float timeHeld) {
		float pow = timeHeld / 20.0F;
		pow = (pow * pow) / 2.0F;
		if (pow > 2.0F) {
			pow = 2.0F;
		}
		return pow;
	}

	@Override
	public float getFullChargeTime() {
		return 2f;
	}

	@Override
	public float getCritPower() {
		return 2f;
	}

	@Override
	public float getArrowDeviation() {
		return 0.25f;
	}
}
