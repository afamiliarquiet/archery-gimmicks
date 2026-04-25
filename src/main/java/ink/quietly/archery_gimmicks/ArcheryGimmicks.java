package ink.quietly.archery_gimmicks;

import ink.quietly.archery_gimmicks.basics.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcheryGimmicks implements ModInitializer {
	public static final String MOD_ID = "archery_gimmicks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final GimmickConfig CONFIG = GimmickConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, GimmickConfig.class);

	@Override
	public void onInitialize() {
		ItemBag.fill();
		Bestiary.fill();
		Messenger.summon();
		MoteCatalog.peruse();
		Spellbook.fill();
		log("Initialized! Artemis smiles upon us today.");
	}

	public static Identifier id(String thing) {
		return Identifier.fromNamespaceAndPath(MOD_ID, thing);
	}

	public static void log(String litter) {
		LOGGER.info("[Archery Gimmicks] {}", litter);
	}
}
