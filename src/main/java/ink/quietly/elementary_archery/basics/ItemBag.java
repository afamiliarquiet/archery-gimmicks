package ink.quietly.elementary_archery.basics;

import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.item.HeavyArrowItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Function;

public class ItemBag {
	public static final Item HEAVY_ARROW = take("heavy_arrow", HeavyArrowItem::new, new Item.Properties());

	public static void fill() {
		DispenserBlock.registerProjectileBehavior(HEAVY_ARROW);
	}

	private static Item take(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ElementaryArchery.id(id));
		Item item = factory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
