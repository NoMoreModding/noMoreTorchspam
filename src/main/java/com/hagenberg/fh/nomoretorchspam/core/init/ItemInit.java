package com.hagenberg.fh.nomoretorchspam.core.init;

import com.hagenberg.fh.nomoretorchspam.NoMoreTorchSpam;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NoMoreTorchSpam.Mod_ID);

    // Block Items
    // TODO Make a Block Item
    public static final RegistryObject<BlockItem> GLOW_CRYSTAL =
            ITEMS.register("glow_crystal", () -> new BlockItem(BlockInit.GLOW_CRYSTAL.get(),
                    new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
}
