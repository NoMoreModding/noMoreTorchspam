package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = NoMoreTorchSpam.Mod_ID, bus = EventBusSubscriber.Bus.MOD)
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, NoMoreTorchSpam.Mod_ID);

    // Block Items
    public static final DeferredHolder<Item, BlockItem> GLOW_CRYSTAL =
            ITEMS.register("glow_crystal", () -> new BlockItem(BlockInit.GLOW_CRYSTAL.get(),
                    new Item.Properties()));

    @SubscribeEvent
    public static void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(GLOW_CRYSTAL.get());
        }
    }
}