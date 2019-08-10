package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.DwarvenCraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemInit {

    private static final Setup SETUP = DwarvenCraft.SETUP;

    public static void init(final RegistryEvent.Register<Item> itemRegistryEvent) {
        Item.Properties blockProps = new Item.Properties().group(SETUP.itemGroup);
        Item.Properties sandProps = new Item.Properties().group(SETUP.itemGroup).maxStackSize(96);

        //BlockItem setup
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.MYTHRITE_ORE, blockProps)
                .setRegistryName("mythriteoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.MALACHITE_ORE, blockProps)
                .setRegistryName("malachiteoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.TUNGSTEN_ORE, blockProps)
                .setRegistryName("tungstenoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.COPPER_ORE, blockProps)
                .setRegistryName("copperoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.TIN_ORE, blockProps)
                .setRegistryName("tinoreblock"));

        //Item setup
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("mythritesand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("malachitesand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("tungstensand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("coppersand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("tinsand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("ironsand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("goldsand"));

        itemRegistryEvent.getRegistry().register(new Item(blockProps).setRegistryName("malachitechunk"));

    }
}
