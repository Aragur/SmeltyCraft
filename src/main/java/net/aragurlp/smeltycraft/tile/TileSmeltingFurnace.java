package net.aragurlp.smeltycraft.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

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
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void updateEntity()
    {
        if(this.burnTime > 0)
        {
            this.burnTime--;
        }

        if(!this.worldObj.isRemote)
        {
            if(this.burnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);
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
