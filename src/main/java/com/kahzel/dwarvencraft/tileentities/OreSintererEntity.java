package com.kahzel.dwarvencraft.tileentities;

import com.kahzel.dwarvencraft.containers.OreSintererContainer;
import com.kahzel.dwarvencraft.setup.BlockInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OreSintererEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);

    public OreSintererEntity() {
        super(BlockInit.SINTERER_ENTITY);
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
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 1) {
                    return stack.getBurnTime() > 0;
                }
                return true;
            }

            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 1 && stack.getBurnTime() > 0) {
                    return super.insertItem(1, stack, simulate);
                } else if (slot == 0) {
                    return super.insertItem(0, stack, simulate);
                }
                return stack;
            }
        };
    }

    public void tick() {

    }

    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(tag);
    }

    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
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

    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OreSintererContainer(i, this.world, this.pos, playerInventory, playerEntity);
    }
}
