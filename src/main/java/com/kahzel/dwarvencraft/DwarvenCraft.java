package com.kahzel.dwarvencraft;

import com.kahzel.dwarvencraft.setup.registries.Registry;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DwarvenCraft.MOD_ID)
public class DwarvenCraft {
    public static final String MOD_ID = "dwarvencraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DwarvenCraft() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Registry init
        Registry.callRegistry(eventBus);

        //Register mod to event bus
        eventBus.register(this);


    }


}
