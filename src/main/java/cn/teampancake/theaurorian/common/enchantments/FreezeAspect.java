package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.items.armor.MysteriumWoolArmor;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FreezeAspect extends Enchantment {

    public FreezeAspect() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return super.getMinCost(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (target instanceof LivingEntity livingEntity && !MysteriumWoolArmor.isWearFullArmor(livingEntity)) {
            livingEntity.addEffect(new MobEffectInstance(TAMobEffects.FROSTBITE.get(), (100 + level * 20), level));
        }
    }

}