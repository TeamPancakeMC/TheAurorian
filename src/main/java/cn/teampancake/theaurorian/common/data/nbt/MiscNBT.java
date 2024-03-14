package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;

public class MiscNBT {

    private int ticksFrostbite;
    private int corruptionTime;
    private int validCorruptionTime;
    private float damageAccumulation;
    private float exhaustionAccumulation;
    private float armorHurtAccumulation;
    private boolean immuneToPressure;

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putInt("ValidCorruptionTime", this.validCorruptionTime);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
        compound.putBoolean("ImmuneToPressure", this.immuneToPressure);
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.corruptionTime = compound.getInt("CorruptionTime");
        this.validCorruptionTime = compound.getInt("MaxCorruptionTime");
        this.damageAccumulation = compound.getInt("DamageAccumulation");
        this.exhaustionAccumulation = compound.getInt("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getInt("ArmorHurtAccumulation");
        this.immuneToPressure = compound.getBoolean("ShouldAffectByPressure");
    }

    public int getTicksFrostbite() {
        return this.ticksFrostbite;
    }

    public void setTicksFrostbite(int value) {
        this.ticksFrostbite = value;
    }

    public int getCorruptionTime() {
        return this.corruptionTime;
    }

    public void setCorruptionTime(int value) {
        this.corruptionTime = value;
    }

    public int getValidCorruptionTime() {
        return this.validCorruptionTime;
    }

    public void setValidCorruptionTime(int value) {
        this.validCorruptionTime =  value;
    }

    public float getDamageAccumulation() {
        return this.damageAccumulation;
    }

    public void setDamageAccumulation(float value) {
        this.damageAccumulation = value;
    }

    public float getExhaustionAccumulation() {
        return this.exhaustionAccumulation;
    }

    public void setExhaustionAccumulation(float value) {
        this.exhaustionAccumulation = value;
    }

    public float getArmorHurtAccumulation() {
        return this.armorHurtAccumulation;
    }

    public void setArmorHurtAccumulation(float value) {
        this.armorHurtAccumulation = value;
    }

    public boolean isImmuneToPressure() {
        return this.immuneToPressure;
    }

    public void setImmuneToPressure(boolean value) {
        this.immuneToPressure = value;
    }

}