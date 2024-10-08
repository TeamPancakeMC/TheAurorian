package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
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
    public static void onRenderItemTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if (stack.getItem() instanceof ITooltipsItem tooltipsItem && tooltipsItem.isHasTooltips()) {
            tooltip.add(Component.translatable("tooltips." + stack.getItem().getDescriptionId()));
        }

        Ingredient repairItem = null;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial() == TAArmorMaterials.SPECTRAL) {
                repairItem = armorItem.getMaterial().getRepairIngredient();
            }
        } else if (stack.getItem() instanceof TieredItem tieredItem) {
            repairItem = tieredItem.getTier().getRepairIngredient();
        }

        if (repairItem == Ingredient.of(TAItems.AURORIAN_STEEL.get())) {
            AurorianSteelHelper.getAurorianSteelInfo(stack, tooltip);
        }

        if (repairItem == Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())) {
            //TODO CRYSTALLINE_INGOT TOOLTIP
        }
    }

    @SubscribeEvent
    public static void onSpectralSuit(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        entity.getArmorSlots().forEach(itemStack -> {
            if (itemStack.is(TAItemTags.SPECTRAL_ARMOR)
                    && level.random.nextDouble() <= 0.06F
                    && entity instanceof Player player) {
                player.getActiveEffects().forEach(effectInstance -> {
                    if (!effectInstance.getEffect().isBeneficial()) {
                        player.removeEffect(effectInstance.getEffect());
                    }
                });
            }
        });
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.isShiftKeyDown() && !player.onGround()) return;
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(TAItems.AURORIAN_SLIME_BOOTS.get()) && player.getCooldowns().isOnCooldown(stack.getItem())) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.5F, player.getDeltaMovement().z);
                    player.getCooldowns().addCooldown(stack.getItem(), 100);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(TAItems.AURORIAN_SLIME_BOOTS.get()) && event.getDistance() > 3.0F) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.75F, player.getDeltaMovement().z);
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onStartUseItem(LivingEntityUseItemEvent.Start event){
        Level level = event.getEntity().level();
        if (event.getEntity() instanceof Player player) {
            if (event.getItem().is(TAItems.CRYSTALLINE_SWORD.get())) {
                Vec3 lookAngle = player.getLookAngle().normalize();
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 30; j++) {
                        double d6 = level.random.nextGaussian() * 0.02D;
                        double d7 = level.random.nextGaussian() * 0.02D;
                        double d8 = level.random.nextGaussian() * 0.02D;
                        double v = Math.cos(j * Math.PI / 15) * (Math.abs(Math.sin(i * Math.PI / 15 + Math.PI / 2)));
                        level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                                player.getX() + lookAngle.x + Math.sin(i * Math.PI / 15),
                                player.getY() + lookAngle.y + v + 1.0D,
                                player.getZ() + lookAngle.z + v, d6, d7, d8);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFinishUseItem(PlayerInteractEvent.RightClickItem event){
        Level level = event.getEntity().level();
        Player player = event.getEntity();
            if(event.getItemStack().is(TAItems.DUNGEON_LOCATOR.get())){
                double y = player.getY() + 1;
                double targetx = event.getItemStack().getOrCreateTag().getInt("BlockPosX") > player.getX()?0.5D:-0.5D;
                double targetz = event.getItemStack().getOrCreateTag().getInt("BlockPosZ") > player.getZ()?0.5D:-0.5D;
                double originx = player.getX();
                double originz = player.getZ();

                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.CLOUD, originx, y, originz, targetx, 0.25D, targetz);
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, event.getItemStack()), originx, y, originz, targetx, 0.25D, targetz);
                }
            }

    }

    @SubscribeEvent
    public static void onUsingItem(LivingEntityUseItemEvent.Tick event){
        if (event.getEntity() instanceof Player player) {
            if (event.getItem().is(TAItems.CRYSTALLINE_SWORD.get())) {
                if (player.getTicksUsingItem() >= 60) {
                    player.releaseUsingItem();
                }
            }
        }
    }

}