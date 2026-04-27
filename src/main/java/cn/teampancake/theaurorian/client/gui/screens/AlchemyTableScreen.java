package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.datamaps.AlchemyTableMaterial;
import cn.teampancake.theaurorian.common.network.AddItemToInventoryC2SPacket;
import cn.teampancake.theaurorian.common.network.SetContainerSlotItemC2SPacket;
import cn.teampancake.theaurorian.common.network.UpdateAlchemyTableIntDataC2SPacket;
import cn.teampancake.theaurorian.common.network.UpdateMenuCarriedC2SPacket;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TADataMaps;
import cn.teampancake.theaurorian.common.utils.TAPotionUtils;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ItemDecoratorHandler;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = getGuiTexture("alchemy_table_new");
    private static final ResourceLocation SLOT_HIGHLIGHTED = TheAurorian.prefix("alchemy_table/slot_highlighted");
    private static final ResourceLocation SCROLLBAR_VERTICAL = TheAurorian.prefix("alchemy_table/scrollbar_vertical");
    private static final ResourceLocation POTION_LIQUID = TheAurorian.prefix("liquid/water");
    private float scrollOffs;
    private boolean scrolling;

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 261;
        this.imageHeight = 174;
    }

    public static ResourceLocation getGuiTexture(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheAurorian.MOD_ID, String.format("textures/gui/alchemy_table/%s.png", path));
    }

    @Nullable
    private AlchemyTableMaterial getMaterial(ItemStack carried) {
        return carried.getItemHolder().getData(TADataMaps.ALCHEMY_TABLE_INGREDIENTS);
    }

    private void setContainerData(BlockPos blockPos, ItemStack carried, String data, int value) {
        PacketDistributor.sendToServer(new UpdateAlchemyTableIntDataC2SPacket(blockPos, carried, data, value));
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        ContainerData containerData = this.menu.getContainerData();
        int i = this.leftPos;
        int j = this.topPos;
        float k1 = containerData.get(0);
        float k2 = containerData.get(1);
        PoseStack poseStack = guiGraphics.pose();
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight, 512, 256);
        poseStack.pushPose();
        int v = Mth.floor(33 * (k1 / k2));
        int y = j - v + 72;
        guiGraphics.blit(CONTAINER_LOCATION, i + 147, y, this.imageWidth, 33 - v, 30, v, 512, 256);
        poseStack.popPose();
        poseStack.pushPose();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        int liquidLevel = this.menu.getLiquidLevel();
        int liquidData = this.menu.getLiquidData();
        int potionColor = TAPotionUtils.getPotionColor(liquidData);
        if (liquidData == 0) potionColor = 0x3F76E4;
        float r = (potionColor >> 16 & 0xFF) / 255.0F;
        float g = (potionColor >> 8 & 0xFF) / 255.0F;
        float b = (potionColor & 0xFF) / 255.0F;
        guiGraphics.setColor(r, g, b, 1.0F);
        guiGraphics.blitSprite(POTION_LIQUID, i + 218, j + 33 * (4 - liquidLevel), 12, 33 * liquidLevel);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        if (this.isScrollBarActive()) {
            poseStack.pushPose();
            int k = j + 12;
            int l = k + 166;
            int m = k + (int)((float)(l - k - 17) * this.scrollOffs);
            guiGraphics.blitSprite(SCROLLBAR_VERTICAL, i + 3, m, 8, 24);
            poseStack.popPose();
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        int i = this.leftPos + 218;
        int j = this.topPos + 33;
        if (x > i && x < i + 12 && y > j && y < j + 99) {
            ItemStack carried = this.menu.getCarried();
            int liquidLevel = this.menu.getLiquidLevel();
            int liquidData = this.menu.getLiquidData();
            List<MobEffectInstance> potionEffects = TAPotionUtils.getPotionEffects(liquidData);
            if (!carried.isEmpty()) {
                AlchemyTableMaterial material = this.getMaterial(carried);
                String prefix = "tooltips.block.theaurorian.alchemy_table.gui.";
                if (material == null || carried.is(Items.WATER_BUCKET) && liquidLevel == 3) {
                    MutableComponent component = Component.translatable(prefix + "material_invalid");
                    guiGraphics.renderTooltip(this.font, component.withStyle(ChatFormatting.RED), x, y);
                    return;
                }

                if (TAPotionUtils.applyIngredient(liquidData, material.formula()) == liquidData) {
                    MutableComponent component = Component.translatable(prefix + "material_pass");
                    guiGraphics.renderTooltip(this.font, component.withStyle(ChatFormatting.YELLOW), x, y);
                    return;
                }
            }

            if (potionEffects.isEmpty()) {
                MutableComponent component = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
                guiGraphics.renderTooltip(this.font, component, x, y);
                return;
            }

            List<Pair<Holder<Attribute>, AttributeModifier>> list = Lists.newArrayList();
            List<Component> components = new ArrayList<>();
            for (MobEffectInstance instance : potionEffects) {
                MutableComponent mutableComponent = Component.translatable(instance.getDescriptionId());
                Holder<MobEffect> effectHolder = instance.getEffect();
                effectHolder.value().createModifiers(instance.getAmplifier(),
                        (holder, modifier) -> list.add(new Pair<>(holder, modifier)));
                if (instance.getAmplifier() > 0) {
                    MutableComponent component = Component.translatable("potion.potency." + instance.getAmplifier());
                    mutableComponent = Component.translatable("potion.withAmplifier", mutableComponent, component);
                }

                if (!instance.endsWithin(20)) {
                    Component component = MobEffectUtil.formatDuration(instance, 1.0F, 20.0F);
                    mutableComponent = Component.translatable("potion.withDuration", mutableComponent, component);
                }

                components.add(mutableComponent.withStyle(effectHolder.value().getCategory().getTooltipFormatting()));
            }

            if (!list.isEmpty() && this.minecraft != null) {
                components.add(CommonComponents.EMPTY);
                components.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
                TooltipFlag tooltipFlag = this.minecraft.options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL;
                for (Pair<Holder<Attribute>, AttributeModifier> pair : list) {
                    components.add(pair.getFirst().value().toComponent(pair.getSecond(), tooltipFlag));
                }
            }

            guiGraphics.renderComponentTooltip(this.font, components, x, y);
        }
    }

    @Override
    public void renderFloatingItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y, String text) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 232.0F);
        guiGraphics.renderItem(stack, x, y);
        Font font = IClientItemExtensions.of(stack).getFont(stack, IClientItemExtensions.FontContext.ITEM_COUNT);
        this.renderItemDecorations(guiGraphics, font == null ? this.font : font, stack, x, y - (this.draggingItem.isEmpty() ? 0 : 8), text);
        guiGraphics.pose().popPose();
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        guiGraphics.blitSprite(SLOT_HIGHLIGHTED, slot.x - 1, slot.y - 1, 18, 18);
        super.renderSlot(guiGraphics, slot);
    }

    @Override
    protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemStack, Slot slot, @Nullable String countString) {
        this.renderItemDecorations(guiGraphics, this.font, itemStack, slot.x, slot.y, countString);
        int seed = slot.x + slot.y * this.imageWidth;
        if (slot.isFake()) {
            guiGraphics.renderFakeItem(itemStack, slot.x, slot.y, seed);
        } else {
            guiGraphics.renderItem(itemStack, slot.x, slot.y, seed);
        }
    }

    private void renderItemDecorations(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y, @Nullable String text) {
        if (this.minecraft != null && !stack.isEmpty()) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            Boolean ingredientApplier = stack.get(TADataComponents.INGREDIENT_APPLIER);
            if (ingredientApplier != null && ingredientApplier) {
                renderSlotHighlight(guiGraphics, x, y, 0, FastColor.ARGB32.color(192, 0x7FCC19));
            }

            AlchemyTableMaterial material = this.getMaterial(stack);
            if (this.menu.getLiquidLevel() > 0) {
                int liquidData = this.menu.getLiquidData();
                boolean flag1 = material != null && liquidData != TAPotionUtils.applyIngredient(liquidData, material.formula());
                boolean flag2 = stack.is(Items.NETHER_WART) && TAPotionUtils.applyNetherWart(liquidData) != liquidData;
                if (flag1 || flag2) {
                    renderSlotHighlight(guiGraphics, x, y, 0, FastColor.ARGB32.color(192, 16755200));
                }
            }

            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                poseStack.translate(0.0F, 0.0F, 200.0F);
                guiGraphics.drawString(font, s, x + 19 - 2 - font.width(s), y + 6 + 3, 16777215, true);
            }

            if (stack.isBarVisible()) {
                int l = stack.getBarWidth();
                int i = stack.getBarColor();
                int j = x + 2;
                int k = y + 13;
                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | 0xFF000000);
            }

            LocalPlayer player = this.minecraft.player;
            float partialTick = this.minecraft.getTimer().getGameTimeDeltaPartialTick(true);
            float f = player == null ? 0.0F : player.getCooldowns().getCooldownPercent(stack.getItem(), partialTick);
            if (f > 0.0F) {
                int i1 = y + Mth.floor(16.0F * (1.0F - f));
                int j1 = i1 + Mth.ceil(16.0F * f);
                guiGraphics.fill(RenderType.guiOverlay(), x, i1, x + 16, j1, Integer.MAX_VALUE);
            }

            poseStack.popPose();
            //noinspection UnstableApiUsage
            ItemDecoratorHandler.of(stack).render(guiGraphics, font, stack, x, y);
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 18;
            int j = i + 112;
            this.scrollOffs = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.scrollTo(this.scrollOffs);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (!this.isScrollBarActive()) {
            return false;
        } else {
            this.scrollOffs = this.subtractInputFromScroll(this.scrollOffs, scrollY);
            this.scrollTo(this.scrollOffs);
            return true;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = this.leftPos + 218;
        int j = this.topPos + 33;
        if (button == 0 && this.insideScrollbar(mouseX, mouseY)) {
            this.scrolling = this.isScrollBarActive();
            return true;
        }

        if (mouseX > i && mouseX < i + 12 && mouseY > j && mouseY < j + 99) {
            ItemStack carried = this.menu.getCarried();
            int liquidLevel = this.menu.getLiquidLevel();
            int liquidData = this.menu.getLiquidData();
            BlockPos blockPos = this.menu.getBlockPos();
            if (carried.is(Items.WATER_BUCKET)) {
                boolean flag = false;
                if (liquidLevel == 0) {
                    this.setContainerData(blockPos, carried, "liquidLevel", 3);
                    this.setContainerData(blockPos, carried, "liquidData", 0);
                    flag = true;
                }

                if (liquidLevel < 3) {
                    int applied = TAPotionUtils.applyIngredient(liquidData, "-1-3-5-7-9-11-13");
                    this.setContainerData(blockPos, carried, "liquidLevel", 3);
                    this.setContainerData(blockPos, carried, "liquidData", applied);
                    flag = true;
                }

                if (flag) {
                    PacketDistributor.sendToServer(new UpdateMenuCarriedC2SPacket(this.menu.containerId, new ItemStack(Items.BUCKET)));
                    return true;
                }
            } else if (liquidLevel > 0) {
                AlchemyTableMaterial material = this.getMaterial(carried);
                DataComponentType<PotionContents> componentType = DataComponents.POTION_CONTENTS;
                PotionContents potion = carried.get(componentType);
                boolean flag1 = false, flag2 = false;
                if (carried.is(Items.NETHER_WART)) {
                    int k = TAPotionUtils.applyNetherWart(liquidData);
                    if (k != liquidData) {
                        this.setContainerData(blockPos, carried, "liquidData", k);
                        flag1 = true;
                    }
                } else if (material != null) {
                    int l = TAPotionUtils.applyIngredient(liquidData, material.formula());
                    if (l != liquidData) {
                        this.setContainerData(blockPos, carried, "liquidData", l);
                        flag1 = true;
                    }
                }

                if (flag1) {
                    carried.shrink(1);
                    return true;
                }

                if (liquidData != 0) {
                    int potionColor = TAPotionUtils.getPotionColor(liquidData);
                    PotionContents potionContents = new PotionContents(
                            Optional.of(Potions.WATER), Optional.of(potionColor),
                            TAPotionUtils.getPotionEffects(liquidData));
                    if (carried.is(Items.GLASS_BOTTLE)) {
                        ItemStack potionStack = new ItemStack(Items.POTION);
                        potionStack.set(componentType, potionContents);
                        if (carried.getCount() == 1) {
                            PacketDistributor.sendToServer(new UpdateMenuCarriedC2SPacket(this.menu.containerId, potionStack));
                        } else {
                            carried.shrink(1);
                            PacketDistributor.sendToServer(new AddItemToInventoryC2SPacket(potionStack));
                        }

                        flag2 = true;
                    } else if (potion != null && potion.equals(PotionContents.EMPTY)) {
                        carried.set(DataComponents.POTION_CONTENTS, potionContents);
                        PacketDistributor.sendToServer(new UpdateMenuCarriedC2SPacket(this.menu.containerId, carried));
                        flag2 = true;
                    }

                    if (flag2) {
                        this.setContainerData(blockPos, carried, "liquidLevel", liquidLevel - 1);
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void scrollTo(float pos) {
        int index = this.getRowIndexForScroll(pos);
        for (int k = 5; k < 14; k++) {
            int l = k + index * 9;
            boolean flag = l >= 0 && l < this.getMaterialsCount();
            ItemStack itemStack = flag ? this.menu.getMaterials().get(l) : ItemStack.EMPTY;
            PacketDistributor.sendToServer(new SetContainerSlotItemC2SPacket(this.menu.getBlockPos(), k + 9, itemStack));
        }
    }

    private boolean isScrollBarActive() {
        return this.getMaterialsCount() > 9;
    }

    private boolean insideScrollbar(double mouseX, double mouseY) {
        int k = this.leftPos + 3;
        int l = this.topPos + 10;
        int i1 = k + 11;
        int j1 = l + 164;
        return mouseX >= (double)k && mouseY >= (double)l && mouseX < (double)i1 && mouseY < (double)j1;
    }

    private int getRowIndexForScroll(float scrollOffs) {
        return Math.max((int)((double)(scrollOffs * (float)this.getMaterialsCount()) + 0.5), 0);
    }

    private float subtractInputFromScroll(float scrollOffs, double input) {
        return Mth.clamp(scrollOffs - (float)(input / (double)this.getMaterialsCount()), 0.0F, 1.0F);
    }

    private int getMaterialsCount() {
        List<ItemStack> materials = this.menu.getMaterials();
        List<ItemStack> temp = new ArrayList<>(materials);
        temp.removeIf(ItemStack::isEmpty);
        return temp.size();
    }

}