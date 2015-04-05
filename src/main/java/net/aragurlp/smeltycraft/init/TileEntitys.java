package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileEntitySmeltingFurnace;
import net.aragurlp.smeltycraft.tile.TileEntityStoneMultiblock;

public class TileEntitys {
    public static final Class tileStoneMultiblock = TileEntityStoneMultiblock.class;
    public static final Class tileSmeltingFurnace = TileEntitySmeltingFurnace.class;

    public static void init()
    {
        GameRegistry.registerTileEntity(tileStoneMultiblock, Reference.MOD_ID.toLowerCase() + ".multiblock.stone");
        GameRegistry.registerTileEntity(tileSmeltingFurnace, Reference.MOD_ID.toLowerCase() + ".tile.smeltingfurnace");
    }
}
