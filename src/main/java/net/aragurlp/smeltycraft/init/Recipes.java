package net.aragurlp.smeltycraft.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes
{
    public static void init()
    {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.testItem), " s ", "sss", " s ", 's', new ItemStack(Blocks.dirt));
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.testBlock), new ItemStack(ModItems.testItem), new ItemStack(ModItems.testItem));
    }
}
