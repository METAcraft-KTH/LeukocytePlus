package nu.metacraft.leukocyte_plus;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nucleoid.leukocyte.Leukocyte;

public class LeukocytePlus implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("LeukocytePlus");

	public static final String MODID = "leukocyte-plus";

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded LeukocytePlus by Acuadragon100");
		Leukocyte.registerRuleEnforcer(LeukocytePlusEnforcer.INSTANCE);
		LeukocytePlusRules.init();
		Events.init();
	}

	public static Identifier getID(String name) {
		return Identifier.of(MODID, name);
	}
}
