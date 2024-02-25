package cn.teampancake.theaurorian.common.capability;

import cn.teampancake.theaurorian.common.data.nbt.MiscNBT;
import cn.teampancake.theaurorian.common.registry.TACapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiscCap implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private MiscNBT miscNbt = null;
    private final LazyOptional<MiscNBT> optional = LazyOptional.of(this::createMiscNbt);

    private @NotNull MiscNBT createMiscNbt() {
        if (this.miscNbt == null) {
            this.miscNbt = new MiscNBT();
        }

        return this.miscNbt;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TACapability.MISC_CAP) {
            return this.optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        this.createMiscNbt().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.createMiscNbt().loadNBTData(nbt);
    }

}