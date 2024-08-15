package cn.teampancake.theaurorian.common.level.storage.predicate;

import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TALootItemConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record OverworldAurorianMobCondition(LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final MapCodec<OverworldAurorianMobCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(OverworldAurorianMobCondition::entityTarget)
    ).apply(instance, OverworldAurorianMobCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.OVERWORLD_AURORIAN_MOB.get();
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(this.entityTarget.getParam());
        return entity instanceof LivingEntity living && living.getData(TAAttachmentTypes.SPAWN_IN_OVERWORLD) && living.getLastAttacker() == null;
    }

    public static LootItemCondition.Builder build() {
        return () -> new OverworldAurorianMobCondition(LootContext.EntityTarget.THIS);
    }

}