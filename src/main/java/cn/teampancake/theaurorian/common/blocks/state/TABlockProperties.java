package cn.teampancake.theaurorian.common.blocks.state;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;

/** @noinspection deprecation*/
public class TABlockProperties extends BlockBehaviour.Properties {

    public List<TagKey<Block>> blockTagList = new ArrayList<>();
    public TALootType lootType = TALootType.UNSET;
    public boolean hasTooltips = false;
    public boolean isBuildingBlock = false;
    public boolean isSimpleModelBlock = false;
    public boolean isRiversidePlant = false;
    public boolean useSimpleBlockItem = false;

    public static TABlockProperties get() {
        return new TABlockProperties();
    }

    public static TABlockProperties ofFullCopy(BlockBehaviour blockBehaviour) {
        TABlockProperties properties1 = ofLegacyCopy(blockBehaviour);
        BlockBehaviour.Properties properties2 = blockBehaviour.properties();
        properties1.jumpFactor = properties2.jumpFactor;
        properties1.isRedstoneConductor = properties2.isRedstoneConductor;
        properties1.isValidSpawn = properties2.isValidSpawn;
        properties1.hasPostProcess = properties2.hasPostProcess;
        properties1.isSuffocating = properties2.isSuffocating;
        properties1.isViewBlocking = properties2.isViewBlocking;
        properties1.drops = properties2.drops;
        return properties1;
    }

    public static TABlockProperties ofLegacyCopy(BlockBehaviour blockBehaviour) {
        TABlockProperties properties1 = new TABlockProperties();
        BlockBehaviour.Properties properties2 = blockBehaviour.properties();
        properties1.destroyTime = properties2.destroyTime;
        properties1.explosionResistance = properties2.explosionResistance;
        properties1.hasCollision = properties2.hasCollision;
        properties1.isRandomlyTicking = properties2.isRandomlyTicking;
        properties1.lightEmission = properties2.lightEmission;
        properties1.mapColor = properties2.mapColor;
        properties1.soundType = properties2.soundType;
        properties1.friction = properties2.friction;
        properties1.speedFactor = properties2.speedFactor;
        properties1.dynamicShape = properties2.dynamicShape;
        properties1.canOcclude = properties2.canOcclude;
        properties1.isAir = properties2.isAir;
        properties1.ignitedByLava = properties2.ignitedByLava;
        properties1.liquid = properties2.liquid;
        properties1.forceSolidOff = properties2.forceSolidOff;
        properties1.forceSolidOn = properties2.forceSolidOn;
        properties1.pushReaction = properties2.pushReaction;
        properties1.requiresCorrectToolForDrops = properties2.requiresCorrectToolForDrops;
        properties1.offsetFunction = properties2.offsetFunction;
        properties1.spawnTerrainParticles = properties2.spawnTerrainParticles;
        properties1.requiredFeatures = properties2.requiredFeatures;
        properties1.emissiveRendering = properties2.emissiveRendering;
        properties1.instrument = properties2.instrument;
        properties1.replaceable = properties2.replaceable;
        return properties1;
    }

    @Override
    public TABlockProperties mapColor(MapColor mapColor) {
        this.mapColor = state -> mapColor;
        return this;
    }

    @Override
    public TABlockProperties noCollission() {
        this.hasCollision = false;
        this.canOcclude = false;
        return this;
    }

    @Override
    public TABlockProperties sound(SoundType soundType) {
        this.soundType = soundType;
        return this;
    }

    @Override
    public TABlockProperties lightLevel(ToIntFunction<BlockState> lightEmission) {
        this.lightEmission = lightEmission;
        return this;
    }

    @Override
    public TABlockProperties instrument(NoteBlockInstrument instrument) {
        this.instrument = instrument;
        return this;
    }

    @Override
    public TABlockProperties pushReaction(PushReaction pushReaction) {
        this.pushReaction = pushReaction;
        return this;
    }

    @Override
    public TABlockProperties requiresCorrectToolForDrops() {
        this.requiresCorrectToolForDrops = true;
        return this;
    }

    @Override
    public TABlockProperties destroyTime(float destroyTime) {
        this.destroyTime = destroyTime;
        return this;
    }

    @Override
    public TABlockProperties explosionResistance(float explosionResistance) {
        this.explosionResistance = Math.max(0.0F, explosionResistance);
        return this;
    }

    @Override
    public TABlockProperties strength(float destroyTime, float explosionResistance) {
        return this.destroyTime(destroyTime).explosionResistance(explosionResistance);
    }

    @Override
    public TABlockProperties strength(float strength) {
        this.strength(strength, strength);
        return this;
    }

    @Override
    public TABlockProperties ignitedByLava() {
        this.ignitedByLava = true;
        return this;
    }

    @SafeVarargs
    public final TABlockProperties addBlockTag(TagKey<Block>... values) {
        this.blockTagList.addAll(Arrays.asList(values));
        return this;
    }

    public TABlockProperties lootType(TALootType lootType) {
        this.lootType = lootType;
        return this;
    }

    public TABlockProperties hasTooltips() {
        this.hasTooltips = true;
        return this;
    }

    public TABlockProperties isBuildingBlock() {
        this.isBuildingBlock = true;
        return this;
    }

    public TABlockProperties isSimpleModelBlock() {
        this.isSimpleModelBlock = true;
        return this;
    }

    public TABlockProperties isRiversidePlant() {
        this.isRiversidePlant = true;
        return this;
    }

    public TABlockProperties useSimpleBlockItem() {
        this.useSimpleBlockItem = true;
        return this;
    }

}