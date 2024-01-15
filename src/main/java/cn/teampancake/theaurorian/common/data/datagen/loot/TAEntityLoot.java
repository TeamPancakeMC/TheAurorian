package cn.teampancake.theaurorian.common.data.datagen.loot;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class TAEntityLoot extends VanillaEntityLoot {

    @Override
    public void generate() {
        //Animal
        this.add(TAEntityTypes.MOON_FISH.get(), LootTable.lootTable());
        this.add(TAEntityTypes.AURORIAN_WINGED_FISH.get(), LootTable.lootTable());
        this.add(TAEntityTypes.AURORIAN_RABBIT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RABBIT_HIDE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RABBIT)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_SHEEP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MUTTON)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_PIG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TAItems.AURORIAN_PORK.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        this.add(TAEntityTypes.AURORIAN_PIXIE.get(), LootTable.lootTable());
        //Monster
        this.add(TAEntityTypes.AURORIAN_SLIME.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.AURORIAN_SLIMEBALL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))).when(this.killedByFrog().invert()))
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
        //Boss
        this.add(TAEntityTypes.RUNESTONE_KEEPER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.RUNE_STONE_LOOT_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.DUNGEON_KEEPER_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_KEEPER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
        this.add(TAEntityTypes.SPIDER_MOTHER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.DARK_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_SPIDER_MOTHER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
        this.add(TAEntityTypes.MOON_QUEEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(TAItems.TROPHY_MOON_QUEEN.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
    }

    private void addMonsterLoot(EntityType<?> entityType, ItemLike item, float max) {
        this.add(entityType, LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, max)))
                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entities -> Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entities)).getNamespace().equals(AurorianMod.MOD_ID));
    }

}