package cn.teampancake.theaurorian.common.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AlchemyTableMaterial(String formula) {

    public static final Codec<AlchemyTableMaterial> FORMULA_CODEC = Codec.STRING
            .xmap(AlchemyTableMaterial::new, AlchemyTableMaterial::formula);
    public static final Codec<AlchemyTableMaterial> CODEC = Codec.withAlternative(
            RecordCodecBuilder.create(in -> in.group(Codec.STRING.fieldOf("formula")
                            .forGetter(AlchemyTableMaterial::formula))
                    .apply(in, AlchemyTableMaterial::new)), FORMULA_CODEC);

}