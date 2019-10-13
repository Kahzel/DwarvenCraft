package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.DwarvenCraft;
import com.kahzel.dwarvencraft.containers.OreSintererContainer;
import com.kahzel.dwarvencraft.screens.OreSintererScreen;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfig;

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
