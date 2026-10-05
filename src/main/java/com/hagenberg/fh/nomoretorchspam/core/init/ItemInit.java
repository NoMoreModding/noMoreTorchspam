package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NoMoreTorchSpam.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NoMoreTorchSpam.Mod_ID);

    // Block Items
    public static final RegistryObject<BlockItem> GLOW_CRYSTAL =
            ITEMS.register("glow_crystal", () -> new BlockItem(BlockInit.GLOW_CRYSTAL.get(),
                    new Item.Properties()));

    @SubscribeEvent
    public static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(GLOW_CRYSTAL);
        }
    }
}
