package ink.quietly.elementary_archery;

import ink.quietly.elementary_archery.basics.Bestiary;
import ink.quietly.elementary_archery.basics.ItemBag;
import ink.quietly.elementary_archery.basics.Messenger;
import ink.quietly.elementary_archery.basics.MoteCatalog;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementaryArchery implements ModInitializer {
	public static final String MOD_ID = "elementary_archery";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ElementaryConfig CONFIG = ElementaryConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, ElementaryConfig.class);

	@Override
	public void onInitialize() {
		ItemBag.fill();
		Bestiary.fill();
		Messenger.summon();
		MoteCatalog.peruse();
		log("Initialized! Artemis smiles upon us today.");
	}

	public static Identifier id(String thing) {
		return Identifier.fromNamespaceAndPath(MOD_ID, thing);
	}

	public static void log(String litter) {
		LOGGER.info("[Elementary Archery] {}", litter);
	}
}
