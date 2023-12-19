package cn.teampancake.theaurorian.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAArmorMaterials;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.data.tags.TAItemTags;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.utils.AurorianSteelHelper;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import cn.teampancake.theaurorian.utils.TATooltipRenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
@SuppressWarnings("ConstantConditions")
public class ItemEventSubscriber {

    private static final ResourceLocation UNCOMMON = AurorianMod.prefix("textures/gui/tooltips/uncommon.png");
    private static final ResourceLocation RARE = AurorianMod.prefix("textures/gui/tooltips/rare.png");
    private static final ResourceLocation EPIC = AurorianMod.prefix("textures/gui/tooltips/epic.png");
    private static final ResourceLocation LEGENDARY = AurorianMod.prefix("textures/gui/tooltips/legendary.png");
    private static final ResourceLocation MYTHICAL = AurorianMod.prefix("textures/gui/tooltips/mythical.png");

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderTooltips(RenderTooltipEvent.Pre event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.is(TAItemTags.HAS_CUSTOM_TOOLTIPS)) {
            event.setCanceled(true);
            if (itemStack.is(TAItemTags.IS_UNCOMMON)) {
                int[] xOffset = new int[] {7, 7, 9, 9};
                int[] yOffset = new int[] {-17, -11, -17, -11, -18};
                int[] uOffset = new int[] {0, 0, 8, 8, 16};
                int[] vOffset = new int[] {0, 8, 0, 8, 0};
                int[] uWidth = new int[] {8, 8, 8, 8, 12};
                int[] vHeight = new int[] {8, 8, 8, 8, 6};
                TATooltipRenderUtils.renderTooltips(event, UNCOMMON, Boolean.FALSE, (0xff686f99),
                        (0xfff1f2ff), (0xff686f99), xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
            } else if (itemStack.is(TAItemTags.IS_RARE)) {
                int[] xOffset = new int[] {7, 7, 8, 8};
                int[] yOffset = new int[] {-17, -12, -17, -12, -20};
                int[] uOffset = new int[] {0, 0, 9, 9, 18};
                int[] vOffset = new int[] {0, 9, 0, 9, 0};
                int[] uWidth = new int[] {9, 9, 9, 9, 12};
                int[] vHeight = new int[] {9, 9, 9, 9, 10};
                TATooltipRenderUtils.renderTooltips(event, RARE, Boolean.FALSE, (0xff686f99),
                        (0xfff1f2ff), (0xff686f99), xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
            } else if (itemStack.is(TAItemTags.IS_EPIC)) {
                int[] xOffset = new int[] {7, 7, 8, 8};
                int[] yOffset = new int[] {-17, -12, -17, -12, -21};
                int[] uOffset = new int[] {0, 0, 9, 9, 18};
                int[] vOffset = new int[] {0, 9, 0, 9, 0};
                int[] uWidth = new int[] {9, 9, 9, 9, 18};
                int[] vHeight = new int[] {9, 9, 9, 9, 11};
                TATooltipRenderUtils.renderTooltips(event, EPIC, Boolean.FALSE, (0xff804085),
                        (0xffe3a084), (0xff804085), xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
            } else if (itemStack.is(TAItemTags.IS_LEGENDARY)) {
                int[] xOffset = new int[] {7, 7, 8, 8};
                int[] yOffset = new int[] {-17, -12, -17, -12, -20, -7};
                int[] uOffset = new int[] {0, 0, 9, 9, 18, 18};
                int[] vOffset = new int[] {0, 9, 0, 9, 0, 11};
                int[] uWidth = new int[] {9, 9, 9, 9, 18, 10};
                int[] vHeight = new int[] {9, 9, 9, 9, 11, 4};
                TATooltipRenderUtils.renderTooltips(event, LEGENDARY, Boolean.TRUE, (0xff412051),
                        (0xffe88a36), (0xff14182e), xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
            } else if (itemStack.is(TAItemTags.IS_MYTHICAL)) {
                int[] xOffset = new int[] {7, 7, 8, 8};
                int[] yOffset = new int[] {-17, -12, -17, -12, -20, -7};
                int[] uOffset = new int[] {0, 0, 9, 9, 18, 18};
                int[] vOffset = new int[] {0, 9, 0, 9, 0, 12};
                int[] uWidth = new int[] {9, 9, 9, 9, 18, 14};
                int[] vHeight = new int[] {9, 9, 9, 9, 12, 5};
                TATooltipRenderUtils.renderTooltips(event, MYTHICAL, Boolean.TRUE, (0xff621748),
                        (0xff3d003d), (0xff3d003d), xOffset, yOffset, uOffset, vOffset, uWidth, vHeight);
            }
        }
    }

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
        String keyPart = "string.theaurorian.tooltip.";
        String path = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
        tooltip.add(Component.translatable(keyPart + path));
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

}