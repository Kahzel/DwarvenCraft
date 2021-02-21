package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.blocks.sinterer.OreSintererContainer;
import com.kahzel.dwarvencraft.blocks.sinterer.OreSintererScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    public void init() {
        ScreenManager.registerFactory(BlockInit.SINTERER_CONTAINER, OreSintererScreen::new);
    }

    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public ClientProxy() {
        RecipeInit.init();
    }
}
