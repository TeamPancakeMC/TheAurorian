package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TAArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, TheAurorian.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CERULEAN = register(
            "cerulean", 3, 5, 6, 3, 20, 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(TAItems.CERULEAN_INGOT.get()), 1);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> AURORIAN_STEEL = register(
            "aurorian_steel", 4, 7, 8, 4, 33, 15,
            SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(TAItems.AURORIAN_STEEL.get()), 2);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> UMBRA = register(
            "umbra", 3, 5, 6, 3, 65, 15,
            SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(TAItems.UMBRA_INGOT.get()), 1);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KNIGHT = register(
            "knight", 2, 2, 3, 1, 30, 5,
            SoundEvents.ARMOR_EQUIP_IRON, Ingredient::of, 1);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CRYSTAL_RUNE = register(
            "crystal_rune", 2, 2, 3, 1, 30, 5,
            SoundEvents.ARMOR_EQUIP_IRON, Ingredient::of, 1);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> AURORIAN_SLIME = register(
            "aurorian_slime", 1, 2, 3, 1, 120, 20,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(TAItems.AURORIAN_SLIMEBALL.get()), 1);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SPECTRAL = register(
            "spectral", 4, 6, 6, 4, 20, 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(TAItems.SPECTRAL_SILK.get()), 1);

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name, int boots, int leggings, int chestplate, int helmet, int body,
            int enchantmentValue, Holder<SoundEvent> equipSound,
            Supplier<Ingredient> repairIngredient, float toughness) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);
        Map<ArmorItem.Type, Integer> defense = Util.make(enumMap, map -> {
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.HELMET, helmet);
            map.put(ArmorItem.Type.BODY, body);
        });

        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(TheAurorian.prefix(name)));
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense, enchantmentValue, equipSound, repairIngredient, layers, toughness, (0.0F)));
    }

}