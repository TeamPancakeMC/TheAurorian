package cn.teampancake.theaurorian.data.loot;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModEntityTypes;
import cn.teampancake.theaurorian.registry.ModItems;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class ModEntityLoot extends VanillaEntityLoot {

    @Override
    public void generate() {
        //Animal
        this.add(ModEntityTypes.AURORIAN_RABBIT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RABBIT_HIDE)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RABBIT)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.AURORIAN_SHEEP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MUTTON)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.AURORIAN_PIG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.AURORIAN_PORK.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        //Monster
        this.add(ModEntityTypes.AURORIAN_SLIME.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.AURORIAN_SLIMEBALL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))).when(this.killedByFrog().invert()))
                        .add(LootItem.lootTableItem(ModItems.AURORIAN_SLIMEBALL.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))).when(this.killedByFrog()))
                        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.entity().subPredicate(SlimePredicate.sized(MinMaxBounds.Ints.exactly(1)))))));
        this.addMonsterLoot(ModEntityTypes.DISTURBED_HOLLOW.get(), ModItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(ModEntityTypes.UNDEAD_KNIGHT.get(), ModItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(ModEntityTypes.MOON_ACOLYTE.get(), ModItems.SOULLESS_FLESH.get(), 2.0F);
        this.addMonsterLoot(ModEntityTypes.CRYSTALLINE_SPRITE.get(), ModItems.CRYSTAL.get(), 1.0F);
        this.addMonsterLoot(ModEntityTypes.SPIRIT.get(), ModItems.SPECTRAL_SILK.get(), 2.0F);
        this.addMonsterLoot(ModEntityTypes.SPIDERLING.get(), Items.SPIDER_EYE, 1.0F);
        //Boss
        this.add(ModEntityTypes.RUNESTONE_KEEPER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.RUNE_STONE_LOOT_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.DUNGEON_KEEPER_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.TROPHY_KEEPER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
        this.add(ModEntityTypes.SPIDER_MOTHER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.DARK_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.TROPHY_SPIDER_MOTHER.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))));
        this.add(ModEntityTypes.MOON_QUEEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.TROPHY_MOON_QUEEN.get())
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