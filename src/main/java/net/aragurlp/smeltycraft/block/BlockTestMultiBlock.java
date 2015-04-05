package net.aragurlp.smeltycraft.block;

import net.aragurlp.smeltycraft.tile.TileEntityStoneMultiblock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTestMultiBlock extends BlockContainerSC {
    public BlockTestMultiBlock()
    {
        super(Material.rock);
        this.setBlockName("stoneMultiBlock");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityStoneMultiblock();
    }
}
