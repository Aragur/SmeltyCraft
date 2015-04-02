package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.block.*;
import net.aragurlp.smeltycraft.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final BlockSC testBlock = new BlockTest();
    public static final BlockContainerSC stoneMultiBlock = new BlockTestMultiBlock();
    public static final BlockContainerSC smeltingfurnace = new BlockSmeltingFurnace(false);
    public static final BlockContainerSC smeltingfurnace_lit = new BlockSmeltingFurnace(true);

    public static void init()
    {
        GameRegistry.registerBlock(testBlock, "testBlock");
        GameRegistry.registerBlock(stoneMultiBlock, "stoneMultiBlock");
        GameRegistry.registerBlock(smeltingfurnace, "smeltingFurnace");
        GameRegistry.registerBlock(smeltingfurnace_lit, "smeltingFurnace_lit");
    }
}
