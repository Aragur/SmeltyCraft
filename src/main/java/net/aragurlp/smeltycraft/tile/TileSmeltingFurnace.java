package net.aragurlp.smeltycraft.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.block.BlockSmeltingFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class TileSmeltingFurnace extends TileEntity implements ISidedInventory{

    private String localizedName;

    private static final int[] slots_top = new int[]{0};
    private static final int[] slots_bottom = new int[]{2, 1};
    private static final int[] slots_sides = new int[]{1};

    private ItemStack[] slots = new ItemStack[3];

    /** Furnace Speed */
    public int furnaceSpeed = 200; //200 = Normal Furnace; 100 = 2x faster
    /** The time this furnace will continue for (fuel) */
    public int burnTime;
    /** The start time for this fuel */
    public int currentItemBurnTime;
    /** How much time left before cooked */
    public int cookTime;

    public int getSizeInventory()
    {
        return this.slots.length;
    }

    public void setGuiDisplayName(String displayName) {
        this.localizedName = displayName;
    }

    public boolean isInvNameLocalized()
    {
        return this.localizedName != null && this.localizedName.length() > 0;
    }

    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.localizedName : "container.smeltycraft.smeltingfurnace";
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.slots[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(this.slots[i] != null)
        {
            ItemStack itemStack;
            if(this.slots[i].stackSize <= j)
            {
                itemStack = this.slots[i];
                this.slots[i] = null;
                return itemStack;
            }
            else
            {
                itemStack = this.slots[i].splitStack(j);

                if(this.slots[i].stackSize == 0)
                {
                    this.slots[i] = null;
                }

                return itemStack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if(this.slots[i] != null)
        {
            ItemStack itemStack = this.slots[i];
            this.slots[i] = null;
            return itemStack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        this.slots[i] = itemStack;

        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    private boolean isBurning()
    {
        return this.burnTime > 0;
    }

    @Override
    public void updateEntity()
    {
        boolean flag = this.burnTime > 0;
        boolean flag1 = false;

        if(this.burnTime > 0)
        {
            this.burnTime--;
        }

        if(!this.worldObj.isRemote)
        {
            if(this.burnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);

                if(this.burnTime > 0)
                {
                    flag1 = true;
                    if(this.slots[1] != null)
                    {
                        this.slots[1].stackSize--;
                        if(this.slots[1].stackSize == 0)
                        {
                            this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
                        }
                    }
                }
            }

            if(this.isBurning() && this.canSmelt())
            {
                this.cookTime++;

                if(this.cookTime == this.furnaceSpeed)
                {
                    this.cookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            } else {
                this.cookTime = 0;
            }
            if(flag != this.burnTime > 0)
            {
                flag1 = true;
                BlockSmeltingFurnace.updateSmeltingFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(flag1)
        {
            this.markDirty();
        }
    }

    private boolean canSmelt()
    {
        if(this.slots[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

            if(itemStack == null) return  false;
            if(this.slots[2] == null) return true;
            if(!this.slots[2].isItemEqual(itemStack)) return false;

            int result = this.slots[2].stackSize + itemStack.stackSize;

            return (result <= getInventoryStackLimit() && result <= itemStack.getMaxStackSize());
        }
    }

    public void smeltItem(){
        if(this.canSmelt())
        {
            ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

            if(this.slots[2] == null)
            {
                this.slots[2] = itemStack.copy();
            }
            else  if(this.slots[2].isItemEqual(itemStack))
            {
                this.slots[2].stackSize += itemStack.stackSize;
            }

            this.slots[0].stackSize --;

            if(this.slots[0].stackSize <= 0)
            {
                this.slots[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack itemStack)
    {
        if(itemStack == null)
        {
            return 0;
        }
        else
        {
            return GameRegistry.getFuelValue(itemStack);
        }
    }

    public static boolean isItemFuel(ItemStack itemStack)
    {
        return getItemBurnTime(itemStack) > 0;
    }
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == 2 ? false : (i == 1 ? isItemFuel(itemStack) : true);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int i) {
        return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_sides);
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, int j) {
        return this.isItemValidForSlot(i, itemStack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int j) {
        return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
    }
}
