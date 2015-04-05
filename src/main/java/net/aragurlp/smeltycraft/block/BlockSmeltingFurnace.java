package net.aragurlp.smeltycraft.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.aragurlp.smeltycraft.SmeltyCraft;
import net.aragurlp.smeltycraft.init.ModBlocks;
import net.aragurlp.smeltycraft.reference.GUIs;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileEntitySmeltingFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSmeltingFurnace extends BlockContainerSC
{
    private Random random = new Random();

    private boolean isActive;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;

    private static boolean keepInventory;

    public BlockSmeltingFurnace(boolean isActive)
    {
        super(Material.rock, isActive ? false : true);
        this.isActive = isActive;
        this.setBlockName(this.isActive ? "smeltingFurnace_lit" : "smeltingFurnace");
        this.setStepSound(soundTypeStone);
        this.setHardness(3.5F);
        this.setLightLevel(this.isActive ? 0.9F : 0F);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_side");
        this.iconFront = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_front_" + (this.isActive ? "on" : "off"));
        this.iconTop = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_top_" + (this.isActive ? "on" : "off"));
        this.iconBottom = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase() + ":" + "smeltingfurnace_bottom");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if(metadata  == 0 && side == 3)
        {
            return this.iconFront;
        }
        else if(side == 0)
        {
            return this.iconBottom;
        }
        else if(side == 1)
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

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if(this.isActive)
        {
            int direction = world.getBlockMetadata(x, y, z);

            float x1 = (float)x + 0.5F;
            float y1 = (float)y + random.nextFloat() / 2.1F;
            float z1 = (float)z + 0.5F;

            float f = 0.52F;
            float f1 = random.nextFloat() * 0.6F - 0.3F;

            if(direction == 4)
            {
                world.spawnParticle("smoke", (double)(x1 - f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(x1 - f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 - f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 - f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
            }
            else if(direction == 5)
            {
                world.spawnParticle("smoke", (double)(x1 + f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(x1 + f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f), (double)y1, (double)(z1 + f1), 0.0D, 0.0D, 0.0D);
            }
            else if(direction == 2)
            {
                world.spawnParticle("smoke", (double)(x1 + f1), (double)y1, (double)(z1 - f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(x1 + f1), (double)y1, (double)(z1 - f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f1), (double)y1, (double)(z1 - f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f1), (double)y1, (double)(z1 - f), 0.0D, 0.0D, 0.0D);
            }
            else if(direction == 3)
            {
                world.spawnParticle("smoke", (double)(x1 + f1), (double)y1, (double)(z1 + f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(x1 + f1), (double)y1, (double)(z1 + f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f1), (double)y1, (double)(z1 + f), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(x1 + f1), (double)y1, (double)(z1 + f), 0.0D, 0.0D, 0.0D);
            }

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

    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntitySmeltingFurnace();
    }

//    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
//        if(!world.isRemote)
//        {
            FMLNetworkHandler.openGui(player, SmeltyCraft.instance, GUIs.SMELTING_FURNACE, world, x, y, z);
//        }
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
            ((TileEntitySmeltingFurnace)world.getTileEntity(x, y, z)).setGuiDisplayName(itemStack.getDisplayName());
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

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);

        if(tileEntity != null)
        {
            tileEntity.validate();
            worldObj.setTileEntity(xCoord, yCoord, zCoord, tileEntity);
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMetadata)
    {
        if(!keepInventory)
        {
            TileEntitySmeltingFurnace tileEntity = (TileEntitySmeltingFurnace) world.getTileEntity(x, y, z);

            if(tileEntity != null)
            {
                for(int i = 0; i < tileEntity.getSizeInventory(); i++)
                {
                    ItemStack itemStack = tileEntity.getStackInSlot(i);

                    if(itemStack != null)
                    {
                        float f = this.random.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                        while (itemStack.stackSize > 0)
                        {
                            int j = this.random.nextInt(21) + 10;

                            if(j > itemStack.stackSize)
                            {
                                j = itemStack.stackSize;
                            }

                            itemStack.stackSize -= j;

                            EntityItem item = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double) z + f2, new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

                            if(itemStack.hasTagCompound())
                            {
                                item.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            item.motionX = (double) ((float)this.random.nextGaussian() * f3);
                            item.motionY = (double) ((float)this.random.nextGaussian() * f3 + 0.2F);
                            item.motionZ = (double) ((float)this.random.nextGaussian() * f3);

                            world.spawnEntityInWorld(item);
                        }
                    }
                }
//                world.setBlock(x, y, z, oldBlock);
            }
        }

        super.breakBlock(world, x,y, z, oldBlock, oldMetadata);
    }

    public boolean hasComparatorInputOverride()
    {
        return true;
    }


    public int getComparatorInputOverride(World world, int x, int y, int z, int i)
    {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(x, y, z));
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y , int z, EntityPlayer player){
        return new ItemStack(ModBlocks.smeltingfurnace);
    }

    public static void doParticles(World world, int x, int y, int z, Random random) {
        BlockSmeltingFurnace block = (BlockSmeltingFurnace) world.getBlock(x, y, z);
        if(block.isActive){
            for (int i = 0; i < 3; i++) {
                world.spawnParticle("smoke", x + 0.5D, y + 0.99D, z + 0.5D, random.nextDouble() / 10 - 0.05D, 0.15D, random.nextDouble() / 10 - 0.05D);
            }
        }
    }
}
