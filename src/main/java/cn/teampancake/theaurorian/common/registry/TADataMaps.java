package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.datamaps.AlchemyTableMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class TADataMaps {

    public static final DataMapType<MobEffect, AlchemyTableMaterial> ALCHEMY_TABLE_USABLE_EFFECTS = DataMapType.builder(
                    TheAurorian.prefix("alchemy_table/usable_effects"), Registries.MOB_EFFECT, AlchemyTableMaterial.CODEC)
            .synced(AlchemyTableMaterial.FORMULA_CODEC, false).build();

    public static final DataMapType<MobEffect, AlchemyTableMaterial> ALCHEMY_TABLE_AMPLIFIER_EFFECTS = DataMapType.builder(
                    TheAurorian.prefix("alchemy_table/amplifier_effects"), Registries.MOB_EFFECT, AlchemyTableMaterial.CODEC)
            .synced(AlchemyTableMaterial.FORMULA_CODEC, false).build();

    public static final DataMapType<Item, AlchemyTableMaterial> ALCHEMY_TABLE_INGREDIENTS = DataMapType.builder(
                    TheAurorian.prefix("alchemy_table/ingredients"), Registries.ITEM, AlchemyTableMaterial.CODEC)
            .synced(AlchemyTableMaterial.FORMULA_CODEC, false).build();

}