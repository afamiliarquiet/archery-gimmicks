package ink.quietly.archery_gimmicks.client.mixin;

import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArrowRenderer.class)
public interface ArrowRendererAccessor {
	@Mutable
	@Accessor()
	void setModel(ArrowModel model);
}
