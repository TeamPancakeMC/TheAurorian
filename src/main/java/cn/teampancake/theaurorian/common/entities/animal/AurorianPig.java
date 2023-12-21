package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.data.tags.TABlockTags;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class AurorianPig extends Pig {

    public AurorianPig(EntityType<? extends AurorianPig> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.SILK_BERRY.get());
    }

    @SuppressWarnings("unused")
    public static boolean checkAurorianPigSpawnRules(EntityType<AurorianPig> aurorianPig, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

}