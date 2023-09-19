package com.kahzel.dwarvencraft.setup.registries;

import com.kahzel.dwarvencraft.DwarvenCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

  public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, DwarvenCraft.MOD_ID);
  public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DwarvenCraft.MOD_ID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DwarvenCraft.MOD_ID);
  public static final DeferredRegister<RecipeType<?>> RECIPE_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, DwarvenCraft.MOD_ID);
  public static final DeferredRegister<MenuType<?>> MENU_REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, DwarvenCraft.MOD_ID);

  public static void callRegistry(IEventBus eventBus) {
      BLOCK_REGISTRY.register(eventBus);
      ITEM_REGISTRY.register(eventBus);
      BLOCK_ENTITY_REGISTRY.register(eventBus);
      RECIPE_REGISTRY.register(eventBus);
      MENU_REGISTRY.register(eventBus);
  }

}
