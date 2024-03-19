package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;

public class MiscNBT {

    public int ticksFrostbite;
    public int corruptionTime;
    public int validCorruptionTime;
    public int uninterruptedHurtByMoonQueenCount;
    public float damageAccumulation;
    public float exhaustionAccumulation;
    public float armorHurtAccumulation;
    public boolean immuneToPressure;

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putInt("ValidCorruptionTime", this.validCorruptionTime);
        compound.putInt("UninterruptedHurtByMoonQueenCount",
                this.uninterruptedHurtByMoonQueenCount);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
        compound.putBoolean("ImmuneToPressure", this.immuneToPressure);
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.corruptionTime = compound.getInt("CorruptionTime");
        this.validCorruptionTime = compound.getInt("MaxCorruptionTime");
        this.uninterruptedHurtByMoonQueenCount =
                compound.getInt("UninterruptedHurtByMoonQueenCount");
        this.damageAccumulation = compound.getFloat("DamageAccumulation");
        this.exhaustionAccumulation = compound.getFloat("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getFloat("ArmorHurtAccumulation");
        this.immuneToPressure = compound.getBoolean("ShouldAffectByPressure");
    }

}