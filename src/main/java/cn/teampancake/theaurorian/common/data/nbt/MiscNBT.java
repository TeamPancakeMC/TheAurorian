package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;

public class MiscNBT {

    private int ticksFrostbite;
    private float damageAccumulation;
    private float exhaustionAccumulation;
    private float armorHurtAccumulation;
    private boolean immuneToPressure;

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
        compound.putBoolean("ImmuneToPressure", this.immuneToPressure);
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.damageAccumulation = compound.getInt("DamageAccumulation");
        this.exhaustionAccumulation = compound.getInt("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getInt("ArmorHurtAccumulation");
        this.immuneToPressure = compound.getBoolean("ShouldAffectByPressure");
    }

    public void copyFrom(MiscNBT source) {
        this.ticksFrostbite = source.ticksFrostbite;
        this.damageAccumulation = source.damageAccumulation;
        this.exhaustionAccumulation = source.exhaustionAccumulation;
        this.armorHurtAccumulation = source.armorHurtAccumulation;
        this.immuneToPressure = source.immuneToPressure;
    }

    public int getTicksFrostbite() {
        return this.ticksFrostbite;
    }

    public void setTicksFrostbite(int value) {
        this.ticksFrostbite = value;
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