package ink.quietly.archery_gimmicks.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

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

	public static InteractionResult cauldronInteraction(
		final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final ItemStack itemInHand
	) {
		// ripped from mojang's hands. i could maybe accessorize it or access widen.. class tweak? it. but why bother
		if (!itemInHand.has(DataComponents.DYED_COLOR)) {
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		} else {
			if (!level.isClientSide()) {
				itemInHand.remove(DataComponents.DYED_COLOR);
				// it's not armor. and i don't think anybody needs a heavy bows cleaned stat.
//				player.awardStat(Stats.CLEAN_ARMOR);
				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
			}

			return InteractionResult.SUCCESS;
		}
	}
}
