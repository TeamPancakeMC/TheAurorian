package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import cn.teampancake.theaurorian.common.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.common.utils.AurorianUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
@SuppressWarnings("ConstantConditions")
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void onRenderItemTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if (stack.getItem() instanceof ITooltipsItem tooltipsItem && tooltipsItem.isHasTooltips()) {
            showTooltips(tooltip, stack.getItem());
        }

        if (stack.getItem() instanceof ForgeSpawnEggItem spawnEggItem) {
            String namespace = ForgeRegistries.ITEMS.getKey(spawnEggItem).getNamespace();
            if (namespace.equals(AurorianMod.MOD_ID)) {
                showTooltips(tooltip, spawnEggItem);
            }
        }

        Ingredient repairItem = null;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial() == TAArmorMaterials.SPECTRAL) {
                showTooltips(tooltip, armorItem);
                repairItem = armorItem.getMaterial().getRepairIngredient();
            }
        } else if (stack.getItem() instanceof TieredItem tieredItem) {
            repairItem = tieredItem.getTier().getRepairIngredient();
        }

        if (repairItem == Ingredient.of(TAItems.AURORIAN_STEEL.get())) {
            AurorianSteelHelper.getAurorianSteelInfo(stack, tooltip);
        }

        if (repairItem == Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())){
            //TODO CRYSTALLINE_INGOT TOOLTIP
        }
    }

    private static void showTooltips(List<Component> tooltip, Item item) {
        tooltip.add(Component.translatable("tooltips." + item.getDescriptionId()));
    }

    @SubscribeEvent
    public static void onSpectralSuit(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        entity.getArmorSlots().forEach(itemStack -> {
            if (itemStack.is(TAItemTags.SPECTRAL_ARMOR)
                    && AurorianUtil.randomChanceOf(AurorianConfig.CONFIG_SPECTRAL_ARMOR_CLEANSE_CHANCE.get())
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
    public static void SlimeBootsJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.isShiftKeyDown() && !player.onGround()) return;
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(TAItems.AURORIAN_SLIME_BOOTS.get()) && player.getCooldowns().isOnCooldown(stack.getItem())) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.5, player.getDeltaMovement().z);
                    player.getCooldowns().addCooldown(stack.getItem(), AurorianConfig.Config_SlimeBootsCooldown.get());
                }
            });
        }
    }

    @SubscribeEvent
    public static void SlimeBootsFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(TAItems.AURORIAN_SLIME_BOOTS.get()) && event.getDistance() > 3f) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.75, player.getDeltaMovement().z);
                    event.setCanceled(true);
                }
            });
        }
    }
    @SubscribeEvent
    public static void OnStartItem(LivingEntityUseItemEvent.Start event){
        Level level= event.getEntity().level();
        if(event.getEntity() instanceof Player player) {
            if (event.getItem().is(TAItems.CRYSTALLINE_SWORD.get())) {
            Vec3 lookAngle = player.getLookAngle().normalize();
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    double d6 = level.random.nextGaussian() * 0.02D;
                    double d7 = level.random.nextGaussian() * 0.02D;
                    double d8 = level.random.nextGaussian() * 0.02D;
                    level.addParticle(TAParticleTypes.MAGIC_PURPLE.get(),
                            player.getX()+lookAngle.x+Math.sin(i*Math.PI/15),
                            player.getY()+1+lookAngle.y+Math.cos(j*Math.PI/15)*(Math.abs(Math.sin(i*Math.PI/15+Math.PI/2))),
                            player.getZ()+lookAngle.z+Math.cos(j*Math.PI/15)*(Math.abs(Math.sin(i*Math.PI/15+Math.PI/2))),
                            d6, d7, d8);
                }
            }
            }
        }
    }

    @SubscribeEvent
    public static void OnUsingItem(LivingEntityUseItemEvent.Tick event){
        Level level= event.getEntity().level();
        if(event.getEntity() instanceof Player player) {
            if (event.getItem().is(TAItems.CRYSTALLINE_SWORD.get())) {
                if(event.getDuration()>=60)
                    event.getItem().finishUsingItem(level, player);
            }
        }
    }
}