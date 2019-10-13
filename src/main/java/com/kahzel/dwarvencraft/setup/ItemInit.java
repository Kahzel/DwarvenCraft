package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.DwarvenCraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemInit {

    private static final Setup SETUP = DwarvenCraft.SETUP;

    public static void init(final RegistryEvent.Register<Item> itemRegistryEvent) {
        Item.Properties baseProps = new Item.Properties().group(SETUP.itemGroup);
        Item.Properties sandProps = new Item.Properties().group(SETUP.itemGroup).maxStackSize(96);

        //BlockItem setup
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.MYTHRITE_ORE, baseProps)
                .setRegistryName("mythriteoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.MALACHITE_ORE, baseProps)
                .setRegistryName("malachiteoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.TUNGSTEN_ORE, baseProps)
                .setRegistryName("tungstenoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.COPPER_ORE, baseProps)
                .setRegistryName("copperoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.TIN_ORE, baseProps)
                .setRegistryName("tinoreblock"));
        itemRegistryEvent.getRegistry().register(new BlockItem(BlockInit.ORE_SINTERER, baseProps)
                .setRegistryName("oresintererblock"));

        //Item setup
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("mythritesand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("malachitesand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("tungstensand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("coppersand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("tinsand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("ironsand"));
        itemRegistryEvent.getRegistry().register(new Item(sandProps).setRegistryName("goldsand"));

        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("mythritechunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("malachitechunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("tungstenchunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("copperchunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("tinchunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("ironchunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("goldchunk"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("redstonechunk"));

        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("malachiteingot"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("mythriteingot"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("tungsteningot"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("copperingot"));
        itemRegistryEvent.getRegistry().register(new Item(baseProps).setRegistryName("tiningot"));



    }
}
