package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.enchantments.FreezeAspectEffect;
import cn.teampancake.theaurorian.common.enchantments.MoltenCoreEffect;
import cn.teampancake.theaurorian.common.enchantments.SpringOfLifeEffect;
import cn.teampancake.theaurorian.common.level.storage.predicate.MoltenCoreEnchantmentCondition;
import cn.teampancake.theaurorian.common.level.storage.predicate.NightWalkerEnchantmentCondition;
import cn.teampancake.theaurorian.common.level.storage.predicate.SavageEnchantmentCondition;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.Ignite;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;

public class TAEnchantments {

    public static final ResourceKey<Enchantment> IMPALE = createKey("impale");
    public static final ResourceKey<Enchantment> AURORA = createKey("aurora");
    public static final ResourceKey<Enchantment> SAVAGE = createKey("savage");
    public static final ResourceKey<Enchantment> OVERLOAD = createKey("overload");
    public static final ResourceKey<Enchantment> GUARDIAN = createKey("guardian");
    public static final ResourceKey<Enchantment> MOONLIGHT = createKey("moonlight");
    public static final ResourceKey<Enchantment> SOUL_SLASH = createKey("soul_slash");
    public static final ResourceKey<Enchantment> CLEAR_MIND = createKey("clear_mind");
    public static final ResourceKey<Enchantment> WIND_RUNNER = createKey("wind_runner");
    public static final ResourceKey<Enchantment> MOLTEN_CORE = createKey("molten_core");
    public static final ResourceKey<Enchantment> NIGHT_WALKER = createKey("night_walker");
    public static final ResourceKey<Enchantment> REFLECT_AURA = createKey("reflect_aura");
    public static final ResourceKey<Enchantment> AMNESIA_CURSE = createKey("amnesia_curse");
    public static final ResourceKey<Enchantment> FREEZE_ASPECT = createKey("freeze_aspect");
    public static final ResourceKey<Enchantment> SPRING_OF_LIFE = createKey("spring_of_life");
    public static final ResourceKey<Enchantment> EXPERIENCE_ORE = createKey("experience_ore");
    public static final ResourceKey<Enchantment> LEGENDARY_HERO = createKey("legendary_hero");
    public static final ResourceKey<Enchantment> VIRTUALIZATION = createKey("virtualization");
    public static final ResourceKey<Enchantment> SOURCE_OF_TERRA = createKey("source_of_terra");
    public static final ResourceKey<Enchantment> COBWEB_CROSSING = createKey("cobweb_crossing");
    public static final ResourceKey<Enchantment> ROUNDABOUT_THROW = createKey("roundabout_throw");
    public static final ResourceKey<Enchantment> LIGHTNING_DAMAGE = createKey("lightning_damage");
    public static final ResourceKey<Enchantment> SUNDER_ARMOR_SLASH = createKey("sunder_armor_slash");
    public static final ResourceKey<Enchantment> LIGHTNING_RESISTANCE = createKey("lightning_resistance");

