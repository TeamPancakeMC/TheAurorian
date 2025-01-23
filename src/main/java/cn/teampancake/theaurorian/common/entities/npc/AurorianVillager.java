package cn.teampancake.theaurorian.common.entities.npc;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AurorianVillager extends Villager {

    public AurorianVillager(EntityType<? extends AurorianVillager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.level().isClientSide && this.isDeadOrDying() && this.random.nextFloat() <= 0.02F) {
            Spirit spirit = new Spirit(TAEntityTypes.SPIRIT.get(), this.level());
            spirit.setPos(this.position());
            this.level().addFreshEntity(spirit);
        }

        super.die(damageSource);
    }

}