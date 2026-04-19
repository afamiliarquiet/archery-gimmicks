package ink.quietly.elementary_archery.basics;

import ink.quietly.elementary_archery.ElementaryArchery;
import ink.quietly.elementary_archery.item.HeavyArrowItem;
import ink.quietly.elementary_archery.item.HeavyBowItem;
import ink.quietly.elementary_archery.item.LightBowItem;
import ink.quietly.elementary_archery.item.SignalArrowItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Function;

public class ItemBag {
	public static final Item LIGHT_BOW = take("light_bow", LightBowItem::new, new Item.Properties().durability(384).enchantable(1));
	public static final Item HEAVY_BOW = take("heavy_bow", HeavyBowItem::new, new Item.Properties().durability(384).enchantable(1));
	public static final Item HEAVY_ARROW = take("heavy_arrow", HeavyArrowItem::new, new Item.Properties());
	public static final Item SIGNAL_ARROW = take("signal_arrow", SignalArrowItem::new, new Item.Properties());

	public static void fill() {
		DispenserBlock.registerProjectileBehavior(HEAVY_ARROW);
		DispenserBlock.registerProjectileBehavior(SIGNAL_ARROW);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(itemGroup -> {
			itemGroup.insertAfter(Items.SPECTRAL_ARROW, HEAVY_ARROW, SIGNAL_ARROW);
			itemGroup.insertAfter(Items.BOW, LIGHT_BOW, HEAVY_BOW);
		});
	}

	private static Item take(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ElementaryArchery.id(id));
		Item item = factory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
