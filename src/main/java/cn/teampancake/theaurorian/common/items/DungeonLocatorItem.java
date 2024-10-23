package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAStructureTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;

import javax.annotation.Nullable;
import java.util.Optional;

public class DungeonLocatorItem extends Item {

    public DungeonLocatorItem() {
        super(TAItemProperties.get().durability(30).rarity(Rarity.EPIC).addItemTag(TAItemTags.IS_RARE).hasTooltips().isSimpleModelItem());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        DataComponentType<CustomData> customData = DataComponents.CUSTOM_DATA;
        CompoundTag compoundTag = itemInHand.getOrDefault(customData, CustomData.EMPTY).copyTag();
        if (player.isCrouching()) {
            switch (compoundTag.getString("dungeon")) {
                case "moon_temple" -> {
                    this.setSelectedDungeon(itemInHand, "runestone_dungeon");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator1"), true);
                }
                case "darkstone_dungeon" -> {
                    this.setSelectedDungeon(itemInHand, "moon_temple");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator3"), true);
                }
                default -> {
                    this.setSelectedDungeon(itemInHand, "darkstone_dungeon");
                    player.displayClientMessage(Component.translatable("theaurorian.item.locator2"), true);
                }
            }
        } else {
            BlockPos dungeon;
            if (level instanceof ServerLevel serverLevel) {
                Optional<HolderSet.Named<Structure>> optional = level.registryAccess().registryOrThrow(Registries.STRUCTURE).getTag(getSelectedDungeon(itemInHand));
                Pair<BlockPos, Holder<Structure>> result = this.findNearestMapStructure(serverLevel, optional.get(), player.blockPosition(), 600, false);
                dungeon = result == null ? null : result.getFirst();
                if (dungeon != null && player instanceof ServerPlayer serverPlayer) {
                    compoundTag.putInt("block_pos_x", dungeon.getX());
                    compoundTag.putInt("block_pos_z", dungeon.getZ());
                    itemInHand.set(customData, CustomData.of(compoundTag));
                    itemInHand.hurtAndBreak(1, serverLevel, serverPlayer, item -> {});
                }
            }

            float pitch = 0.4F / (level.random.nextFloat() * 0.4F + 0.8F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.NEUTRAL, 0.5F, pitch);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.success(itemInHand);
        }

        return InteractionResultHolder.pass(itemInHand);
    }

    @Nullable
    public Pair<BlockPos, Holder<Structure>> findNearestMapStructure(ServerLevel serverLevel, HolderSet<Structure> structureHolderSet, BlockPos pos, int range, boolean findUnexplored) {
        ChunkGenerator generator = serverLevel.getChunkSource().getGenerator();
        Pair<BlockPos, Holder<Structure>> nearest = generator.findNearestMapStructure(serverLevel, structureHolderSet, pos, range, findUnexplored);
        if (nearest == null) return null;
        return nearest.getFirst().distManhattan(pos) <= 600 ? nearest : null;
    }

    private TagKey<Structure> getSelectedDungeon(ItemStack stack) {
        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        String blockName = nbt.getString("dungeon");
        if (blockName.equals("darkstone_dungeon")) {
            return TAStructureTags.DARKSTONE_DUNGEON;
        } else if (blockName.equals("moon_temple")) {
            return TAStructureTags.MOON_TEMPLE;
        } else {
            return TAStructureTags.RUNESTONE_DUNGEON;
        }
    }

    private void setSelectedDungeon(ItemStack stack, String dungeon) {
        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.putString("dungeon", dungeon);
    }

}