package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;

import javax.annotation.Nullable;
import java.util.Optional;

public class DungeonLocatorItem extends Item implements ITooltipsItem {

    public DungeonLocatorItem() {
        super(new Properties().defaultDurability(30).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        ItemStack itemstack = player.getItemInHand(pUsedHand);
        if (player.isCrouching()) {
            switch (itemstack.getOrCreateTag().getString("dungeon")) {
                case "moon_temple" -> {
                    this.setSelectedDungeon(itemstack, "runestone_dungeon");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator1"), true);
                }
                default -> {
                    this.setSelectedDungeon(itemstack, "darkstone_dungeon");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator2"), true);
                }
                case "darkstone_dungeon" -> {
                    this.setSelectedDungeon(itemstack, "moon_temple");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator3"), true);
                }
            }
        } else {
            BlockPos dungeon;
            if (level instanceof ServerLevel serverLevel) {
                Optional<HolderSet.Named<Structure>> optional = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getTag(getSelectedDungeon(itemstack));
                Pair<BlockPos, Holder<Structure>> result = this.findNearestMapStructure(serverLevel, optional.get(), player.blockPosition(), 600, false);
                dungeon = result==null?null:result.getFirst();
                if (dungeon != null) {
                    itemstack.getOrCreateTag().putInt("BlockPosX", dungeon.getX());
                    itemstack.getOrCreateTag().putInt("BlockPosZ", dungeon.getZ());
                    itemstack.hurt(1, serverLevel.random, (ServerPlayer) player);
                }
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Nullable
    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel serverLevel, HolderSet<Structure> structureHolderSet, BlockPos pos, int range, boolean findUnexplored) {
        ChunkGenerator generator = serverLevel.getChunkSource().getGenerator();
        Pair<BlockPos, Holder<Structure>> nearest = generator.findNearestMapStructure(serverLevel, structureHolderSet, pos, range, findUnexplored);
        if (nearest == null) return null;
        return nearest.getFirst().distManhattan(pos) <= 600 ? nearest : null;
    }

    private TagKey<Structure> getSelectedDungeon(ItemStack stack) {
        String blockname = stack.getOrCreateTag().getString("dungeon");
        if (blockname.equals("darkstone_dungeon")) {
            return TAStructureTags.DARKSTONE_DUNGEON;
        } else if(blockname.equals("moon_temple")){
            return TAStructureTags.MOON_TEMPLE;
        }else {
            return TAStructureTags.RUNESTONE_DUNGEON;
        }
    }

    private void setSelectedDungeon(ItemStack stack, String dungeon) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("dungeon", dungeon);
    }
}