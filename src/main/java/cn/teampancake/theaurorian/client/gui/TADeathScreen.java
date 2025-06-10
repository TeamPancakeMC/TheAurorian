package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.network.PlayerDeathRespawnC2SPacket;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class TADeathScreen extends DeathScreen {

    private static final Component SPECTATE = Component.translatable("deathScreen.spectate");
    private static final Component TITLE_SCREEN = Component.translatable("deathScreen.titleScreen");
    private static final Component RESPAWN_IN_OVERWORLD = Component.translatable(TheAurorian.MOD_ID + ".deathScreen.respawn.overworld");
    private static final Component RESPAWN_IN_AURORIAN = Component.translatable(TheAurorian.MOD_ID + ".deathScreen.respawn.aurorian");

    private final boolean hasSpawnPoint;
    @Nullable
    private Button respawnInOverworldButton;
    @Nullable
    private Button respawnInAurorianButton;

    public TADeathScreen(@Nullable Component causeOfDeath, boolean hardcore, boolean hasSpawnPoint) {
        super(causeOfDeath, hardcore);
        this.hasSpawnPoint = hasSpawnPoint;
    }

    @Override
    public void init() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.delayTicker = 0;
            this.exitButtons.clear();
            Component component = this.hardcore ? SPECTATE : RESPAWN_IN_OVERWORLD;
            this.respawnInOverworldButton = this.addRenderableWidget(Button.builder(component, button -> {
                PacketDistributor.sendToServer(new PlayerDeathRespawnC2SPacket(Boolean.FALSE));
                this.minecraft.player.respawn();
            }).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build());
            this.respawnInAurorianButton = this.addRenderableWidget(Button.builder(RESPAWN_IN_AURORIAN, button -> {
                PacketDistributor.sendToServer(new PlayerDeathRespawnC2SPacket(Boolean.TRUE));
                this.minecraft.player.respawn();
            }).bounds(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
            this.exitToTitleButton = this.addRenderableWidget(Button.builder(TITLE_SCREEN, button -> {
                ReportingContext context = this.minecraft.getReportingContext();
                context.draftReportHandled(this.minecraft, this, this::handleExitToTitleScreen, Boolean.TRUE);
            }).bounds(this.width / 2 - 100, this.height / 4 + (this.hardcore ? 96 : 120), 200, 20).build());
            this.respawnInAurorianButton.active = this.hasSpawnPoint;
            this.exitButtons.add(this.respawnInOverworldButton);
            if (!this.hardcore) {
                this.exitButtons.add(this.respawnInAurorianButton);
            }

            this.exitButtons.add(this.exitToTitleButton);
            this.setButtonsActive(false);
            String score = Integer.toString(this.minecraft.player.getScore());
            MutableComponent componentWithStyle = Component.literal(score).withStyle(ChatFormatting.YELLOW);
            this.deathScore = Component.translatable("deathScreen.score.value", componentWithStyle);
        }
    }

    @Override
    public void handleExitToTitleScreen() {
        if (this.minecraft != null) {
            if (this.hardcore) {
                this.exitToTitleScreen();
            } else {
                ConfirmScreen confirmscreen = new TitleConfirmScreen(
                        p_280795_ -> {
                            if (p_280795_) {
                                this.exitToTitleScreen();
                            }
                        },
                        Component.translatable("deathScreen.quit.confirm"),
                        Component.translatable("deathScreen.titleScreen"));
                this.minecraft.setScreen(confirmscreen);
                confirmscreen.setDelay(20);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class TitleConfirmScreen extends ConfirmScreen {

        public TitleConfirmScreen(BooleanConsumer callback, Component title, Component yesButton) {
            super(callback, title, CommonComponents.EMPTY, yesButton, CommonComponents.EMPTY);
        }

        @Override
        protected void addButtons(int y) {
            this.addExitButton(Button.builder(this.yesButton, button -> this.callback.accept(true))
                    .bounds(this.width / 2 - 155, y, 150, 20).build());
            if (respawnInOverworldButton != null) {
                this.addExitButton(respawnInOverworldButton);
            }

            if (respawnInAurorianButton != null) {
                this.addExitButton(respawnInAurorianButton);
            }
        }

        @Override
        public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            DeathScreen.renderDeathBackground(guiGraphics, this.width, this.height);
        }

    }

}