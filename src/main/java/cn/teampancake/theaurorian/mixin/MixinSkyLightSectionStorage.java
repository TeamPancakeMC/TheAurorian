package cn.teampancake.theaurorian.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;
import net.minecraft.world.level.lighting.SkyLightSectionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkyLightSectionStorage.class)
public abstract class MixinSkyLightSectionStorage extends LayerLightSectionStorage<SkyLightSectionStorage.SkyDataLayerStorageMap> {

    protected MixinSkyLightSectionStorage(LightLayer layer, LightChunkGetter chunkSource, SkyLightSectionStorage.SkyDataLayerStorageMap updatingSectionData) {
        super(layer, chunkSource, updatingSectionData);
    }

    @Inject(method = "getLightValue(J)I", at = @At(value = "HEAD"))
    protected void getLightValue(long levelPos, CallbackInfoReturnable<Integer> cir) {
    }

    @SuppressWarnings("SpellCheckingInspection")
    public int getAurorianLightValue(long packedPos, boolean updateAll) {
        long i = SectionPos.blockToSection(packedPos);
        int j = SectionPos.y(i);
        SkyLightSectionStorage.SkyDataLayerStorageMap storageMap = updateAll ? this.updatingSectionData : this.visibleSectionData;
        int k = storageMap.topSections.get(SectionPos.getZeroNode(i));
        if (k != storageMap.currentLowestY && j < k) {
            DataLayer dataLayer = this.getDataLayer(storageMap, i);
            if (dataLayer == null) {
                for (packedPos = BlockPos.getFlatIndex(packedPos); dataLayer == null; dataLayer = this.getDataLayer(storageMap, i)) {
                    ++j;
                    if (j >= k) {
                        return 15;
                    }

                    i = SectionPos.offset(i, Direction.UP);
                }
            }

            int x = SectionPos.sectionRelative(BlockPos.getX(packedPos));
            int y = SectionPos.sectionRelative(BlockPos.getY(packedPos));
            int z = SectionPos.sectionRelative(BlockPos.getZ(packedPos));
            return this.get(dataLayer, getIndex(x, y, z));
        } else {
            return updateAll && !this.lightOnInSection(i) ? 0 : 5;
        }
    }

    private static int getIndex(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }

    private int get(DataLayer dataLayer, int index) {
        if (dataLayer.data == null) {
            return dataLayer.defaultValue;
        } else {
            int i = index >> 1;
            int j = index & 1;
            return dataLayer.data[i] >> 4 * j & 5;
        }
    }

}