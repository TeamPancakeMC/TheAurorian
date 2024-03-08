package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.CeruleanArrow;
import cn.teampancake.theaurorian.common.items.CrystalArrow;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.google.common.collect.Maps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class VanillaItemCompat {

    @SubscribeEvent
    public static void registerFurnaceFuels(FurnaceFuelBurnTimeEvent event) {
        Map<Item, Integer> map = Maps.newLinkedHashMap();
        map.put(TABlocks.AURORIAN_CRAFTING_TABLE.get().asItem(), 300);
        map.put(TABlocks.SILENT_WOOD_LADDER.get().asItem(), 300);
        map.put(TAItems.SILENT_WOOD_STICK.get(), 100);
        map.put(TAItems.SILENT_WOOD_BOW.get(), 300);
        map.put(TAItems.AURORIAN_COAL.get(), 1500);
        for (Item item : map.keySet()) {
            if (event.getItemStack().getItem() == item) {
                event.setBurnTime(map.get(item));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BusModEvents {

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        }

        @SubscribeEvent
        public static void registerCompostables(FMLCommonSetupEvent event) {
            ComposterBlock.add(0.3F, TABlocks.AURORIAN_GRASS.get());
            ComposterBlock.add(0.3F, TABlocks.AURORIAN_GRASS_LIGHT.get());
            ComposterBlock.add(0.3F, TABlocks.SILENT_TREE_SAPLING.get());
            ComposterBlock.add(0.3F, TABlocks.SILENT_TREE_LEAVES.get());
            ComposterBlock.add(0.3F, TABlocks.WEEPING_WILLOW_LEAVES.get());
            ComposterBlock.add(0.3F, TABlocks.CURTAIN_TREE_LEAVES.get());
            ComposterBlock.add(0.3F, TABlocks.CURSED_FROST_TREE_LEAVES.get());
            ComposterBlock.add(0.65F, TABlocks.AURORIAN_FLOWER_1.get());
            ComposterBlock.add(0.65F, TABlocks.AURORIAN_FLOWER_2.get());
            ComposterBlock.add(0.65F, TABlocks.AURORIAN_FLOWER_3.get());
            ComposterBlock.add(0.65F, TABlocks.EQUINOX_FLOWER.get());
            ComposterBlock.add(0.65F, TABlocks.WICK_GRASS.get());
            ComposterBlock.add(0.65F, TABlocks.LAVENDER_PLANT.get());
            ComposterBlock.add(0.65F, TABlocks.PETUNIA_PLANT.get());
            ComposterBlock.add(0.65F, TABlocks.ICE_CALENDULA.get());
            ComposterBlock.add(0.65F, TABlocks.AURORIAN_WINTER_ROOT.get());
            ComposterBlock.add(0.3F, TAItems.WEEPING_WILLOW_SAP.get());
            ComposterBlock.add(0.3F, TAItems.LAVENDER_SEEDS.get());
            ComposterBlock.add(0.3F, TAItems.SILK_BERRY.get());
            ComposterBlock.add(0.3F, TAItems.BLUEBERRY.get());
            ComposterBlock.add(0.85F, TAItems.LAVENDER_BREAD.get());
            ComposterBlock.add(0.65F, TAItems.TALL_WICK_GRASS.get());
        }

        @SubscribeEvent
        public static void registerDispenserBehavior(FMLCommonSetupEvent event) {
            DispenserBlock.registerBehavior(TAItems.CERULEAN_ARROW.get(), new CeruleanArrow.Dispense());
            DispenserBlock.registerBehavior(TAItems.CRYSTAL_ARROW.get(), new CrystalArrow.Dispense());
            DispenserBlock.registerBehavior(TAItems.MOON_WATER_BUCKET.get(), new DispenseBucket());
            DispenserBlock.registerBehavior(TAItems.MOON_FISH_BUCKET.get(), new DispenseBucket());
            DispenserBlock.registerBehavior(TAItems.AURORIAN_WINGED_FISH_BUCKET.get(), new DispenseBucket());
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerItemProperties(FMLClientSetupEvent event) {
            ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                    entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
            ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            ItemProperties.register(TAItems.KEEPERS_BOW.get(), AurorianMod.prefix("pull"), ((stack, level, entity, seed) ->
                    entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F));
            ItemProperties.register(TAItems.KEEPERS_BOW.get(), AurorianMod.prefix("pulling"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            ItemProperties.register(TAItems.STAR_OCEAN_CROSSBOW.get(), AurorianMod.prefix("pull"), (stack, level, entity, seed) ->
                    entity == null || CrossbowItem.isCharged(stack) ? 0.0F :
                            (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(stack));
            ItemProperties.register(TAItems.STAR_OCEAN_CROSSBOW.get(), AurorianMod.prefix("arrow_pulling"), (stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
            ItemProperties.register(TAItems.STAR_OCEAN_CROSSBOW.get(), AurorianMod.prefix("arrow_charged"), (stack, level, entity, seed) ->
                    CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
            ItemProperties.register(TAItems.STAR_OCEAN_CROSSBOW.get(), AurorianMod.prefix("firework_pulling"), (stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack)
                            && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            ItemProperties.register(TAItems.STAR_OCEAN_CROSSBOW.get(), AurorianMod.prefix("firework_charged"), (stack, level, entity, seed) ->
                    CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            ItemProperties.register(TAItems.CRYSTALLINE_SWORD.get(), AurorianMod.prefix("shoot"), ((stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
            for (Item item : TACommonUtils.getKnownItems()) {
                if (item instanceof ShieldItem) {
                    ItemProperties.register(item, AurorianMod.prefix("blocking"), ((stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
                }
            }
        }

    }

    @MethodsReturnNonnullByDefault
    private static class DispenseBucket extends DefaultDispenseItemBehavior {

        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem containerItem = (DispensibleContainerItem)stack.getItem();
            BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
            if (containerItem.emptyContents(null, level, blockPos, null, stack)) {
                containerItem.checkExtraContent(null, level, stack, blockPos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }

    }

}