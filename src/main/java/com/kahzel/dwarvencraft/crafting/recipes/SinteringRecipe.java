package com.kahzel.dwarvencraft.crafting.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

public class SinteringRecipe implements IRecipe<IInventory> {

    public static final ResourceLocation SINTERING = new ResourceLocation("dwarvencraft", "sintering");
    public static final IRecipeType<SinteringRecipe> SINTERING_RECIPE = new IRecipeType<SinteringRecipe>() {
        public String toString() {
            return SINTERING.toString();
        }
    };

    public static final SinteringSerializer SINTERING_SERIALIZER = new SinteringSerializer();


    private final ResourceLocation recipeId;
    private int processTime;
    private Ingredient ingredient;
    private int ingredientCount;
    private ItemStack result;

    public SinteringRecipe(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    public boolean matches(IInventory inv, World worldIn) {
        ItemStack itemIn = inv.getStackInSlot(0);
        return this.ingredient.test(itemIn) && itemIn.getCount() >= this.ingredientCount;
    }

    public int getProcessTime() {
        return this.processTime;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public int getIngredientCount() {
        return this.ingredientCount;
    }

    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height) {
        return true;
    }

    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public ResourceLocation getId() {
        return this.recipeId;
    }

    public IRecipeSerializer<?> getSerializer() {
        return SINTERING_SERIALIZER;
    }

    public IRecipeType<?> getType() {
        return SINTERING_RECIPE;
    }

    public static class SinteringSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SinteringRecipe> {

        SinteringSerializer() {
            this.setRegistryName(SINTERING);
        }

        public SinteringRecipe read(ResourceLocation recipeId, JsonObject json) {
            SinteringRecipe recipe = new SinteringRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", 400);
            recipe.ingredient = Ingredient.deserialize(json.get("ingredient"));
            recipe.ingredientCount = JSONUtils.getInt(json.get("ingredient").getAsJsonObject(), "count", 2);
            recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return recipe;
        }

        public SinteringRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            SinteringRecipe recipe = new SinteringRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.read(buffer);
            recipe.ingredientCount = buffer.readByte();
            recipe.result = buffer.readItemStack();
            return recipe;
        }

        public void write(PacketBuffer buffer, SinteringRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeByte(recipe.ingredientCount);
            buffer.writeItemStack(recipe.result);
        }
    }

}