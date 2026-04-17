package ink.quietly.elementary_archery.basics;

import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.entity.BiggerArrow;
import ink.quietly.elementary_archery.entity.HeavyArrow;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class Bestiary {
	public static final EntityType<HeavyArrow> HEAVY_ARROW = note("heavy_arrow", EntityType.Builder.<HeavyArrow>of(HeavyArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(4)
		.updateInterval(20)
	);

	public static final EntityType<BiggerArrow> BIGGER_ARROW = note("bigger_arrow", EntityType.Builder.<BiggerArrow>of(BiggerArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(16f, 16f)
		.eyeHeight(0f)
		.clientTrackingRange(32)
		.updateInterval(20)
	);

	public static void fill() {

	}

	private static <T extends Entity> EntityType<T> note(String id, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, ElementaryArchery.id(id));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}
}
