package com.kahzel.dwarvencraft.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class InventoryUtil {

  public static boolean canItemsStack(ItemStack a, ItemStack b) {
    if (a.isEmpty() || b.isEmpty()) {
      return true;
    }
    return ItemHandlerHelper.canItemStacksStack(a, b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
  }
}
