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

public class BlockSC extends Block
{
    public BlockSC(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabSC.SC_TAB);
    }

    public BlockSC()
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

    protected String getUnWrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

}
