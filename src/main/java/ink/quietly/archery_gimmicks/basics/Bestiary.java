package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.entity.AncientArrow;
import ink.quietly.archery_gimmicks.entity.EnchantedArrow;
import ink.quietly.archery_gimmicks.entity.MessengerArrow;
import ink.quietly.archery_gimmicks.entity.SignalArrow;
import ink.quietly.archery_gimmicks.entity.TNTArrow;
import ink.quietly.archery_gimmicks.entity.WeightedArrow;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class Bestiary {
	public static final EntityType<WeightedArrow> WEIGHTED_ARROW = note("weighted_arrow", EntityType.Builder.<WeightedArrow>of(WeightedArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(4)
		.updateInterval(20)
	);

	public static final EntityType<SignalArrow> SIGNAL_ARROW = note("signal_arrow", EntityType.Builder.<SignalArrow>of(SignalArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(32)
		.updateInterval(20)
	);

	public static final EntityType<MessengerArrow> MESSENGER_ARROW = note("messenger_arrow", EntityType.Builder.<MessengerArrow>of(MessengerArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(4)
		.updateInterval(20)
	);

	public static final EntityType<TNTArrow> TNT_ARROW = note("tnt_arrow", EntityType.Builder.<TNTArrow>of(TNTArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(4)
		.updateInterval(20)
	);

	public static final EntityType<EnchantedArrow> ENCHANTED_ARROW = note("enchanted_arrow", EntityType.Builder.<EnchantedArrow>of(EnchantedArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(0.5F, 0.5F)
		.eyeHeight(0.13F)
		.clientTrackingRange(4)
		.updateInterval(20)
	);

	public static final EntityType<AncientArrow> ANCIENT_ARROW = note("ancient_arrow", EntityType.Builder.<AncientArrow>of(AncientArrow::new, MobCategory.MISC)
		.noLootTable()
		.sized(8f, 8f)
		.eyeHeight(0f)
		.clientTrackingRange(32)
		.updateInterval(20)
	);

	public static final TicketType ANCIENT_TICKET = youShallFlyForth("ancient_arrow", 40, 14);

	public static void fill() {

	}

	private static <T extends Entity> EntityType<T> note(String id, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, ArcheryGimmicks.id(id));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	@SuppressWarnings("SameParameterValue")
	private static TicketType youShallFlyForth(String name, long timeout, int flags) {
		return Registry.register(BuiltInRegistries.TICKET_TYPE, ArcheryGimmicks.id(name), new TicketType(timeout, flags));
	}
}
