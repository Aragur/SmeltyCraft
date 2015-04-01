package net.aragurlp.smeltycraft.block;

import net.minecraft.block.material.Material;

public class BlockTest extends BlockSC
{
    public BlockTest()
    {
        super(Material.iron);
        this.setBlockName("testBlock");
        this.setStepSound(soundTypeMetal);
    }
}
