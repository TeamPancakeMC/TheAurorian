package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum ModToolTiers implements Tier {

    SILENT_WOOD(AurorianConfig.CONFIG_SILENT_WOOD_HARVEST_LEVEL.get(),
            59 * AurorianConfig.CONFIG_SILENT_WOOD_MULTIPLIER_DURABILITY.get(),
            3.0F * AurorianConfig.CONFIG_SILENT_WOOD_MULTIPLIER_SPEED.get(),
            0.0F, 20, () -> Ingredient.of(ModBlocks.SILENT_TREE_PLANKS.get())),

    AURORIAN_STONE(AurorianConfig.CONFIG_AURORIAN_STONE_HARVEST_LEVEL.get(),
            131 * AurorianConfig.CONFIG_AURORIAN_STONE_MULTIPLIER_DURABILITY.get(),
            4.5F * AurorianConfig.CONFIG_AURORIAN_STONE_MULTIPLIER_SPEED.get(),
            1.5F * AurorianConfig.CONFIG_AURORIAN_STONE_MULTIPLIER_DAMAGE.get(),
            14, () -> Ingredient.of(ModBlocks.AURORIAN_STONE.get())),

    MOONSTONE(AurorianConfig.CONFIG_MOONSTONE_HARVEST_LEVEL.get(),
            250 * AurorianConfig.CONFIG_MOONSTONE_MULTIPLIER_DURABILITY.get(),
            7.0F * AurorianConfig.CONFIG_MOONSTONE_MULTIPLIER_SPEED.get(),
            2.5F * AurorianConfig.CONFIG_MOONSTONE_MULTIPLIER_DAMAGE.get(),
            14, () -> Ingredient.of(ModBlocks.MOONSTONE_BLOCK.get())),

    AURORIANITE(AurorianConfig.CONFIG_SPECIAL_HARVEST_LEVEL.get(),
            1000 * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DURABILITY.get(),
            8.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_SPEED.get(),
            3.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DAMAGE.get(),
            20, () -> Ingredient.of(ModItems.AURORIANITE_INGOT.get())),

    UMBRA(AurorianConfig.CONFIG_SPECIAL_HARVEST_LEVEL.get(),
            1000 * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DURABILITY.get(),
            8.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_SPEED.get(),
            3.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DAMAGE.get(),
            20, () -> Ingredient.of(ModItems.UMBRA_INGOT.get())),

    CRYSTALLINE(AurorianConfig.CONFIG_SPECIAL_HARVEST_LEVEL.get(),
            1000 * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DURABILITY.get(),
            8.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_SPEED.get(),
            3.0F * AurorianConfig.CONFIG_SPECIAL_MULTIPLIER_DAMAGE.get(),
            20, () -> Ingredient.of(ModItems.CRYSTALLINE_INGOT.get())),

    AURORIAN_STEEL(AurorianConfig.CONFIG_AURORIAN_STEEL_HARVEST_LEVEL.get(),
            1500 * AurorianConfig.CONFIG_AURORIAN_STEEL_MULTIPLIER_DURABILITY.get(),
            8.5F * AurorianConfig.CONFIG_AURORIAN_STEEL_MULTIPLIER_SPEED.get(),
            3.5F * AurorianConfig.CONFIG_AURORIAN_STEEL_MULTIPLIER_DAMAGE.get(),
            10, () -> Ingredient.of(ModItems.AURORIAN_STEEL.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModToolTiers(int level, int uses, float speed, float damage,
                 int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
