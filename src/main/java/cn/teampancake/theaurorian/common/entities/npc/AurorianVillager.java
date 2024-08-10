package cn.teampancake.theaurorian.common.entities.npc;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAVillagerType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AurorianVillager extends Villager {

    public AurorianVillager(EntityType<? extends AurorianVillager> entityType, Level level) {
        super(entityType, level, TAVillagerType.AURORIAN_PLAINS.get());
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Nullable
    @Override
    public Villager getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        Villager villager = new AurorianVillager(TAEntityTypes.AURORIAN_VILLAGER.get(), pLevel);
        villager.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.BREEDING, null, null);
        return villager;
    }
}
