package ink.quietly.elementary_archery.mixin;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
	@Accessor
	int getLife();

	@Accessor
	void setLife(int life);

	@Accessor
	IntOpenHashSet getPiercingIgnoreEntityIds();
}
