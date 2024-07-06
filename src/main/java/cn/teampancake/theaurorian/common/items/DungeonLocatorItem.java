package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;

public class DungeonLocatorItem extends Item implements ITooltipsItem {

    public DungeonLocatorItem() {
        super(new Properties().defaultDurability(30).rarity(Rarity.EPIC));
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
                String boundStructure = AurorianMod.MOD_ID+":"+getSelectedDungeon(itemstack);
                ResourceLocation structureLocation = ResourceLocation.tryParse(boundStructure);
                Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
                ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE, structureLocation);
                HolderSet<Structure> featureHolderSet = registry.getHolder(structureKey).map((holders) -> HolderSet.direct(holders)).orElse(null);
                dungeon = this.findNearestMapStructure(serverLevel,featureHolderSet, player.getOnPos(), 600, false).getFirst();
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

    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel serverLevel, HolderSet<Structure> structureHolderSet, BlockPos pos, int range, boolean findUnexplored) {
        ChunkGenerator generator = serverLevel.getChunkSource().getGenerator();
        Pair<BlockPos, Holder<Structure>> nearest = generator.findNearestMapStructure(serverLevel, structureHolderSet, pos, range, findUnexplored);
        if (nearest == null) return null;
        return nearest.getFirst().distManhattan(pos) <= 600 ? nearest : null;
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
            return "runestone";
        } else {
            return blockname;
        }
    }

    private void setSelectedDungeon(ItemStack stack, String dungeon) {
        CompoundTag nbt = this.getNBT(stack);
        if (dungeon.isEmpty()) {
            nbt.putString("dungeon", "runestone");
            return;
        }
        if (!dungeon.equals(this.getSelectedDungeon(stack))) {
            nbt.putString("dungeon", dungeon);
        }
    }

}