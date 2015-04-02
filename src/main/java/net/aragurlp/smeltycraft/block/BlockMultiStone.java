package net.aragurlp.smeltycraft.block;

import net.aragurlp.smeltycraft.tile.TileStoneMultiblock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiStone extends BlockMultiBlock {
    public BlockMultiStone()
    {
        super(Material.rock);
        this.setBlockName("stoneMultiBlock");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileStoneMultiblock();
    }
}
