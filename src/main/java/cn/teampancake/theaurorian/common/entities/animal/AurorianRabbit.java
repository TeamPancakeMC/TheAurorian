package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.data.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AurorianRabbit extends Rabbit {

    public AurorianRabbit(EntityType<? extends AurorianRabbit> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.SILK_BERRY.get());
    }

    @SuppressWarnings("unused")
    public static boolean checkAurorianRabbitSpawnRules(EntityType<AurorianRabbit> rabbit, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

}