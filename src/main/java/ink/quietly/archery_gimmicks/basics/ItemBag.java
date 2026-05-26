package ink.quietly.archery_gimmicks.basics;

import ink.quietly.archery_gimmicks.ArcheryGimmicks;
import ink.quietly.archery_gimmicks.item.BreadArrowItem;
import ink.quietly.archery_gimmicks.item.BugArrowItem;
import ink.quietly.archery_gimmicks.item.EnchantedArrowItem;
import ink.quietly.archery_gimmicks.item.HeavyBowItem;
import ink.quietly.archery_gimmicks.item.LightBowItem;
import ink.quietly.archery_gimmicks.item.MessengerArrowItem;
import ink.quietly.archery_gimmicks.item.SignalArrowItem;
import ink.quietly.archery_gimmicks.item.TNTArrowItem;
import ink.quietly.archery_gimmicks.item.WeightedArrowItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Function;

public class ItemBag {
	public static final Item LIGHT_BOW = take("light_bow", LightBowItem::new, new Item.Properties().durability(384).enchantable(1));
	public static final Item HEAVY_BOW = take("heavy_bow", HeavyBowItem::new, new Item.Properties().durability(384).enchantable(1));
	public static final Item WEIGHTED_ARROW = take("weighted_arrow", WeightedArrowItem::new, new Item.Properties());
	public static final Item SIGNAL_ARROW = take("signal_arrow", SignalArrowItem::new, new Item.Properties());
	public static final Item MESSENGER_ARROW = take("messenger_arrow", MessengerArrowItem::new, new Item.Properties().stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
	public static final Item TNT_ARROW = take("tnt_arrow", TNTArrowItem::new, new Item.Properties());
	public static final Item ENCHANTED_ARROW = take("enchanted_arrow", EnchantedArrowItem::new, new Item.Properties().rarity(Rarity.UNCOMMON).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
	public static final Item BREAD_ARROW = take("bread_arrow", BreadArrowItem::new, new Item.Properties().rarity(Rarity.UNCOMMON).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.6F).build()));
	public static final Item BUG_ARROW = take("bug_arrow", BugArrowItem::new, new Item.Properties().rarity(Rarity.EPIC).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

	public static void fill() {
		DispenserBlock.registerProjectileBehavior(WEIGHTED_ARROW);
		DispenserBlock.registerProjectileBehavior(SIGNAL_ARROW);
		DispenserBlock.registerProjectileBehavior(MESSENGER_ARROW);
		DispenserBlock.registerProjectileBehavior(TNT_ARROW);
		DispenserBlock.registerProjectileBehavior(ENCHANTED_ARROW);
		DispenserBlock.registerProjectileBehavior(BREAD_ARROW);
		DispenserBlock.registerProjectileBehavior(BUG_ARROW);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(itemGroup -> {
			itemGroup.insertAfter(Items.SPECTRAL_ARROW, WEIGHTED_ARROW, SIGNAL_ARROW, MESSENGER_ARROW, TNT_ARROW, ENCHANTED_ARROW);
			itemGroup.insertAfter(Items.BOW, LIGHT_BOW, HEAVY_BOW);
		});

		LootTableEvents.MODIFY.register(((resourceKey, builder, lootTableSource, provider) -> {
			if (lootTableSource.isBuiltin()) {
				if(BuiltInLootTables.SIMPLE_DUNGEON.equals(resourceKey)) {
					LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ItemBag.ENCHANTED_ARROW).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))));
					builder.withPool(poolBuilder);
				} else if (BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(resourceKey)) {
					LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ItemBag.ENCHANTED_ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))));
					builder.withPool(poolBuilder);
				}
			}
		}));
	}

	private static Item take(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, ArcheryGimmicks.id(id));
		Item item = factory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