    private static ResourceKey<Enchantment> createKey(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantmentLookup = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        register(context, IMPALE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                10, 4, Enchantment.dynamicCost(1, 10),
                Enchantment.constantCost(50), 4, EquipmentSlotGroup.HAND))
                .withEffect(EnchantmentEffectComponents.PROJECTILE_PIERCING,
                        new AddValue(LevelBasedValue.perLevel(1.0F))));
        register(context, AURORA, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                2, 1, Enchantment.constantCost(30),
                Enchantment.constantCost(60), 4, EquipmentSlotGroup.HEAD)));
        register(context, SAVAGE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.AXES),
                2, 3, Enchantment.dynamicCost(5, 8),
                Enchantment.dynamicCost(41, 8), 4, EquipmentSlotGroup.HAND))
                .withEffect(EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(4.0F)),
                        SavageEnchantmentCondition.get()));
        register(context, OVERLOAD, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                5, 4, Enchantment.dynamicCost(11, 10),
                Enchantment.dynamicCost(16, 10), 4, EquipmentSlotGroup.HAND))
                .withEffect(EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(1.0F))));
        register(context, GUARDIAN, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                5, 1, Enchantment.constantCost(20),
                Enchantment.constantCost(40), 4, EquipmentSlotGroup.CHEST)));
        register(context, MOONLIGHT, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                2, 1, Enchantment.constantCost(20),
                Enchantment.constantCost(50), 4, EquipmentSlotGroup.HEAD)));
        register(context, SOUL_SLASH, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(1, 11),
                Enchantment.dynamicCost(21, 11), 4, EquipmentSlotGroup.HAND)));
        register(context, CLEAR_MIND, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(5, 10),
                Enchantment.dynamicCost(15, 10), 4, EquipmentSlotGroup.HEAD)));
        register(context, WIND_RUNNER, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                2, 1, Enchantment.constantCost(30),
                Enchantment.constantCost(60), 4, EquipmentSlotGroup.FEET)));
        register(context, MOLTEN_CORE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                2, 3, Enchantment.dynamicCost(15, 20),
                Enchantment.dynamicCost(75, 20), 4, EquipmentSlotGroup.CHEST))
                .withEffect(EnchantmentEffectComponents.TICK, new MoltenCoreEffect(0))
                .withEffect(EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(3.0F)),
                        MoltenCoreEnchantmentCondition.get()));
        register(context, NIGHT_WALKER, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                5, 5, Enchantment.dynamicCost(0, 10),
                Enchantment.dynamicCost(15, 10), 4, EquipmentSlotGroup.HAND))
                .withEffect(EnchantmentEffectComponents.DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(1.0F)),
                        NightWalkerEnchantmentCondition.get()));
        register(context, REFLECT_AURA, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(10, 20),
                Enchantment.dynamicCost(60, 10), 4, EquipmentSlotGroup.ARMOR))
                .exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(VIRTUALIZATION))));
        register(context, AMNESIA_CURSE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                5, 5, Enchantment.constantCost(25),
                Enchantment.constantCost(50), 4, EquipmentSlotGroup.ARMOR)));
        register(context, FREEZE_ASPECT, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                2, 2, Enchantment.dynamicCost(10, 20),
                Enchantment.dynamicCost(60, 10), 4, EquipmentSlotGroup.HAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new FreezeAspectEffect(0),
                        DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))));
        register(context, SPRING_OF_LIFE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                5, 1, Enchantment.constantCost(30),
                Enchantment.constantCost(50), 4, EquipmentSlotGroup.CHEST))
                .withEffect(EnchantmentEffectComponents.TICK, new SpringOfLifeEffect(0)));
        register(context, EXPERIENCE_ORE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(15, 10),
                Enchantment.dynamicCost(65, 10), 4, EquipmentSlotGroup.HAND)));
        register(context, LEGENDARY_HERO, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                2, 1, Enchantment.constantCost(30),
                Enchantment.constantCost(60), 4, EquipmentSlotGroup.CHEST)));
        register(context, VIRTUALIZATION, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                5, 4, Enchantment.dynamicCost(11, 10),
                Enchantment.dynamicCost(16, 10), 4, EquipmentSlotGroup.ARMOR))
                .exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(REFLECT_AURA))));
        register(context, SOURCE_OF_TERRA, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.MINING_LOOT_ENCHANTABLE),
                2, 1, Enchantment.constantCost(40),
                Enchantment.constantCost(80), 4, EquipmentSlotGroup.HAND)));
        register(context, COBWEB_CROSSING, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                5, 1, Enchantment.constantCost(20),
                Enchantment.constantCost(50), 4, EquipmentSlotGroup.FEET)));
        register(context, ROUNDABOUT_THROW, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.AXES),
                2, 3, Enchantment.dynamicCost(11, 10),
                Enchantment.dynamicCost(16, 10), 4, EquipmentSlotGroup.HAND)));
        register(context, LIGHTNING_DAMAGE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(5, 15),
                Enchantment.dynamicCost(25, 15), 4, EquipmentSlotGroup.HAND))
                .exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(LIGHTNING_RESISTANCE))));
        register(context, SUNDER_ARMOR_SLASH, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.AXES),
                2, 5, Enchantment.dynamicCost(5, 8),
                Enchantment.dynamicCost(25, 8), 4, EquipmentSlotGroup.HAND)));
        register(context, LIGHTNING_RESISTANCE, Enchantment.enchantment(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                2, 4, Enchantment.dynamicCost(10, 15),
                Enchantment.dynamicCost(40, 15), 4, EquipmentSlotGroup.ARMOR))
                .exclusiveWith(HolderSet.direct(enchantmentLookup.getOrThrow(LIGHTNING_DAMAGE))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    public static Holder<Enchantment> get(Level level, ResourceKey<Enchantment> key) {
        RegistryAccess registryAccess = level.registryAccess();
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

}