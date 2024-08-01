package cn.teampancake.theaurorian.common.entities.npc;

import cn.teampancake.theaurorian.common.registry.TAVillagerType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AurorianVillager extends Villager {

    public AurorianVillager(EntityType<? extends AurorianVillager> entityType, Level level) {
        super(entityType, level, TAVillagerType.AURORIAN_PLAINS.get());
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 48.0);
    }
}
