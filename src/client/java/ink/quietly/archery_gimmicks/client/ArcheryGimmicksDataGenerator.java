package ink.quietly.archery_gimmicks.client;

import ink.quietly.archery_gimmicks.basics.ItemBag;
import ink.quietly.archery_gimmicks.basics.Spellbook;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ArcheryGimmicksDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(@NonNull FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(GimmickModelProvider::new);
		pack.addProvider(GimmickRecipeProvider::new);
		pack.addProvider(GimmickEnchantmentProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, GimmickEnchantmentProvider::bootstrap);
	}

	public static class GimmickModelProvider extends FabricModelProvider {

		public GimmickModelProvider(FabricPackOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(@NonNull BlockModelGenerators g) {

		}

		@Override
		public void generateItemModels(ItemModelGenerators g) {
			g.generateFlatItem(ItemBag.WEIGHTED_ARROW, ModelTemplates.FLAT_ITEM);
//			g.generateFlatItem(ItemBag.SIGNAL_ARROW, ModelTemplates.FLAT_ITEM);
			g.generateTwoLayerDyedItem(ItemBag.SIGNAL_ARROW);
			g.generateFlatItem(ItemBag.MESSENGER_ARROW, ModelTemplates.FLAT_ITEM);
			g.generateFlatItem(ItemBag.TNT_ARROW, ModelTemplates.FLAT_ITEM);
			this.generateBow(g, ItemBag.LIGHT_BOW, 0.2f, 0.6f, 1f);
			// heavy bow is manual now because i don't want to deal with datagen for a dyed bow
//			this.generateBow(g, ItemBag.HEAVY_BOW, 0.025f, 0.65f, 0.95f);
		}

		@SuppressWarnings("SameParameterValue") // intellij... give it up. i do it for the love of the game
		private void generateBow(ItemModelGenerators g, Item item, float scale, float threshold1, float threshold2) {
			ItemModel.Unbaked bowModel = ItemModelUtils.plainModel(g.createFlatItemModel(item, ModelTemplates.BOW));
			ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(g.createFlatItemModel(item, "_pulling_0", ModelTemplates.BOW));
			ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(g.createFlatItemModel(item, "_pulling_1", ModelTemplates.BOW));
			ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(g.createFlatItemModel(item, "_pulling_2", ModelTemplates.BOW));
			g.itemModelOutput
				.accept(
					item,
					ItemModelUtils.conditional(
						ItemModelUtils.isUsingItem(),
						ItemModelUtils.rangeSelect(new UseDuration(false), scale, pulling0, ItemModelUtils.override(pulling1, threshold1), ItemModelUtils.override(pulling2, threshold2)),
						bowModel
					)
				);
		}
	}

	public static class GimmickRecipeProvider extends FabricRecipeProvider {

		public GimmickRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
			return new RecipeProvider(registries, output) {
				@Override
				public void buildRecipes() {
					// riddle me this, docs.fabricmc.net..
//					HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

					shaped(RecipeCategory.COMBAT, ItemBag.LIGHT_BOW)
						.pattern(" *s")
						.pattern("/ s")
						.pattern(" *s")
						.define('*', Items.GLOWSTONE_DUST)
						.define('s', Items.STRING)
						.define('/', Items.STICK)
						.unlockedBy(getHasName(ItemBag.LIGHT_BOW), has(ItemBag.LIGHT_BOW))
						.unlockedBy(getHasName(Items.BOW), has(Items.BOW))
						.unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
						.save(output);
					shaped(RecipeCategory.COMBAT, ItemBag.HEAVY_BOW)
						.pattern(" /s")
						.pattern("# s")
						.pattern(" /s")
						.define('#', Items.NETHERITE_SCRAP)
						.define('s', Items.STRING)
						.define('/', Items.STICK)
						.unlockedBy(getHasName(ItemBag.HEAVY_BOW), has(ItemBag.HEAVY_BOW))
						.unlockedBy(getHasName(Items.BOW), has(Items.BOW))
						.unlockedBy(getHasName(Items.NETHERITE_SCRAP), has(Items.NETHERITE_SCRAP))
						.save(output);

					shaped(RecipeCategory.COMBAT, ItemBag.WEIGHTED_ARROW, 4)
						.pattern(".")
						.pattern("|")
						.pattern("#")
						.define('.', Items.IRON_NUGGET)
						.define('|', Items.IRON_INGOT)
						.define('#', Items.FEATHER) // you're gonna have to put in a lot of work, little guy. good luck
						.unlockedBy(getHasName(ItemBag.WEIGHTED_ARROW), has(ItemBag.WEIGHTED_ARROW))
						.unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
						.unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
						.save(output);
					shaped(RecipeCategory.COMBAT, ItemBag.SIGNAL_ARROW, 4)
						.pattern("c")
						.pattern("/")
						.pattern("#")
						.define('c', Items.COAL) // fight me, torch placing arrow mods
						.define('/', Items.STICK)
						.define('#', Items.FEATHER) // you're gonna have to put in a lot of work, little guy. good luck
						.unlockedBy(getHasName(ItemBag.SIGNAL_ARROW), has(ItemBag.SIGNAL_ARROW))
						.unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
						.unlockedBy(getHasName(Items.TORCH), has(Items.TORCH))
						.save(output);
					shapeless(RecipeCategory.COMBAT, ItemBag.MESSENGER_ARROW, 1)
						.requires(Items.BUNDLE)
						.requires(Items.ARROW)
						.unlockedBy(getHasName(ItemBag.MESSENGER_ARROW), has(ItemBag.MESSENGER_ARROW))
						.unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
						.unlockedBy(getHasName(Items.BUNDLE), has(Items.BUNDLE))
						.save(output);
					shapeless(RecipeCategory.COMBAT, ItemBag.TNT_ARROW, 1)
						.requires(Items.TNT)
						.requires(Items.ARROW)
						.unlockedBy(getHasName(ItemBag.TNT_ARROW), has(ItemBag.TNT_ARROW))
						.unlockedBy(getHasName(Items.ARROW), has(Items.ARROW))
						.unlockedBy(getHasName(Items.TNT), has(Items.TNT))
						.save(output);

//					CustomCraftingRecipeBuilder.customCrafting(
//							RecipeCategory.MISC,
//							(commonInfo, bookInfo) -> new DyeRecipe(commonInfo, bookInfo, Ingredient.of(ItemBag.HEAVY_BOW), this.tag(ItemTags.DYES), new ItemStackTemplate(ItemBag.HEAVY_BOW))
//						)
//						.unlockedBy(getHasName(ItemBag.HEAVY_BOW), this.has(ItemBag.HEAVY_BOW))
//						.save(this.output, getItemName(ItemBag.HEAVY_BOW) + "_dyed");
				}
			};
		}

		@Override
		public @NonNull String getName() {
			return "Recipe Definitions";
		}
	}

	public static class GimmickEnchantmentProvider extends FabricDynamicRegistryProvider {

		public GimmickEnchantmentProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.@NonNull Provider registries, @NonNull Entries entries) {
			entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
		}

		public static void bootstrap(BootstrapContext<Enchantment> context) {
			var enchantments = context.lookup(Registries.ENCHANTMENT);
			var items = context.lookup(Registries.ITEM);
			register(
				context,
				Spellbook.QUICKSTEP,
				Enchantment.enchantment(
						Enchantment.definition(
							items.getOrThrow(ItemTags.BOW_ENCHANTABLE), 1, 3, Enchantment.dynamicCost(17, 7), Enchantment.constantCost(50), 8, EquipmentSlotGroup.HAND
						)
					)
					.exclusiveWith(enchantments.getOrThrow(EnchantmentTags.BOW_EXCLUSIVE))
					.withSpecialEffect(Spellbook.QUICKSTEP_POWER, new AddValue(LevelBasedValue.perLevel(4F, 3F)))
			);
		}

		private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
			context.register(key, builder.build(key.identifier()));
		}

		@Override
		public @NonNull String getName() {
			return "Enchantment Definitions";
		}
	}
}
