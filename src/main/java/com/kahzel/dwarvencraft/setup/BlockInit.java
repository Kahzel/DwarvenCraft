package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.blocks.BaseOreBlock;
import com.kahzel.dwarvencraft.blocks.OreSintererBlock;
import com.kahzel.dwarvencraft.containers.OreSintererContainer;
import com.kahzel.dwarvencraft.tileentities.OreSintererEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
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

    @ObjectHolder("dwarvencraft:oresintererblock")
    public static OreSintererBlock ORE_SINTERER;

    @ObjectHolder("dwarvencraft:oresintererblock")
    public static TileEntityType<OreSintererEntity> SINTERER_ENTITY;

    @ObjectHolder("dwarvencraft:oresintererblock")
    public static ContainerType<OreSintererContainer> SINTERER_CONTAINER;
}
