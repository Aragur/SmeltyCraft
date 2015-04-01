package net.aragurlp.smeltycraft.creativetab;

import net.aragurlp.smeltycraft.init.ModItems;
import net.aragurlp.smeltycraft.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSC
{
    public static final CreativeTabs SC_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.testItem;
        }
    };
}
