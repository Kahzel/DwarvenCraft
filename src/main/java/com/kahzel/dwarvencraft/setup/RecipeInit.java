package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.crafting.recipes.SinteringRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class RecipeInit {

    public static void init(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, SinteringRecipe.SINTERING, SinteringRecipe.SINTERING_RECIPE);
        event.getRegistry().register(SinteringRecipe.SINTERING_SERIALIZER);
    }
}
