package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.TheAurorian;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class CrimsonPactPendant extends Item implements ICurioItem {

    public static final ItemAttributeModifiers ATTRIBUTES = ItemAttributeModifiers.builder().add(
            Attributes.MAX_HEALTH, new AttributeModifier(TheAurorian.prefix("base_max_heath"), -0.5D,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.OFFHAND).build();

    public CrimsonPactPendant(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return !checkFirstCurio(slotContext.entity(), this, "necklace", null);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        ATTRIBUTES.modifiers().forEach(entry -> multimap.put(entry.attribute(), entry.modifier()));
        return multimap;
    }

    public static boolean checkFirstCurio(LivingEntity entity, Item curio, String identifier, @Nullable Consumer<ItemStack> consumer) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Optional<ICuriosItemHandler> maybeCuriosInventory = CuriosApi.getCuriosInventory(entity);
        maybeCuriosInventory.flatMap(o -> o.getStacksHandler(identifier)).ifPresent(stacksHandler -> {
            IDynamicStackHandler stacks = stacksHandler.getStacks();
            for (int i = 0; i < stacks.getSlots(); i++) {
                ItemStack stackInSlot = stacks.getStackInSlot(i);
                if (stackInSlot.is(curio)) {
                    if (consumer != null) consumer.accept(stackInSlot);
                    flag.set(true); break;
                }
            }
        });

        return flag.get();
    }

}