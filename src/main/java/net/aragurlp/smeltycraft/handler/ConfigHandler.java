package net.aragurlp.smeltycraft.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.aragurlp.smeltycraft.reference.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;
    public static boolean testValue = false;

    public static void init(File configFile)
    {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent event)
    {
        if(event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        testValue = config.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "This is an example configuration value");

        if(config.hasChanged())
        {
            config.save();
        }
    }
}
