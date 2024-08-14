package cn.teampancake.theaurorian.common.level.storage.predicate;

import cn.teampancake.theaurorian.common.registry.TALootItemConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record NightWalkerEnchantmentCondition(LootContext.EntityTarget attacker) implements LootItemCondition {

    public static final MapCodec<LegendaryHeroEnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("attacker").forGetter(LegendaryHeroEnchantmentCondition::attacker)
    ).apply(instance, LegendaryHeroEnchantmentCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.NIGHT_WALKER_ENCHANTMENT.get();
    }

    @Override
    public boolean test(LootContext context) {
        if (context.getParamOrNull(this.attacker.getParam()) instanceof LivingEntity entity) {
            return context.getLevel().getRawBrightness(entity.getOnPos(), 0) < 5;
        }

        return false;
    }

    public static LootItemCondition.Builder build() {
        return () -> new NightWalkerEnchantmentCondition(LootContext.EntityTarget.ATTACKER);
    }

}