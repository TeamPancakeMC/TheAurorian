package cn.teampancake.theaurorian.common.enchantments;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public record SpringOfLifeEffect(int placeholder) implements EnchantmentEntityEffect {

    private static final DataComponentType<CustomData> SPRING_OF_LIFE = TADataComponents.SPRING_OF_LIFE.get();
    public static final MapCodec<SpringOfLifeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("placeholder").forGetter(SpringOfLifeEffect::placeholder)).apply(instance, SpringOfLifeEffect::new));

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        ItemStack chestItem = item.itemStack();
        if (entity instanceof ServerPlayer player && player.isAlive()) {
            Holder<Enchantment> holder1 = TAEnchantments.get(level, Enchantments.BINDING_CURSE);
            Holder<Enchantment> holder2 = TAEnchantments.get(level, TAEnchantments.SPRING_OF_LIFE);
            CompoundTag tag = chestItem.getOrDefault(SPRING_OF_LIFE, CustomData.EMPTY).copyTag();
            Set<Holder<Enchantment>> holderSet = chestItem.getTagEnchantments().keySet();
            boolean shouldHeal = tag.getBoolean("should_heal_player");
            int healAmount = tag.getInt("enchant_armor_heal_amount");
            if (player.getHealth() < player.getMaxHealth() * 0.1F && !shouldHeal) {
                tag.putBoolean("should_heal_player", Boolean.TRUE);
                chestItem.set(SPRING_OF_LIFE, CustomData.of(tag));
                chestItem.enchant(holder1, 1);
            }

            if (shouldHeal) {
                tag.putInt("enchant_armor_heal_amount", healAmount + 1);
                chestItem.set(SPRING_OF_LIFE, CustomData.of(tag));
                player.heal((player.getMaxHealth() * 0.01F));
                if (healAmount > 100) {
                    holderSet.remove(holder1);
                    holderSet.remove(holder2);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}