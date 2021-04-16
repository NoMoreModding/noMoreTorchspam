package com.hagenberg.fh.nomoretorchspam.common.block;

import net.minecraft.block.Block;

public class GlowLight extends Block {


    public GlowLight(Properties properties){
        super(properties);
        properties.lightLevel(lightLevel -> 15);
        //TODO add other properties and add Block.json, Item.json and BlockItem aswell as blockstates and lang
    }

}
