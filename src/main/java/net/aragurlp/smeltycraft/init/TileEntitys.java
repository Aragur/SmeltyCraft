package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileStoneMultiblock;

public class TileEntitys {
    public static final Class tileStoneMultiblock = TileStoneMultiblock.class;

    public static void init()
    {
        GameRegistry.registerTileEntity(tileStoneMultiblock, Reference.MOD_ID.toLowerCase() + ".multiblock.stone");
    }
}
