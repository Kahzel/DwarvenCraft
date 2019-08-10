package com.kahzel.dwarvencraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseOreBlock extends Block {

    public static final String[] ORES =  {
            "mythriteore",
            "malachiteore",
            "tungstenore",
            "copperore",
            "tinore",
    };

    public BaseOreBlock(String regName) {
        super(Properties.create(Material.ROCK)
        .sound(SoundType.STONE)
        .hardnessAndResistance(2.0f));
        setRegistryName(regName+"block");
    }
}
