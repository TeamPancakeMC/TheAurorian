package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.widget.SelenaButton;
import cn.teampancake.theaurorian.client.widget.SelenaCloseButton;
import cn.teampancake.theaurorian.common.entities.npc.Selena;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SelenaInitialScreen extends Screen {

    private static final ResourceLocation BACKGROUND = TheAurorian.prefix("textures/gui/selena.png");
    private static final String PREFIX = TheAurorian.MOD_ID + ".selena_initial_screen.";
    private static final Component DIALOGUE = Component.translatable(PREFIX + "dialogue");
    private static final Component FIGHT = Component.translatable(PREFIX + "fight");
    private static final Component TRADE = Component.translatable(PREFIX + "trade");
    private static final Component MISSION = Component.translatable(PREFIX + "mission");
    private final List<Button> buttonList = Lists.newArrayList();
    private final Selena selena;
    private final int leftPos;
    private final int topPos;
    private SelenaButton dialogButton;
    private SelenaButton followButton;
    private SelenaButton tradeButton;
    private SelenaButton missionButton;

    public SelenaInitialScreen(Component title, Selena selena) {
        super(title);
        this.selena = selena;
        this.leftPos = (this.width + 256) / 2;
        this.topPos = (this.height + 64) / 2;
    }

    @Override
    protected void init() {
        this.buttonList.clear();
        this.dialogButton = new SelenaButton(this.leftPos + 76, this.topPos + 116, 66, 18, DIALOGUE, this::enableOtherButton);
        this.followButton = new SelenaButton(this.leftPos + 146, this.topPos + 116, 66, 18, FIGHT, this::enableOtherButton);
        this.tradeButton = new SelenaButton(this.leftPos + 76, this.topPos + 137, 66, 18, TRADE, this::enableOtherButton);
        this.missionButton = new SelenaButton(this.leftPos + 146, this.topPos + 137, 66, 18, MISSION, this::enableOtherButton);
        SelenaCloseButton closeButton = new SelenaCloseButton(this.leftPos + 5, this.topPos + 149, 11, 11, button -> this.onClose());
        this.buttonList.add(this.addRenderableWidget(this.dialogButton));
        this.buttonList.add(this.addRenderableWidget(this.followButton));
        this.buttonList.add(this.addRenderableWidget(this.tradeButton));
        this.buttonList.add(this.addRenderableWidget(this.missionButton));
        this.addRenderableWidget(closeButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos + (64 - this.font.width(this.getTitle())) / 2 + 7;
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, 221, 165);
        guiGraphics.drawString(this.font, this.getTitle(), x, this.topPos + 11, 16777215, Boolean.FALSE);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 7, this.topPos + 30,
                this.leftPos + 71, this.topPos + 110, 30, 0.0625F, mouseX, mouseY, this.selena);
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    private void enableOtherButton(Button clickedButton) {
        clickedButton.active = false;
        for (Button button : this.buttonList) {
            if (button != clickedButton) {
                button.active = true;
            }
        }
    }

}