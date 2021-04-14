package com.hagenberg.fh.nomoretorchspam;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class mod_items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,nomoretorchspam.MODID);

    public static final RegistryObject<BlockItem> NMTS_MAIN = ITEMS.register("nmts_light", ()-> new BlockItem(mod_blocks.NMTS_MAIN.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));
}
