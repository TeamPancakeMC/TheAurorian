package cn.teampancake.theaurorian.common.data.datagen.loot;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class TAEntityLoot extends VanillaEntityLoot {

    public TAEntityLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    public void generate() {
        this.add(TAEntityTypes.AURORIAN_VILLAGER.get(), LootTable.lootTable());
        //Animal
        this.add(TAEntityTypes.BREAD_BEAST.get(), LootTable.lootTable());
        this.add(TAEntityTypes.ICEFIELD_DEER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.BLUE_TAIL_WOLF.get(), LootTable.lootTable());
        this.add(TAEntityTypes.MOON_FISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.MOON_FISH.get()))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
        this.add(TAEntityTypes.AURORIAN_WINGED_FISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.AURORIAN_WINGED_FISH.get()))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
        this.add(TAEntityTypes.AURORIAN_RABBIT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.LUCKY_RABBIT_EAR.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.AURORIAN_RABBIT.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_SHEEP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.AURORIAN_MUTTON.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TABlocks.MYSTERIUM_WOOL.get()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
        this.add(TAEntityTypes.AURORIAN_PIG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.AURORIAN_PORK.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_COW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.LEATHER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.BROKEN_OX_HORN.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))
                                .when(LootItemRandomChanceCondition.randomChance(0.1F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.AURORIAN_BEEF.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_PIXIE.get(), LootTable.lootTable());
        //Monster
        this.add(TAEntityTypes.AURORIAN_SLIME.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.AURORIAN_SLIMEBALL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))).when(this.killedByFrog().invert()))
                        .add(LootItem.lootTableItem(TAItems.AURORIAN_SLIMEBALL.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))).when(this.killedByFrog()))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.exactly(1)))))));
        this.addMonsterLoot(TAEntityTypes.DISTURBED_HOLLOW.get(), TAItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(TAEntityTypes.UNDEAD_KNIGHT.get(), TAItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(TAEntityTypes.MOON_ACOLYTE.get(), TAItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(TAEntityTypes.CRYSTALLINE_SPRITE.get(), TAItems.CRYSTAL.get(), 1.0F);
        this.addMonsterLoot(TAEntityTypes.SPIRIT.get(), TAItems.SPECTRAL_SILK.get(), 2.0F);
        this.addMonsterLoot(TAEntityTypes.SPIDERLING.get(), Items.SPIDER_EYE, 1.0F);
        this.add(TAEntityTypes.SPIDERLING_CRYSTAL_SHELL.get(), LootTable.lootTable());
        this.add(TAEntityTypes.SPIDERLING_WALL_CLIMBER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.GIANT_CRYSTAL_SPIDER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.RUNE_SPIDER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.CAVE_DWELLER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.ROCK_HAMMER.get(), LootTable.lootTable());
        this.add(TAEntityTypes.TONG_SCORPION.get(), LootTable.lootTable());
        this.add(TAEntityTypes.SNOW_TUNDRA_GIANT_CRAB.get(), LootTable.lootTable());
        this.add(TAEntityTypes.FLOWER_LEECH.get(), LootTable.lootTable());
        this.add(TAEntityTypes.FORGOTTEN_MAGIC_BOOK.get(), LootTable.lootTable());
        this.add(TAEntityTypes.HYPHA_WALKING_MUSHROOM.get(), LootTable.lootTable());
        //Boss
        this.add(TAEntityTypes.MOONLIGHT_KNIGHT.get(), LootTable.lootTable());
        this.add(TAEntityTypes.RUNESTONE_KEEPER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.RUNE_STONE_LOOT_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.DUNGEON_KEEPER_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_KEEPER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.RUNE_KNOWLEDGE_FRAGMENT.get())
                                .when(LootItemRandomChanceCondition.randomChance(0.1F)))));
        this.add(TAEntityTypes.SPIDER_MOTHER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_SPIDER_MOTHER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.DARK_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.UMBRA_INGOT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 15.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.STRING)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(20.0F, 25.0F))))));
        this.add(TAEntityTypes.MOON_QUEEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_MOON_QUEEN.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
    }

    private void addMonsterLoot(EntityType<?> entityType, ItemLike item, float max) {
        this.add(entityType, LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, max)))
                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return TAEntityTypes.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::value);
    }

}