package ink.quietly.archery_gimmicks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityType.class)
public class ArrowRangeEntityTypeMixin {
	@Shadow
	@Final
	private Holder.Reference<EntityType<?>> builtInRegistryHolder;

	@ModifyReturnValue(method = "clientTrackingRange", at = @At("RETURN"))
	private int scale(int original) {
		if (this.builtInRegistryHolder.is(EntityTypeTags.ARROWS)) {
			return Mth.ceil(original * ArcheryGimmicks.CONFIG.arrowRenderDistanceScale);
		} else {
			return original;
		}
	}
}
