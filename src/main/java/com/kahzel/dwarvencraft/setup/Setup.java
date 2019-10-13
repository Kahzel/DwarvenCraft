package com.kahzel.dwarvencraft.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Setup {

    public ItemGroup itemGroup = new ItemGroup("dwarvencraft") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockInit.MYTHRITE_ORE);
        }
    };

    public void preinit() {
        //todo
    }
}
