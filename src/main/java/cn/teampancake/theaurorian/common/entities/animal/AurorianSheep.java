package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class AurorianSheep extends Sheep {

    public AurorianSheep(EntityType<? extends AurorianSheep> type, Level level) {
        super(type, level);
    }

    @Nullable @Override
    public Sheep getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return TAEntityTypes.AURORIAN_SHEEP.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.LAVENDER.get());
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setColor(getRandomSheepColor(level.getRandom()));
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public static DyeColor getRandomSheepColor(RandomSource random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return DyeColor.WHITE;
        } else if (i < 10) {
            return DyeColor.LIGHT_GRAY;
        } else if (i < 18) {
            return DyeColor.LIGHT_BLUE;
        } else {
            return DyeColor.PURPLE;
        }
    }

    @SuppressWarnings("unused")
    public static boolean checkAurorianSheepSpawnRules(EntityType<AurorianSheep> aurorianSheep, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

}