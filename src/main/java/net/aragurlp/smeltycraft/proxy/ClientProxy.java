package net.aragurlp.smeltycraft.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.aragurlp.smeltycraft.client.settings.Keybindings;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindings()
    {
        ClientRegistry.registerKeyBinding(Keybindings.charge);
        ClientRegistry.registerKeyBinding(Keybindings.release);
    }
}
