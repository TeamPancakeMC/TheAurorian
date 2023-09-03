package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class DungeonLocatorItem extends Item implements ITooltipsItem{
    public DungeonLocatorItem() {
        super(new Properties()
                .defaultDurability(30)
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        ItemStack itemstack = player.getUseItem();
        if (player.isCrouching()) {
            switch (this.getSelectedDungeon(itemstack)) {
                case "Moontemple" -> {
                    this.setSelectedDungeon(itemstack, "Runestone");
                    player.displayClientMessage(Component.translatable("string.theaurorian.item.locator1"), true);
                }
                default -> {
                    this.setSelectedDungeon(itemstack, "Darkstone");
                    player.displayClientMessage(Component.translatable("string.theaurorian.item.locator2"), true);
                }
                case "Darkstone" -> {
                    this.setSelectedDungeon(itemstack, "Moontemple");
                    player.displayClientMessage(Component.translatable("string.theaurorian.item.locator3"), true);
                }
            }
        } else {
            BlockPos dungeon = null;
            if (level instanceof ServerLevel serverLevel) {
                //TODO Tagkey<Strucure>
                dungeon = switch (this.getSelectedDungeon(itemstack)) {
                    case "Moontemple" -> serverLevel.findNearestMapStructure(new TagKey<>(), player.getOnPos(), AurorianConfig.CONFIG_DUNGEON_DENSITY.get() * 4, false);
                    default -> serverLevel.findNearestMapStructure(new TagKey<>(), player, AurorianConfig.CONFIG_DUNGEON_DENSITY.get() * 2);
                    case "Darkstone" -> serverLevel.findNearestMapStructure(new TagKey<>(), player, AurorianConfig.CONFIG_DUNGEON_DENSITY.get() * 6);
                };
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
            if (dungeon != null) {
                double lookx = 0.25D + -Math.sin((float) Math.toRadians(player.yHeadRot)) * Math.cos((float) Math.toRadians(player.getXRot()));
                double looky = 0.25D - Math.sin((float) Math.toRadians(player.getXRot()));
                double lookz = 0.25D + Math.cos((float) Math.toRadians(player.yHeadRot)) * Math.cos((float) Math.toRadians(player.getXRot()));

                double y = player.getY() + 1 + level.random.nextDouble() * 6.0D / 16.0D;
                double speed = 0.01D;
                double targetx = player.getX() - dungeon.getX() * 16;
                double targetz = player.getZ() - dungeon.getZ() * 16;
                double originx = player.getX();
                double originz = player.getZ();

                double partx = targetx * -speed;
                double partz = targetz * -speed;

                if (partx < -0.5) {
                    partx = -0.5;
                }
                if (partx > 0.5) {
                    partx = 0.5;
                }
                if (partz < -0.5) {
                    partz = -0.5;
                }
                if (partz > 0.5) {
                    partz = 0.5;
                }

                double randx = level.random.nextDouble() / 8;
                double randz = level.random.nextDouble() / 8;

                for (int i = 0; i < 2; i++) {
                    level.addParticle(ParticleTypes.CLOUD, originx + lookx, y + looky, originz + lookz, partx + randx, 0.25D, partz + randz);
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), originx + lookx, y + looky, originz + lookz, partx + randx, 0.25D, partz + randz);
                }
                itemstack.hurt(1, level.random, (ServerPlayer) player);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    private CompoundTag getNBT(ItemStack stack) {
        CompoundTag nbt = new CompoundTag();
        if (stack.hasTag()) {
            nbt = stack.getTag();
        }
        return nbt;
    }

    private String getSelectedDungeon(ItemStack stack) {
        String blockname = this.getNBT(stack).getString("dungeon");
        if (blockname.isEmpty()) {
            return "Runestone";
        } else {
            return blockname;
        }
    }

    private void setSelectedDungeon(ItemStack stack, String dungeon) {
        CompoundTag nbt = this.getNBT(stack);
        if (dungeon.isEmpty()) {
            nbt.putString("dungeon", "Runestone");
            return;
        }
        if (!dungeon.equals(this.getSelectedDungeon(stack))) {
            nbt.putString("dungeon", dungeon);
        }
    }
}
