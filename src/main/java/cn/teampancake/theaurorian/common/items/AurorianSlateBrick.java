package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AurorianSlateBrick extends SimpleThrowProjectProjectile {

    public AurorianSlateBrick() {
        super(new Properties(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, TAEntityTypes.THROWN_SLATE_BRICK::get, 1.5F);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        RandomSource random = level.random;
        ItemStack itemInHand = attacker.getItemInHand(attacker.getUsedItemHand());
        float pitch = 0.4F / (random.nextFloat() * 0.4F + 0.8F);
        if (random.nextFloat() <= 0.3F) {
            if (attacker instanceof ServerPlayer player && !player.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }

            level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                    SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, 0.5F, pitch);
            if (level.isClientSide) {
                double bbWidth = target.getBbWidth();
                for (int i = 0; i < 16; ++i) {
                    ItemParticleOption particleOption = new ItemParticleOption(ParticleTypes.ITEM, itemInHand);
                    double px = target.getX() + (random.nextDouble() * bbWidth * 2.0D) - bbWidth;
                    double py = target.getY() + (random.nextDouble() * target.getBbHeight());
                    double pz = target.getZ() + (random.nextDouble() * bbWidth * 2.0D) - bbWidth;
                    double xSpeed = (random.nextDouble() - 0.5D) * 0.08D;
                    double ySpeed = (random.nextDouble() - 0.5D) * 0.08D;
                    double zSpeed = (random.nextDouble() - 0.5D) * 0.08D;
                    level.addParticle(particleOption, px, py, pz, xSpeed, ySpeed, zSpeed);
                }
            }
        }
        
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADDITION;
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 4.0D, operation));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 1.5D, operation));
        return slot.getType() == EquipmentSlot.Type.HAND ? builder.build() : ImmutableMultimap.of();
    }

}