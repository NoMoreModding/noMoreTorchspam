package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NoMoreTorchSpam.Mod_ID);

    // Block Items
    public static final RegistryObject<BlockItem> GLOW_CRYSTAL =
            ITEMS.register("glow_crystal", () -> new BlockItem(BlockInit.GLOW_CRYSTAL.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

//    public static final RegistryObject<BlockItem> GLOW_LIGHT =
//            ITEMS.register("glow_light", () -> new BlockItem(BlockInit.GLOW_LIGHT.get(),
//                    new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
}
