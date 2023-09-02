package cn.teampancake.theaurorian.data.provider;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = new ResourceLocation("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = new ResourceLocation("translucent");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AurorianMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModBlocks.MOLTEN_AURORIAN_STEEL.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_AURORIAN_STEEL.getId().getPath())
                .texture("particle", this.modLoc("block/molten_aurorian_steel")));
        this.simpleBlock(ModBlocks.MOLTEN_CERULEAN.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_CERULEAN.getId().getPath())
                .texture("particle", this.modLoc("block/molten_cerulean")));
        this.simpleBlock(ModBlocks.MOLTEN_MOONSTONE.get(), this.models()
                .getBuilder(ModBlocks.MOLTEN_MOONSTONE.getId().getPath())
                .texture("particle", this.modLoc("block/molten_moonstone")));
        this.simpleBlock(ModBlocks.MOON_WATER.get(), this.models()
                .getBuilder(ModBlocks.MOON_WATER.getId().getPath())
                .texture("particle", this.modLoc("block/moon_water")));
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

}