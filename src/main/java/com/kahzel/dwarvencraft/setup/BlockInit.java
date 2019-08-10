package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.blocks.BaseOreBlock;
import net.minecraftforge.registries.ObjectHolder;

public class BlockInit {
    @ObjectHolder("dwarvencraft:mythriteoreblock")
    public static BaseOreBlock MYTHRITE_ORE;

    @ObjectHolder("dwarvencraft:malachiteoreblock")
    public static BaseOreBlock MALACHITE_ORE;

    @ObjectHolder("dwarvencraft:tungstenoreblock")
    public static BaseOreBlock TUNGSTEN_ORE;

    @ObjectHolder("dwarvencraft:copperoreblock")
    public static BaseOreBlock COPPER_ORE;

    @ObjectHolder("dwarvencraft:tinoreblock")
    public static BaseOreBlock TIN_ORE;
}
