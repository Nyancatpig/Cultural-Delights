package net.ncpbails.culturaldelights.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.ncpbails.culturaldelights.CulturalDelights;
import net.ncpbails.culturaldelights.container.BambooMatContainer;

public class BambooMatScreen extends ContainerScreen<BambooMatContainer> {
    private final ResourceLocation GUI = new ResourceLocation(CulturalDelights.MOD_ID,
            "textures/gui/bamboo_mat_gui.png");

    public BambooMatScreen(BambooMatContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.width = width / 2;
        this.height = height / 2;
        int i = this.guiLeft;
        int j = this.guiTop;          //placement of texture      how far to the bottom right is the texture used
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    }
}