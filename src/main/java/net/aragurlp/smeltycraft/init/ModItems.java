package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.aragurlp.smeltycraft.item.ItemSC;
import net.aragurlp.smeltycraft.item.ItemTest;

public class ModItems
{
    public static final ItemSC testItem = new ItemTest();

    public static void init()
    {
        GameRegistry.registerItem(testItem, "testItem");
    }
}
