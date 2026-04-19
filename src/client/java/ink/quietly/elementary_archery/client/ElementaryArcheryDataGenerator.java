package ink.quietly.elementary_archery.client;

import ink.quietly.elementary_archery.basics.ItemBag;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

public class ElementaryArcheryDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(@NonNull FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
	}

	public static class ModelProvider extends FabricModelProvider {

		public ModelProvider(FabricPackOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(@NonNull BlockModelGenerators g) {

		}

		@Override
		public void generateItemModels(ItemModelGenerators g) {
			g.generateFlatItem(ItemBag.HEAVY_ARROW, ModelTemplates.FLAT_ITEM);
			g.generateFlatItem(ItemBag.SIGNAL_ARROW, ModelTemplates.FLAT_ITEM);
			this.generateBow(g, ItemBag.LIGHT_BOW, 0.2f, 0.6f, 1f);
			this.generateBow(g, ItemBag.HEAVY_BOW, 0.025f, 0.65f, 0.95f);

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
}
