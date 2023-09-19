package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.DwarvenCraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DwarvenCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {
  private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

  private static final ForgeConfigSpec.IntValue EXAMPLE_NUMBER = BUILDER
          .comment("A magic number")
          .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
  public static int magicNumber;

}
