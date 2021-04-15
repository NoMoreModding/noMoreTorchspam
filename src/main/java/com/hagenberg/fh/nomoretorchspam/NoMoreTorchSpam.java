package com.hagenberg.fh.nomoretorchspam;

import com.hagenberg.fh.nomoretorchspam.core.EvListener;
import com.hagenberg.fh.nomoretorchspam.core.init.BlockInit;
import com.hagenberg.fh.nomoretorchspam.core.init.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NoMoreTorchSpam.Mod_ID)
public class NoMoreTorchSpam
{
    //CHNAGING SOMETHIGN TO TEST GIT
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String Mod_ID = "nomoretorchspam";
    public static final boolean DEBUGMODE = true;



    public NoMoreTorchSpam() {
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(new EvListener());

        bus.addListener(this::setup);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
}
