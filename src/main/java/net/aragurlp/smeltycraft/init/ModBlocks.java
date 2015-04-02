package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.block.BlockMultiBlock;
import net.aragurlp.smeltycraft.block.BlockMultiStone;
import net.aragurlp.smeltycraft.block.BlockSC;
import net.aragurlp.smeltycraft.block.BlockTest;
import net.aragurlp.smeltycraft.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final BlockSC testBlock = new BlockTest();
    public static final BlockMultiBlock stoneMultiBlock= new BlockMultiStone();

    public static void init()
    {
        GameRegistry.registerBlock(testBlock, "testBlock");
        GameRegistry.registerBlock(stoneMultiBlock, "stoneMultiBlock");
    }
}
