package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.blocks.sinterer.OreSintererContainer;
import com.kahzel.dwarvencraft.blocks.sinterer.OreSintererScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.world.World;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, RecipeInit::init);
    }
}
