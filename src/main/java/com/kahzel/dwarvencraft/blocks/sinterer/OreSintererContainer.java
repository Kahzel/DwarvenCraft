package com.kahzel.dwarvencraft.blocks.sinterer;

import com.kahzel.dwarvencraft.setup.BlockInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class OreSintererContainer extends Container {
    private OreSintererEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public OreSintererContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(BlockInit.SINTERER_CONTAINER, windowId);
        tileEntity = (OreSintererEntity) world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 56, 17));
            addSlot(new SlotItemHandler(h, 1, 56, 53));
            addSlot(new SlotItemHandler(h, 2, 116, 35));
        });
        layoutPlayerInventorySlots(8, 84);
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(this.tileEntity.getWorld(),
                this.tileEntity.getPos()), this.playerEntity, BlockInit.ORE_SINTERER);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
    }

    public int getProcessTime() {
        return this.tileEntity.getProcessTime();
    }

    public int getProgress() {
        return this.tileEntity.getProgress();
    }
}
