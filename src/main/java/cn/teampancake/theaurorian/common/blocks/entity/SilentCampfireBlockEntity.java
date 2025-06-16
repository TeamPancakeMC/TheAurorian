package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class SilentCampfireBlockEntity extends CampfireBlockEntity {

    public SilentCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return TABlockEntityTypes.SILENT_CAMPFIRE.get();
    }

    public static void cookTick(Level level, BlockPos pos, BlockState state, SilentCampfireBlockEntity blockEntity) {
        boolean flag = false;
        for (int i = 0; i < blockEntity.items.size(); i++) {
            ItemStack inputStack = blockEntity.items.get(i);
            if (!inputStack.isEmpty()) {
                flag = true;
                blockEntity.cookingProgress[i]++;
                if (blockEntity.cookingProgress[i] >= blockEntity.cookingTime[i]) {
                    SingleRecipeInput input = new SingleRecipeInput(inputStack);
                    ItemStack resultStack = blockEntity.quickCheck.getRecipeFor(input, level)
                            .map(holder -> holder.value().assemble(input, level.registryAccess())).orElse(inputStack);
                    if (resultStack.isItemEnabled(level.enabledFeatures())) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), resultStack);
                        blockEntity.items.set(i, ItemStack.EMPTY);
                        level.sendBlockUpdated(pos, state, state, 3);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
                    }
                }
            }
        }

        if (flag) {
            if (level.getGameTime() % 20 == 0) {
                AABB aabb = new AABB(pos).inflate(7).expandTowards(0.0, level.getHeight(), 0.0);
                for (Player player : level.getEntitiesOfClass(Player.class, aabb)) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.WARM, 100));
                }
            }

            setChanged(level, pos, state);
        }
    }

}