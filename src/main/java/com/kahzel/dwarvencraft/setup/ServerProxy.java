package com.kahzel.dwarvencraft.setup;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Do not run client-side stuff from server!");
    }
}
