package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlueberryBush extends SweetBerryBushBlock {

    public BlueberryBush() {
        super(TABlockProperties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).addBlockTag(
                BlockTags.FALL_DAMAGE_RESETTING,
                BlockTags.SWORD_EFFICIENT,
                BlockTags.MINEABLE_WITH_AXE));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(TAItems.BLUEBERRY.get());
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        }
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        int i = state.getValue(AGE);
        boolean flag = i == 3;
        if (i > 1) {
            int j = 1 + level.random.nextInt(2);
            float pitch = 0.8F + level.random.nextFloat() * 0.4F;
            popResource(level, pos, new ItemStack(TAItems.BLUEBERRY.get(), j + (flag ? 1 : 0)));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, pitch);
            BlockState blockstate = state.setValue(AGE, 1);
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useWithoutItem(state, level, pos, player, hit);
        }
    }

}