package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import cn.teampancake.theaurorian.registry.ModItems;
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
        return stack.is(ModItems.SILK_BERRY.get());
    }

    public static boolean checkAurorianPigSpawnRules(EntityType<AurorianPig> aurorianPig, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(ModBlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkAnimalSpawnRules(aurorianPig, level, spawnType, pos, random);
    }

}