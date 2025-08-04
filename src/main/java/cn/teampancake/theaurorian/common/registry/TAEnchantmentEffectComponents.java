package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Consumer;

public class TAEnchantmentEffectComponents {

    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_EFFECT_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, TheAurorian.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> ITEM_USE = register("item_use", LootContextParamSets.ENCHANTED_ITEM);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> BLOCK_USE = register("block_use", LootContextParamSets.BLOCK_USE);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> PROJECTILE_TICK = register("projectile_tick", LootContextParamSets.ENCHANTED_ENTITY);

    private static DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>>> register(String name, LootContextParamSet paramSet) {
        return ENCHANTMENT_EFFECT_COMPONENT_TYPES.register(name, () -> DataComponentType.<List<ConditionalEffect<EnchantmentEntityEffect>>>builder()
                .persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, paramSet).listOf()).build());
    }

    public static void onItemUse(ServerLevel serverLevel, LivingEntity entity) {
        ItemStack useItem = entity.getUseItem();
        EnchantmentHelper.runIterationOnItem(useItem, (enchantment, level) -> {
            LootContext context = Enchantment.itemContext(serverLevel, level, useItem);
            EnchantedItemInUse itemInUse = new EnchantedItemInUse(useItem, EquipmentSlot.MAINHAND, entity);
            Consumer<EnchantmentEntityEffect> applier = effect -> effect.apply(serverLevel, level, itemInUse, entity, entity.position());
            Enchantment.applyEffects(enchantment.value().getEffects(ITEM_USE.get()), context, applier);
        });
    }

    public static void onBlockUse(ServerLevel serverLevel, LivingEntity entity, BlockPos pos) {
        ItemStack useItem = entity.getUseItem();
        EnchantmentHelper.runIterationOnItem(useItem, (enchantment, level) -> {
            EnchantedItemInUse itemInUse = new EnchantedItemInUse(useItem, EquipmentSlot.MAINHAND, entity);
            LootContext context = Enchantment.blockHitContext(serverLevel, level, entity, Vec3.atCenterOf(pos), serverLevel.getBlockState(pos));
            Consumer<EnchantmentEntityEffect> applier = effect -> effect.apply(serverLevel, level, itemInUse, entity, Vec3.atCenterOf(pos));
            Enchantment.applyEffects(enchantment.value().getEffects(BLOCK_USE.get()), context, applier);
        });
    }

    public static void onProjectileTick(ServerLevel serverLevel, LivingEntity livingEntity, Projectile projectile) {
        ItemStack useItem = livingEntity.getUseItem();
        EnchantmentHelper.runIterationOnItem(useItem, (enchantment, level) -> {
            EnchantedItemInUse itemInUse = new EnchantedItemInUse(useItem, EquipmentSlot.MAINHAND, livingEntity);
            LootContext context = Enchantment.entityContext(serverLevel, level, projectile, projectile.position());
            Consumer<EnchantmentEntityEffect> applier = effect -> effect.apply(serverLevel, level, itemInUse, projectile, projectile.position());
            Enchantment.applyEffects(enchantment.value().getEffects(PROJECTILE_TICK.get()), context, applier);
        });
    }

}