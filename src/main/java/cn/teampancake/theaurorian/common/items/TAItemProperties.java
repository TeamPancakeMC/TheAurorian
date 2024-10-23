package cn.teampancake.theaurorian.common.items;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAItemProperties extends Item.Properties {

    @Nullable
    private DataComponentMap.Builder components;
    public List<TagKey<Item>> itemTagList = new ArrayList<>();
    public boolean hasTooltips = false;
    public boolean isDeveloperItem = false;
    public boolean isSimpleModelItem = false;

    public static TAItemProperties get() {
        return new TAItemProperties();
    }

    @Override
    public TAItemProperties food(FoodProperties food) {
        return this.component(DataComponents.FOOD, food);
    }

    @Override
    public TAItemProperties stacksTo(int maxStackSize) {
        return this.component(DataComponents.MAX_STACK_SIZE, maxStackSize);
    }

    @Override
    public TAItemProperties durability(int maxDamage) {
        this.component(DataComponents.MAX_DAMAGE, maxDamage);
        this.component(DataComponents.MAX_STACK_SIZE, 1);
        this.component(DataComponents.DAMAGE, 0);
        return this;
    }

    @Override
    public TAItemProperties rarity(Rarity rarity) {
        return this.component(DataComponents.RARITY, rarity);
    }

    @Override
    public TAItemProperties fireResistant() {
        return this.component(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
    }

    @Override
    public <T> TAItemProperties component(DataComponentType<T> component, T value) {
        CommonHooks.validateComponent(value);
        if (this.components == null) {
            this.components = DataComponentMap.builder().addAll(DataComponents.COMMON_ITEM_COMPONENTS);
        }

        this.components.set(component, value);
        return this;
    }

    @Override
    public TAItemProperties attributes(ItemAttributeModifiers attributes) {
        return this.component(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
    }

    @SafeVarargs
    public final TAItemProperties addItemTag(TagKey<Item>... values) {
        this.itemTagList.addAll(Arrays.asList(values));
        return this;
    }

    public TAItemProperties hasTooltips() {
        this.hasTooltips = true;
        return this;
    }

    public TAItemProperties isDeveloperItem() {
        this.isDeveloperItem = true;
        return this;
    }

    public TAItemProperties isSimpleModelItem() {
        this.isSimpleModelItem = true;
        return this;
    }

}