package com.kahzel.dwarvencraft.setup;

import com.kahzel.dwarvencraft.crafting.recipes.SinteringRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class RecipeInit {

    public static void init() {
        Registry.register(Registry.RECIPE_TYPE, SinteringRecipe.SINTERING, SinteringRecipe.SINTERING_RECIPE);
        IRecipeSerializer.register(SinteringRecipe.SINTERING.toString(), SinteringRecipe.SINTERING_SERIALIZER);
    }
}
