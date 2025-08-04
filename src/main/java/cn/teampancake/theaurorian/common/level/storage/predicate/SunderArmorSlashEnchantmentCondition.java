package cn.teampancake.theaurorian.common.level.storage.predicate;

import cn.teampancake.theaurorian.common.registry.TALootItemConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record SunderArmorSlashEnchantmentCondition(LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final MapCodec<SunderArmorSlashEnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(SunderArmorSlashEnchantmentCondition::entityTarget)
    ).apply(instance, SunderArmorSlashEnchantmentCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.SUNDER_ARMOR_SLASH_ENCHANTMENT.get();
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(this.entityTarget.getParam());
        return entity instanceof LivingEntity livingEntity && livingEntity.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0D;
    }

    public static LootItemCondition.Builder get() {
        return () -> new SunderArmorSlashEnchantmentCondition(LootContext.EntityTarget.THIS);
    }

}