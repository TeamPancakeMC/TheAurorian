package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.network.PlayerLostInForestS2CPacket;
import cn.teampancake.theaurorian.common.network.SylvanisProgressS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SylvanisHandler {

    private static final float FOG_START = 80.0f;
    private static final float FOG_VISIBLE = 90.0f;
    private static final float FOG_PEAK = 100.0f;
    private static final float MIN_ALPHA = 0.0f;
    private static final float MAX_ALPHA = 0.5f;

    public static void checkSylvanisToTeleport(Player player, Level level, boolean otherCondition) {
        AttachmentType<Float> sylvanisAttachment = TAAttachmentTypes.SYLVANIS_PROGRESS.get();
        AttachmentType<Boolean> soundFlagAttachment = TAAttachmentTypes.SOUND_PLAYED_FLAG.get();
        AttachmentType<Integer> attachment2 = TAAttachmentTypes.TICKS_IN_FOREST.get();
        AttachmentType<Integer> attachment3 = TAAttachmentTypes.TICKS_STAND_STILL.get();
        float sylvanis = player.getData(sylvanisAttachment);
        int ticksInForest = player.getData(attachment2);
        BlockPos pos = player.blockPosition().below();
        BlockState state = level.getBlockState(pos);
        Holder<Biome> biome = level.getBiome(player.blockPosition());
        boolean inCorrectBiomes = biome.is(BiomeTags.IS_FOREST) || biome.is(BiomeTags.IS_TAIGA) || biome.is(BiomeTags.IS_JUNGLE);
        if (level.dimension() == Level.OVERWORLD && inCorrectBiomes && state.isFaceSturdy(level, pos, Direction.UP)) {
            if (!otherCondition) return;
            int lightLevel = state.getLightEmission(level, pos);
            boolean isRaining = level.isRaining() || level.isThundering();
            boolean isFullMoon = level.isNight() && level.getMoonBrightness() > 0.9F;
            float timeFactor = getTimeFactor(level);
            float lightMod = 1.0f - (lightLevel / 15.0f);
            float weatherMod = isRaining ? 1.3f : 1.0f;
            float moonMod = isFullMoon ? 1.5f : 1.0f;
            float timeMod = 0.7f + (0.6f * timeFactor);
            float stageMod = 1.0f;
            if (sylvanis > 30.0f) stageMod = 1.2f;
            if (sylvanis > 60.0f) stageMod = 1.5f;
            if (sylvanis > 90.0f) stageMod = 2.0f;
            float initMod = ticksInForest < 200 ? 0.5f : 1.0f;
            float increaseRate = 0.8f * lightMod * weatherMod
                    * moonMod * timeMod * stageMod * initMod;
            sylvanis += increaseRate;
            player.setData(sylvanisAttachment, Math.min(100.0f, sylvanis));
            player.setData(attachment2, ticksInForest + 1);
            player.setData(attachment3, 0);
            boolean soundPlayed = player.getData(soundFlagAttachment);
            if (!soundPlayed && sylvanis >= 68.0f && sylvanis <= 72.0f) {
                playAmbientMoodSound();
                player.setData(soundFlagAttachment, true);
            }
            
            if (player instanceof ServerPlayer serverPlayer) {
                syncSylvanisToClient(serverPlayer, sylvanis);
                MinecraftServer server = player.getServer();
                if (sylvanis >= 100.0f && server != null) {
                    syncLostInfoToClient(serverPlayer, true);
                    ServerLevel toLevel = server.getLevel(TADimensions.AURORIAN_DIMENSION);
                    TAEntityUtils.teleportToAurorian(serverPlayer, toLevel);
                    syncLostInfoToClient(serverPlayer, false);
                    player.setData(attachment2, 0);
                }
            }
        } else {
            float i = Math.max(0, sylvanis - 0.5F);
            player.setData(sylvanisAttachment, i);
            player.setData(attachment2, 0);
            if (i < 50.0F) {
                player.setData(soundFlagAttachment, false);
            }
            
            if (player instanceof ServerPlayer serverPlayer) {
                syncSylvanisToClient(serverPlayer, i);
            }
        }
    }

    public static void checkNotMoving(Player player, double dx, double dy, double dz) {
        if (player.isPassenger() || ServerPlayer.didNotMove(dx, dy, dz)) {
            AttachmentType<Float> attachment1 = TAAttachmentTypes.SYLVANIS_PROGRESS.get();
            AttachmentType<Integer> attachment2 = TAAttachmentTypes.TICKS_STAND_STILL.get();
            float sylvanis = player.getData(attachment1);
            int standStillTicks = player.getData(attachment2);
            player.setData(attachment2, standStillTicks + 1);
            if (standStillTicks > 40) {
                float i = Math.max(0, sylvanis - 0.5F);
                player.setData(attachment1, i);
                if (player instanceof ServerPlayer serverPlayer) {
                    SylvanisHandler.syncSylvanisToClient(serverPlayer, i);
                }
            }
        }
    }

    private static void playAmbientMoodSound() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (level != null && level.isClientSide && player != null) {
            SoundManager soundManager = minecraft.getSoundManager();
            Holder<Biome> biome = level.getBiome(player.blockPosition());
            biome.value().getAmbientMood().ifPresent(settings -> {
                double i = settings.getBlockSearchExtent();
                int j = settings.getBlockSearchExtent() * 2 + 1;
                BlockPos blockPos = BlockPos.containing(
                        player.getX() + (double)level.random.nextInt(j) - i,
                        player.getEyeY() + (double)level.random.nextInt(j) - i,
                        player.getZ() + (double)level.random.nextInt(j) - i);
                double d0 = (double)blockPos.getX() + 0.5F;
                double d1 = (double)blockPos.getY() + 0.5F;
                double d2 = (double)blockPos.getZ() + 0.5F;
                double d3 = d0 - player.getX();
                double d4 = d1 - player.getEyeY();
                double d5 = d2 - player.getZ();
                double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                double d7 = d6 + settings.getSoundPositionOffset();
                SimpleSoundInstance soundInstance = SimpleSoundInstance.forAmbientMood(
                        settings.getSoundEvent().value(), level.random,
                        player.getX() + d3 / d6 * d7,
                        player.getEyeY() + d4 / d6 * d7,
                        player.getZ() + d5 / d6 * d7);
                soundManager.play(soundInstance);
            });
        }
    }

    public static void syncSylvanisToClient(ServerPlayer player, float sylvanis) {
        SylvanisProgressS2CPacket packet = new SylvanisProgressS2CPacket(sylvanis);
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void syncLostInfoToClient(ServerPlayer player, boolean lost) {
        PlayerLostInForestS2CPacket packet = new PlayerLostInForestS2CPacket(lost);
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void levelFogColorAlpha(float red, float green, float blue, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity cameraEntity = minecraft.getCameraEntity();
        if (cameraEntity instanceof LocalPlayer player) {
            float sylvanis = player.getData(TAAttachmentTypes.SYLVANIS_PROGRESS);
            if (sylvanis > FOG_START) {
                float alpha = calculateFog(sylvanis);
                RenderSystem.setShaderFogColor(red, green, blue, alpha);
                ci.cancel();
            }
        }
    }

    private static float getTimeFactor(Level level) {
        float dayTime = (float)(level.getDayTime() % 24000);
        return 1.0f - Math.abs(dayTime - 12000f) / 12000f;
    }

    private static float calculateFog(float sylvanisValue) {
        float alpha = 0f;
        if (sylvanisValue >= FOG_START) {
            if (sylvanisValue <= FOG_VISIBLE) {
                float progress = (sylvanisValue - FOG_START) / (FOG_VISIBLE - FOG_START);
                alpha = MIN_ALPHA + (MAX_ALPHA * 0.2f) * (float)Math.pow(progress, 3);
            } else if (sylvanisValue <= FOG_PEAK) {
                float progress = (sylvanisValue - FOG_VISIBLE) / (FOG_PEAK - FOG_VISIBLE);
                alpha = MAX_ALPHA * 0.2f + (MAX_ALPHA * 0.6f) * progress;
            } else {
                float base = MAX_ALPHA * 0.8f;
                float pulse = (float)Math.sin(System.currentTimeMillis() / 1500.0) * 0.05f;
                alpha = Math.min(MAX_ALPHA, base + pulse);
            }
        }

        return alpha;
    }

}