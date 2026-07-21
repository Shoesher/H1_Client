package com.example.client.constants;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;

import java.util.Map;

public class blocksMap {
    private static blocksMap blockConstants = null;

    //key = finishline material based on race
    //value= # laps

    public Map<Block, Integer> raceBlocks = Map.of(
            Blocks.GLAZED_TERRACOTTA.cyan(), 6,
            Blocks.GLAZED_TERRACOTTA.orange(), 4,
            Blocks.GLAZED_TERRACOTTA.pink(), 6,
            Blocks.COPPER_GRATE.waxed().weathered(), 6,
            Blocks.CHISELED_COPPER.waxed().weathered(), 4,
            Blocks.COPPER_BULB.waxed().oxidized(), 4,
            Blocks.WET_SPONGE, 4,
            Blocks.GILDED_BLACKSTONE, 2
    );

    public static blocksMap getInstance(){
        if(blockConstants == null){
            blockConstants = new blocksMap();
        }
        return blockConstants;
    }
}

