package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.screens.AlchemyTableScreen;
import cn.teampancake.theaurorian.client.gui.screens.MoonlightForgeScreen;
import cn.teampancake.theaurorian.client.gui.screens.ScrapperScreen;
import cn.teampancake.theaurorian.client.gui.screens.TAWaitingScreen;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.client.renderer.level.TASpecialEffects;
import cn.teampancake.theaurorian.common.blocks.state.TAWoodType;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.items.AurorianChestItem;
import cn.teampancake.theaurorian.common.items.weapon.CrystallineSword;
import cn.teampancake.theaurorian.common.network.*;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionTransitionScreenEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

/** @noinspection deprecation*/
@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class ModBusEventSubscriber {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(TAKeyMappings.ARMOR_SET);
    }

    @SubscribeEvent
    public static void registerNetworks(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(NightTypeS2CPacket.TYPE, NightTypeS2CPacket.STREAM_CODEC, NightTypeS2CPacket::handle);
        registrar.playToClient(FrostbiteS2CPacket.TYPE, FrostbiteS2CPacket.STREAM_CODEC, FrostbiteS2CPacket::handle);
        registrar.playToClient(FutureNightS2CPacket.TYPE, FutureNightS2CPacket.STREAM_CODEC, FutureNightS2CPacket::handle);
        registrar.playToServer(CrystalRuneSetC2SPacket.TYPE, CrystalRuneSetC2SPacket.STREAM_CODEC, CrystalRuneSetC2SPacket::handle);
        registrar.playToClient(RuneGameStartS2CPacket.TYPE, RuneGameStartS2CPacket.STREAM_CODEC, RuneGameStartS2CPacket::handle);
        registrar.playToServer(RuneGameWinC2SPacket.TYPE, RuneGameWinC2SPacket.STREAM_CODEC, RuneGameWinC2SPacket::handle);
        registrar.playToServer(RuneGameAwardStatC2SPacket.TYPE, RuneGameAwardStatC2SPacket.STREAM_CODEC, RuneGameAwardStatC2SPacket::handle);
        registrar.playToServer(RuneGameTimeConsumingRecordC2SPacket.TYPE, RuneGameTimeConsumingRecordC2SPacket.STREAM_CODEC, RuneGameTimeConsumingRecordC2SPacket::handle);
        registrar.playToClient(InteractWithSelenaS2CPacket.TYPE, InteractWithSelenaS2CPacket.STREAM_CODEC, InteractWithSelenaS2CPacket::handle);
        registrar.playToServer(PlayerDeathRespawnC2SPacket.TYPE, PlayerDeathRespawnC2SPacket.STREAM_CODEC, PlayerDeathRespawnC2SPacket::handle);
        registrar.playToClient(ShowDeathScreenS2CPacket.TYPE, ShowDeathScreenS2CPacket.STREAM_CODEC, ShowDeathScreenS2CPacket::handle);
        registrar.playToClient(ShowStunScreenS2CPacket.TYPE, ShowStunScreenS2CPacket.STREAM_CODEC, ShowStunScreenS2CPacket::handle);
        registrar.playToClient(ShowStarSignScreenS2CPacket.TYPE, ShowStarSignScreenS2CPacket.STREAM_CODEC, ShowStarSignScreenS2CPacket::handle);
        registrar.playToClient(ShowVagrantNoteScreenS2CPacket.TYPE, ShowVagrantNoteScreenS2CPacket.STREAM_CODEC, ShowVagrantNoteScreenS2CPacket::handle);
        registrar.playToClient(DisplayTextureActivationS2CPacket.TYPE, DisplayTextureActivationS2CPacket.STREAM_CODEC, DisplayTextureActivationS2CPacket::handle);
        registrar.playToClient(DisplayItemActivationS2CPacket.TYPE, DisplayItemActivationS2CPacket.STREAM_CODEC, DisplayItemActivationS2CPacket::handle);
        registrar.playToClient(DisplayActivationTickS2CPacket.TYPE, DisplayActivationTickS2CPacket.STREAM_CODEC, DisplayActivationTickS2CPacket::handle);
        registrar.playToClient(SylvanisProgressS2CPacket.TYPE, SylvanisProgressS2CPacket.STREAM_CODEC, SylvanisProgressS2CPacket::handle);
        registrar.playToClient(PlayerLostInForestS2CPacket.TYPE, PlayerLostInForestS2CPacket.STREAM_CODEC, PlayerLostInForestS2CPacket::handle);
        registrar.playToClient(PlayAurorianMusicS2CPacket.TYPE, PlayAurorianMusicS2CPacket.STREAM_CODEC, PlayAurorianMusicS2CPacket::handle);
        registrar.playToClient(SkyColorS2CPacket.TYPE, SkyColorS2CPacket.STREAM_CODEC, SkyColorS2CPacket::handle);
        registrar.playToClient(WorldNightColorS2CPacket.TYPE, WorldNightColorS2CPacket.STREAM_CODEC, WorldNightColorS2CPacket::handle);
        registrar.playToServer(NoteTeleportC2SPacket.TYPE, NoteTeleportC2SPacket.STREAM_CODEC, NoteTeleportC2SPacket::handle);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new CrystallineSword.CrystallineSwordUseAnim(), TAItems.CRYSTALLINE_SWORD);
        event.registerItem(new AurorianChestItem.RenderChestItem(), TAItems.AURORIAN_CHEST);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        new TASkyRenderer();
        event.register(TheAurorian.prefix("aurorian"), new TASpecialEffects());
        event.register(TheAurorian.prefix("north_kingdoms"), new TASpecialEffects());
        event.register(TheAurorian.prefix("south_dimension"), new TASpecialEffects());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerDimensionTransitionScreen(RegisterDimensionTransitionScreenEvent event) {
        event.registerIncomingEffect(TADimensions.AURORIAN_DIMENSION, TAWaitingScreen::new);
        event.registerIncomingEffect(TADimensions.NORTHERN_DIMENSION, TAWaitingScreen::new);
        event.registerIncomingEffect(TADimensions.SOUTHERN_DIMENSION, TAWaitingScreen::new);
    }

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(TADataMaps.ALCHEMY_TABLE_USABLE_EFFECTS);
        event.register(TADataMaps.ALCHEMY_TABLE_AMPLIFIER_EFFECTS);
        event.register(TADataMaps.ALCHEMY_TABLE_INGREDIENTS);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().stream().filter(type -> type.is(TAEntityTags.AURORIAN_BOSS)).forEach(type -> event.add(type, TAAttributes.MAX_BOSS_HEALTH));
    }

    @SubscribeEvent
    public static void registerDispenserBehavior(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerProjectileBehavior(TAItems.CERULEAN_ARROW.get());
            DispenserBlock.registerProjectileBehavior(TAItems.CRYSTAL_ARROW.get());
            DispenserBlock.registerBehavior(TAItems.MOON_WATER_BUCKET.get(), new DispenseBucket());
            DispenserBlock.registerBehavior(TAItems.MOON_FISH_BUCKET.get(), new DispenseBucket());
            DispenserBlock.registerBehavior(TAItems.AURORIAN_WINGED_FISH_BUCKET.get(), new DispenseBucket());
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(TAMenus.MOONLIGHT_FORGE_MENU.get(), MoonlightForgeScreen::new);
        event.register(TAMenus.ALCHEMY_TABLE_MENU.get(), AlchemyTableScreen::new);
        event.register(TAMenus.SCRAPPER_MENU.get(), ScrapperScreen::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerWoodType(FMLClientSetupEvent event) {
        Sheets.addWoodType(TAWoodType.SILENT);
        Sheets.addWoodType(TAWoodType.WEEPING_WILLOW);
        Sheets.addWoodType(TAWoodType.CURTAIN);
        Sheets.addWoodType(TAWoodType.CURSED_FROST);
    }

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(TABlocks.SILENT_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.SILENT_WOOD_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.SILENT_TREE_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.WEEPING_WILLOW_LEAVES.get(), 30, 60);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURTAIN_WOOD_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURTAIN_TREE_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_LOG.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_WOOD.get(), 5, 5);
        fireBlock.setFlammable(TABlocks.CURSED_FROST_TREE_LEAVES.get(), 30, 30);
        fireBlock.setFlammable(TABlocks.AURORIAN_GRASS.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.AURORIAN_GRASS_LIGHT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.LAVENDER_PLANT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.PETUNIA_PLANT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.CRISPED_MALLOW.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.FROST_SNOW_GRASS.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.ICE_CALENDULA.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.WINTER_ROOT.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.DREAMSCAPE_PISTIL.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.FROST_TEARS_FLOWER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.NEBULA_BLOSSOM_CLUSTER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.MOON_FROST_FLOWER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.VOID_CANDLE_FLOWER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.EQUINOX_FLOWER.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.WICK_GRASS.get(), 60, 100);
        fireBlock.setFlammable(TABlocks.TALL_WICK_GRASS.get(), 60, 100);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerItemProperties(FMLClientSetupEvent event) {
        ItemPropertyFunction pullFunction = (stack, level, entity, seed) ->
                entity == null || entity.getUseItem() != stack ? 0.0F :
                        (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
        ItemPropertyFunction usingFunction = (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        ItemPropertyFunction hpFunction = (stack, level, entity, seed) -> {
            Boolean highPrecision = stack.get(TADataComponents.HIGH_PRECISION.get());
            return highPrecision != null && highPrecision ? 1.0F : 0.0F;
        };

        ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), TheAurorian.prefix("pull"), pullFunction);
        ItemProperties.register(TAItems.SILENT_WOOD_BOW.get(), TheAurorian.prefix("pulling"), usingFunction);
        ItemProperties.register(TAItems.KEEPERS_BOW.get(), TheAurorian.prefix("pull"), pullFunction);
        ItemProperties.register(TAItems.KEEPERS_BOW.get(), TheAurorian.prefix("pulling"), usingFunction);
        ItemProperties.register(TAItems.AURORIAN_STEEL_SWORD.get(), TheAurorian.prefix("hp"), hpFunction);
        ItemProperties.register(TAItems.CRYSTALLINE_SWORD.get(), TheAurorian.prefix("shoot"), usingFunction);
        ItemProperties.register(TAItems.CRYSTALLINE_SWORD.get(), TheAurorian.prefix("hp"), hpFunction);
        ItemProperties.register(TAItems.CRYSTALLINE_SWORD.get(), TheAurorian.prefix("hp_shoot"), ((stack, level, entity, seed) -> {
            boolean flag = entity != null && entity.isUsingItem() && entity.getUseItem() == stack;
            Boolean highPrecision = stack.get(TADataComponents.HIGH_PRECISION.get());
            return flag && highPrecision != null && highPrecision ? 1.0F : 0.0F;
        }));

        for (Item item : TACommonUtils.getKnownItems()) {
            if (item instanceof ShieldItem) {
                ItemProperties.register(item, TheAurorian.prefix("blocking"), usingFunction);
            }
        }
    }

    @MethodsReturnNonnullByDefault
    private static class DispenseBucket extends DefaultDispenseItemBehavior {

        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem containerItem = (DispensibleContainerItem)stack.getItem();
            BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level level = source.level();
            if (containerItem.emptyContents(null, level, blockPos, null, stack)) {
                containerItem.checkExtraContent(null, level, stack, blockPos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }

    }

}