package cn.teampancake.theaurorian.data.provider.tag;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.TAFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TAFluidTagsProvider extends FluidTagsProvider {

    public TAFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, AurorianMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FluidTags.LAVA).add(
                TAFluids.MOLTEN_AURORIAN_STEEL_STILL.get(),
                TAFluids.MOLTEN_AURORIAN_STEEL_FLOWING.get(),
                TAFluids.MOLTEN_CERULEAN_STILL.get(),
                TAFluids.MOLTEN_CERULEAN_FLOWING.get(),
                TAFluids.MOLTEN_MOONSTONE_STILL.get(),
                TAFluids.MOLTEN_MOONSTONE_FLOWING.get());
        this.tag(FluidTags.WATER).add(
                TAFluids.MOON_WATER_STILL.get(),
                TAFluids.MOON_WATER_FLOWING.get());
    }

}