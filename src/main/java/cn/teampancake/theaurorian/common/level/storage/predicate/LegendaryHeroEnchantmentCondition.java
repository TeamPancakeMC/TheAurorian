package cn.teampancake.theaurorian.common.level.storage.predicate;

import cn.teampancake.theaurorian.common.registry.TALootItemConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.AABB;

import java.util.List;

public record LegendaryHeroEnchantmentCondition(LootContext.EntityTarget attacker) implements LootItemCondition {

    public static final MapCodec<LegendaryHeroEnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("attacker").forGetter(LegendaryHeroEnchantmentCondition::attacker)
    ).apply(instance, LegendaryHeroEnchantmentCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.LEGENDARY_HERO_ENCHANTMENT.get();
    }

    @Override
    public boolean test(LootContext context) {
        if (context.getParam(this.attacker.getParam()) instanceof ServerPlayer serverPlayer) {
            AABB aabb = serverPlayer.getBoundingBox().inflate(20.0D);
            List<LivingEntity> canEnhances = context.getLevel().getEntitiesOfClass(LivingEntity.class, aabb, object ->
                    object instanceof ServerPlayer player && player != serverPlayer || object instanceof Villager);
            return !canEnhances.isEmpty();
        }

        return false;
    }

}