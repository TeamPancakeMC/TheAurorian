package cn.teampancake.theaurorian.common.level.storage.predicate;

import cn.teampancake.theaurorian.common.registry.TALootItemConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record SavageEnchantmentCondition(LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final MapCodec<SavageEnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(SavageEnchantmentCondition::entityTarget)
    ).apply(instance, SavageEnchantmentCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.SAVAGE_ENCHANTMENT.get();
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(this.entityTarget.getParam());
        return (entity instanceof Animal || entity instanceof NeutralMob) && entity.isAlive();
    }

    public static LootItemCondition.Builder get() {
        return () -> new SavageEnchantmentCondition(LootContext.EntityTarget.THIS);
    }

}