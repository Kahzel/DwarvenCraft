package com.kahzel.dwarvencraft.blocks.sinterer;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kahzel.dwarvencraft.crafting.recipes.SinteringRecipe;
import com.kahzel.dwarvencraft.setup.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.kahzel.dwarvencraft.DwarvenCraft;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OreSintererEntity extends LockableTileEntity implements ITickableTileEntity, INamedContainerProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private SinteringRecipe currentCraft;
    private final IRecipeType<SinteringRecipe> recipeType;
    private int burnTime, cookTime, cookTimeTotal, recipesUsed;
    private final Map<ResourceLocation, Integer> recipes = Maps.newHashMap();

    protected ItemStack recipeKey = ItemStack.EMPTY;
    protected ItemStack recipeOutput = ItemStack.EMPTY;
    protected ItemStack failedMatch = ItemStack.EMPTY;

    public OreSintererEntity() {
        super(BlockInit.SINTERER_ENTITY);
        this.recipeType = SinteringRecipe.SINTERING_RECIPE;
        this.currentCraft = null;
    }

    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(3) {

            protected void onContentsChanged(int slot) {
                markDirty();
            }

            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 1) {
                    int bt = ForgeHooks.getBurnTime(stack);
                    return bt > 0;
                }
                return true;
            }

            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 1 && ForgeHooks.getBurnTime(stack) > 0) {
                    return super.insertItem(1, stack, simulate);
                } else if (slot == 0) {
                    return super.insertItem(0, stack, simulate);
                } else if (slot == 2) {
                    return super.insertItem(0, stack, simulate);
                }
                return stack;
            }
        };
    }

    public void tick() {
        boolean burnFlag = this.isBurning();
        boolean isdirty = false;
        if (!this.world.isRemote) {
            if (burnFlag) {
                --this.burnTime;
            }

            ItemStack itemstack = this.items.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !this.items.get(0).isEmpty()) {
                SinteringRecipe irecipe = this.getRecipe();
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.burnTime = this.getBurnTime(itemstack);
                    this.recipesUsed = this.burnTime;
                    if (this.isBurning()) {
                        isdirty = true;
                        if (itemstack.hasContainerItem())
                            this.items.set(1, itemstack.getContainerItem());
                        else
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                this.items.set(1, itemstack.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getProcessTime();
                        this.finalizeCraft(irecipe);
                        DwarvenCraft.LOGGER.info("Recipe is smelted");
                        isdirty = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (burnFlag != this.isBurning()) {
                isdirty = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(OreSintererBlock.LIT,
                        Boolean.valueOf(this.isBurning())), 3);
            }

            this.markDirty();
        }
    }

    protected int getProcessTime() {
        return this.world.getRecipeManager().getRecipe(this.recipeType,
                this,
                this.world).map(SinteringRecipe::getProcessTime).orElse(400);
    }

    protected int getProgress() {
        return this.cookTime;
    }

    private void finalizeCraft(@Nullable SinteringRecipe recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.setRecipeUsed(recipe);
            }

            itemstack.shrink(recipe.getIngredientCount());
        }
    }

    private int getBurnTime(ItemStack itemstack) {
        return ForgeHooks.getBurnTime(itemstack);
    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipe) {
        if (!this.items.get(0).isEmpty() && recipe != null) {
            ItemStack recipeOut = recipe.getRecipeOutput();
            if (!recipeOut.isEmpty()) {
                ItemStack output = this.items.get(2);
                if (output.isEmpty()) return true;
                else if (!output.isItemEqual(recipeOut)) return false;
                else return output.getCount() + recipeOut.getCount() <= output.getMaxStackSize();
            }
        }
        return false;
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void read(BlockState state, CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        ItemStackHelper.loadAllItems(invTag, this.items);
        this.burnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(state, tag);
    }

    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            ItemStackHelper.saveAllItems(compound, this.items);
            tag.put("inv", compound);
            tag.putInt("BurnTime", this.burnTime);
            tag.putInt("CookTime", this.cookTime);
            tag.putInt("CookTimeTotal", this.cookTimeTotal);
        });
        return super.write(tag);
    }

    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return null;
    }

    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OreSintererContainer(i, this.world, this.pos, playerInventory, playerEntity);
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack item : this.items) {
            if (item != ItemStack.EMPTY)
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.items.get(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int i1) {
        this.items.get(i).shrink(i1);
        return this.items.get(i);
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        ItemStack oldIs = this.items.get(1);
        this.items.set(i, ItemStack.EMPTY);
        return oldIs;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = this.getProcessTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return false;
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.recipes.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
                return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
            });
        }

    }

    public SinteringRecipe getRecipe() {
        ItemStack input = this.items.get(0);
        if (input.isEmpty() || input == this.failedMatch) return null;
        if(currentCraft != null && currentCraft.matches(this, world))
            return currentCraft;
        else {
            SinteringRecipe rec = world.getRecipeManager().getRecipe(this.recipeType, this, this.world).orElse(null);
            if (rec == null)
                failedMatch = input;
            else {
                failedMatch = ItemStack.EMPTY;
                this.cookTimeTotal = rec.getProcessTime();
                this.cookTime = 0;
            }
            this.currentCraft = rec;
            return rec;
        }
    }

    @Override
    public void clear() {

    }
}
