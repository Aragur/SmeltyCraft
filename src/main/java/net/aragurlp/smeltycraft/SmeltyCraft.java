package net.aragurlp.smeltycraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.aragurlp.smeltycraft.client.handler.KeyInputEventHandler;
import net.aragurlp.smeltycraft.handler.ConfigHandler;
import net.aragurlp.smeltycraft.handler.GuiHandler;
import net.aragurlp.smeltycraft.init.ModBlocks;
import net.aragurlp.smeltycraft.init.ModItems;
import net.aragurlp.smeltycraft.init.Recipes;
import net.aragurlp.smeltycraft.init.TileEntitys;
import net.aragurlp.smeltycraft.proxy.IProxy;
import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.util.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class SmeltyCraft {

    @Mod.Instance(Reference.MOD_ID)
    public static SmeltyCraft instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        proxy.registerKeyBindings();

        ModItems.init();
        ModBlocks.init();

        LogHelper.info("Pre Initialization Complete!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());

        Recipes.init();
        TileEntitys.init();
        GuiHandler.init();

        LogHelper.info("Initialization Complete!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post Initialization Complete!");
    }
}
