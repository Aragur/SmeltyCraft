package net.aragurlp.smeltycraft.client.gui;

import net.aragurlp.smeltycraft.reference.Reference;
import net.aragurlp.smeltycraft.tile.TileSmeltingFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiSmeltingFurnace extends GuiContainer
{
    public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/furnace.png");

    public TileSmeltingFurnace smeltingFurnace;

    public GuiSmeltingFurnace(InventoryPlayer inventorryPlayer, TileSmeltingFurnace entity)
    {
        super(new ContainerSmeltingFurnace(inventorryPlayer, entity));

        this.smeltingFurnace = entity;

        this.xSize = 176;
        this.ySize = 166;
    }

    public void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String name = this.smeltingFurnace.isInvNameLocalized() ? this.smeltingFurnace.getInvName() : StatCollector.translateToLocal(this.smeltingFurnace.getInvName());

        this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) /2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {

    }


}
