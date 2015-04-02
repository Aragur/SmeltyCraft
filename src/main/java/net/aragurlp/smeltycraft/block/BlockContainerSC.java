package net.aragurlp.smeltycraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.aragurlp.smeltycraft.creativetab.CreativeTabSC;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileMultiBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockContainerSC extends BlockContainer
{
    public BlockContainerSC(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabSC.SC_TAB);
    }

    /**
     * Simply set's if the block should show in the CreativeTab or not.
     * @param material  Material the block should have
     * @param inCreativeTab Show/Hide in CreativeTab
     */
    public BlockContainerSC(Material material, Boolean inCreativeTab)
    {
        super(material);
        if(inCreativeTab)
        {
            this.setCreativeTab(CreativeTabSC.SC_TAB);
        }
    }

    public BlockContainerSC()
    {
        this(Material.rock);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnWrappedUnlocalizedName(super.getUnlocalizedName()));
        //tile.modid:blockname.name
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnWrappedUnlocalizedName((this.getUnlocalizedName()))));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile != null && (tile instanceof TileMultiBlock))
        {
            TileMultiBlock multiBlock = (TileMultiBlock) tile;
            if(multiBlock.hasMaster())
            {
                if(multiBlock.isMaster())
                {
                    if(!multiBlock.checkMultiBlockForm())
                        multiBlock.resetStructure();
                }
                else
                {
                    if(!multiBlock.checkForMaster())
                        multiBlock.reset();
                }
            }
        }
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return null;
    }

    protected String getUnWrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
