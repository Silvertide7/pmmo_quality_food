package net.silvertide.pmmo_quality_foods;

import net.silvertide.pmmo_quality_foods.config.Config;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PMMOQualityFoods.MODID)
public class PMMOQualityFoods {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "pmmo_quality_foods";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public PMMOQualityFoods(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }
}
