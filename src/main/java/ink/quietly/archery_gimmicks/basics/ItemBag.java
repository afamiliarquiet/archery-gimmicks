package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.item.WeightedArrowItem;
import ink.quietly.archery_gimmicks.item.HeavyBowItem;
import ink.quietly.archery_gimmicks.item.LightBowItem;
import ink.quietly.archery_gimmicks.item.SignalArrowItem;
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
	public static final Item WEIGHTED_ARROW = take("weighted_arrow", WeightedArrowItem::new, new Item.Properties());
	public static final Item SIGNAL_ARROW = take("signal_arrow", SignalArrowItem::new, new Item.Properties());

	public static void fill() {
		DispenserBlock.registerProjectileBehavior(WEIGHTED_ARROW);
		DispenserBlock.registerProjectileBehavior(SIGNAL_ARROW);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(itemGroup -> {
			itemGroup.insertAfter(Items.SPECTRAL_ARROW, WEIGHTED_ARROW, SIGNAL_ARROW);
			itemGroup.insertAfter(Items.BOW, LIGHT_BOW, HEAVY_BOW);
		});
	}

	private static Item take(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ArcheryGimmicks.id(id));
		Item item = factory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
