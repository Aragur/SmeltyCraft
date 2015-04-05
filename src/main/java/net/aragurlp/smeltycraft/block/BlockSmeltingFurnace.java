package net.aragurlp.smeltycraft.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.aragurlp.smeltycraft.SmeltyCraft;
import net.aragurlp.smeltycraft.init.ModBlocks;
import net.aragurlp.smeltycraft.reference.GUIs;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileSmeltingFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSmeltingFurnace extends BlockContainerSC
{
    private boolean isActive;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    private static boolean keepInventory;

    public BlockSmeltingFurnace(boolean isActive)
    {
        super(Material.rock, true);
        this.isActive = isActive;
        this.setBlockName(this.isActive ? "smeltingFurnace_lit" : "smeltingFurnace");
        this.setStepSound(soundTypeStone);
        this.setHardness(3.5F);
        this.setLightLevel(this.isActive ? 0.9F : 0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_side");
        this.iconFront = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_front_" + (this.isActive ? "on" : "off"));
        this.iconTop = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_top");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if(side <= 1)
        {
            return this.iconTop;
        }
        else if(side ==  metadata)
        {
            return this.iconFront;
        }
        else
        {
            return this.blockIcon;
        }
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.smeltingfurnace);
    }

    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            Block b1 = world.getBlock(x, y, z - 1);
            Block b2 = world.getBlock(x, y, z +1);
            Block b3 = world.getBlock(x - 1, y, z);
            Block b4 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if(b1.func_149730_j() && !b2.func_149730_j())
            {
                b0 = 3;
            }
            if(b2.func_149730_j() && !b1.func_149730_j())
            {
                b0 = 2;
            }
            if(b3.func_149730_j() && !b4.func_149730_j())
            {
                b0 = 5;
            }
            if(b4.func_149730_j() && !b3.func_149730_j())
            {
                b0 = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, b0, 2);
        }
    }

    public TileEntity createNewTileEntity(World world)
    {
        return new TileSmeltingFurnace();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            FMLNetworkHandler.openGui(player, SmeltyCraft.instance, GUIs.SMELTING_FURNACE, world, x, y, z);
        }
        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int l = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if(l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if(l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if(l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if(itemStack.hasDisplayName())
        {
            ((TileSmeltingFurnace)world.getTileEntity(x, y, z)).setGuiDisplayName(itemStack.getDisplayName());
        }

    }

    public static void updateSmeltingFurnaceBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord) {
        int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
        keepInventory = true;

        if(active)
        {
            worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.smeltingfurnace_lit);
        }
        else
        {
            worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.smeltingfurnace);
        }

        keepInventory = false;

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);

        if(tileEntity != null)
        {
            tileEntity.validate();
            worldObj.setTileEntity(xCoord, yCoord, zCoord, tileEntity);
        }
    }

    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int i)
    {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(x, y, z));
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y , int z, EntityPlayer player){
        return new ItemStack(ModBlocks.smeltingfurnace);
    }
}
