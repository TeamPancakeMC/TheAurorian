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

public record MoltenCoreEnchantmentCondition(LootContext.EntityTarget attacker) implements LootItemCondition {

    public static final MapCodec<MoltenCoreEnchantmentCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LootContext.EntityTarget.CODEC.fieldOf("attacker").forGetter(MoltenCoreEnchantmentCondition::attacker)
    ).apply(instance, MoltenCoreEnchantmentCondition::new));

    @Override
    public LootItemConditionType getType() {
        return TALootItemConditions.MOLTEN_CORE_ENCHANTMENT.get();
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.getParamOrNull(this.attacker.getParam());
        return entity instanceof LivingEntity && entity.getData(TAAttachmentTypes.TICKS_THERMAL_ENHANCEMENT) > 0;
    }

    public static LootItemCondition.Builder build() {
        return () -> new MoltenCoreEnchantmentCondition(LootContext.EntityTarget.ATTACKER);
    }

}