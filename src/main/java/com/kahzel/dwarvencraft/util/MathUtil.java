package com.kahzel.dwarvencraft.util;

import net.minecraft.util.math.BlockPos;

public class MathUtil {

  public static Double sqDist(BlockPos pos1, BlockPos pos2) {

    Double dx = (double) pos2.getX() - pos1.getX();
    Double dy = (double) pos2.getY() - pos1.getY();
    Double dz = (double) pos2.getZ() - pos1.getZ();

    return (dx * dx) + (dy * dy) + (dz * dz);
  }
}
