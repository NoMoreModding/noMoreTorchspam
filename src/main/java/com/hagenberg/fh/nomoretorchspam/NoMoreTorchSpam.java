package com.hagenberg.fh.nomoretorchspam;

import com.hagenberg.fh.nomoretorchspam.config.Config;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockEntityInit;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockInit;
import com.hagenberg.fh.nomoretorchspam.core.init.ItemInit;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NoMoreTorchSpam.Mod_ID)
public class NoMoreTorchSpam
{

    // Directly reference a logger.
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String Mod_ID = "nomoretorchspam";
    public static final boolean DEBUGMODE = false;



    public NoMoreTorchSpam(IEventBus modEventBus, ModContainer modContainer) {
        // Register our config so FML creates and loads the config file for us.
        // Config copies the values into plain fields on ModConfigEvent, which is what the
        // block reads - the spec values themselves are not available during registry events.
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.COMMEN_CONFIG);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        BlockEntityInit.BLOCK_ENTITIES.register(modEventBus);
    }
}