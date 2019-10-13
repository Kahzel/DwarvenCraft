package com.kahzel.dwarvencraft.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Do not run client-side stuff from server!");
    }
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("Do not run client-side stuff from server!");
    }

    @Override
    public void init() {
        //todo
    }
}
