package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.SourceOfTerra;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAArmorMaterials;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();
        ItemStack copyOfLeftStack = leftStack.copy();
        int maxDamage = copyOfLeftStack.getMaxDamage();
        if (copyOfLeftStack.isDamageableItem() && rightStack.is(TAItems.BROKEN_OX_HORN.get())) {
            int l = Math.min(copyOfLeftStack.getDamageValue(), maxDamage / 5);
            int i;
            if (l <= 0) {
                event.setOutput(ItemStack.EMPTY);
                event.setCost(0);
                event.setCanceled(true);
            }

            for (i = 0; l > 0 && i < rightStack.getCount(); ++i) {
                int j = copyOfLeftStack.getDamageValue() - l;
                copyOfLeftStack.setDamageValue(j);
                l = Math.min(copyOfLeftStack.getDamageValue(), maxDamage / 5);
            }

            event.setCost(10);
            event.setMaterialCost(1);
            event.setOutput(copyOfLeftStack);
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        FoodProperties foodProperties = stack.get(DataComponents.FOOD);
        Boolean infusePotion = stack.get(TADataComponents.INFUSED_POTION);
        SourceOfTerra sourceOfTerra = stack.get(TADataComponents.SOURCE_OF_TERRA.get());
        if (stack.getItem().components().has(TADataComponents.EXTRA_TOOLTIP.get())) {
            tooltip.add(Component.translatable("tooltips." + stack.getItem().getDescriptionId()));
        }

        if (foodProperties != null && infusePotion != null && infusePotion) {
            float tickRate = event.getContext().tickRate();
            List<MobEffectInstance> foodEffects = new ArrayList<>();
            foodProperties.effects().forEach(effect -> foodEffects.add(effect.effect()));
            PotionContents.addPotionTooltip(foodEffects, tooltip::add, (1.0F), tickRate);
        }

        if (sourceOfTerra != null) {
            sourceOfTerra.addToTooltip(event.getContext(), tooltip::add, event.getFlags());
        }

        Ingredient repairItem = null;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial() == TAArmorMaterials.SPECTRAL) {
                repairItem = armorItem.getMaterial().value().repairIngredient().get();
            }
        } else if (stack.getItem() instanceof TieredItem tieredItem) {
            repairItem = tieredItem.getTier().getRepairIngredient();
        }

        if (repairItem == Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())) {
            //TODO CRYSTALLINE_INGOT TOOLTIP
        }
    }

    @SubscribeEvent
    public static void onSpectralSuit(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        entity.getArmorSlots().forEach(itemStack -> {
            if (itemStack.is(TAItemTags.SPECTRAL_ARMOR)
                    && level.random.nextDouble() <= 0.06F
                    && entity instanceof Player player) {
                player.getActiveEffects().forEach(effectInstance -> {
                    if (!effectInstance.getEffect().value().isBeneficial()) {
                        player.removeEffect(effectInstance.getEffect());
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player && player.isShiftKeyDown() && player.onGround()) {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.FEET);
            if (stack.is(TAItems.AURORIAN_SLIME_BOOTS) && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                float jumpPower = player.getJumpPower(2.0F);
                Vec3 vec3 = player.getDeltaMovement();
                player.setDeltaMovement(vec3.x, jumpPower, vec3.z);
                player.getCooldowns().addCooldown(stack.getItem(), 100);
            }
        }
    }

    @SubscribeEvent
    public static void onFinishUseItem(PlayerInteractEvent.RightClickItem event){
        Level level = event.getEntity().level();
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        DataComponentType<CustomData> customData = DataComponents.CUSTOM_DATA;
        CompoundTag compoundTag = stack.getOrDefault(customData, CustomData.EMPTY).copyTag();
        if (stack.is(TAItems.DUNGEON_LOCATOR.get())) {
            double y = player.getY() + 1;
            double targetX = compoundTag.getInt("block_pos_x") > player.getX() ? 0.5D : -0.5D;
            double targetZ = compoundTag.getInt("block_pos_z") > player.getZ() ? 0.5D : -0.5D;
            double originX = player.getX();
            double originZ = player.getZ();
            for (int i = 0; i < 4; i++) {
                level.addParticle(ParticleTypes.CLOUD, originX, y, originZ, targetX, 0.25D, targetZ);
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), originX, y, originZ, targetX, 0.25D, targetZ);
            }
        }
    }

}