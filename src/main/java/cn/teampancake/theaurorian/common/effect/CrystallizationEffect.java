package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.UUID;

public class CrystallizationEffect extends IncurableEffect {

    public CrystallizationEffect() {
        super(MobEffectCategory.HARMFUL, 0x17d1c7);
    }

    @Override
    public void onEffectExpired(LivingEntity livingEntity, int amplifier) {
        List<ResourceLocation> list = livingEntity.getData(TAAttachmentTypes.ATTRIBUTE_UNIVERSAL_IDS);
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (!list.isEmpty() && attribute != null) {
            list.forEach(attribute::removeModifier);
            list.clear();
        }
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
        if (attribute != null && livingEntity.getMaxHealth() > 2.0D && Math.random() <= (amplifier + 1) * 0.1F) {
            UUID uuid = Mth.createInsecureUUID(RandomSource.createNewThreadLocalInstance());
            ResourceLocation id = TheAurorian.prefix("crystallization-" + uuid);
            AttributeModifier modifier = new AttributeModifier(id, -0.1D, operation);
            livingEntity.getData(TAAttachmentTypes.ATTRIBUTE_UNIVERSAL_IDS).add(modifier.id());
            attribute.addTransientModifier(modifier);
        }
    }

}