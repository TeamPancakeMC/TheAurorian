package cn.teampancake.theaurorian.event;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.ModArmorMaterials;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.data.tags.ModItemTags;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class ItemEventSubscriber {

    @SubscribeEvent
    public static void onRenderTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if (stack.getItem() instanceof ITooltipsItem tooltipsItem && tooltipsItem.isHasTooltips()) {
            showTooltips(tooltip, ForgeRegistries.ITEMS.getKey(stack.getItem()));
        }

        Ingredient repairItem = null;
        if (stack.getItem() instanceof ArmorItem armorItem){
            if (armorItem.getMaterial() == ModArmorMaterials.SPECTRAL) {
                showTooltips(tooltip, ForgeRegistries.ITEMS.getKey(armorItem));
                repairItem = armorItem.getMaterial().getRepairIngredient();
            }
        } else if (stack.getItem() instanceof TieredItem tieredItem) {
            repairItem = tieredItem.getTier().getRepairIngredient();
        }

        if (repairItem == Ingredient.of(ModItems.AURORIAN_STEEL.get())) {
            AurorianSteelHelper.getAurorianSteelInfo(stack, tooltip);
        }

        if(repairItem == Ingredient.of(ModItems.CRYSTALLINE_INGOT.get())){
            //TODO CRYSTALLINE_INGOT TOOLTIP
        }
    }

    private static void showTooltips(List<Component> tooltip, ResourceLocation key) {
        String keyPart = "string.theaurorian.tooltip.";
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable(keyPart + "shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else if (key != null) {
            tooltip.add(Component.translatable(keyPart + key.getPath()));
        }
    }
    @SubscribeEvent
    public static void onSpectralSuit(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(level.isClientSide()) return;
        entity.getArmorSlots().forEach(itemStack -> {
            if (itemStack.is(ModItemTags.SPECTRAL_ARMOR)
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
        if (event.getEntity() instanceof Player player){
            if (!player.isShiftKeyDown() && !player.onGround()) return;
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(ModItems.AURORIAN_SLIME_BOOTS.get()) && player.getCooldowns().isOnCooldown(stack.getItem())) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.5, player.getDeltaMovement().z);
                    player.getCooldowns().addCooldown(stack.getItem(), AurorianConfig.Config_SlimeBootsCooldown.get());
                }
            });
        }
    }

    @SubscribeEvent
    public static void SlimeBootsFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player){
            player.getArmorSlots().forEach(stack -> {
                if (stack.is(ModItems.AURORIAN_SLIME_BOOTS.get()) && event.getDistance() > 3f) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.75, player.getDeltaMovement().z);
                    event.setCanceled(true);
                }
            });
        }
    }
}