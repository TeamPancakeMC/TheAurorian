package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;

public class MiscNBT {

    private int ticksFrostbite;
    private float damageAccumulation;
    private float exhaustionAccumulation;
    private float armorHurtAccumulation;
    private boolean shouldAffectByPressure = true;

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
        compound.putBoolean("ShouldAffectByPressure", this.shouldAffectByPressure);
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.damageAccumulation = compound.getInt("DamageAccumulation");
        this.exhaustionAccumulation = compound.getInt("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getInt("ArmorHurtAccumulation");
        this.shouldAffectByPressure = compound.getBoolean("ShouldAffectByPressure");
    }

    public void copyFrom(MiscNBT source) {
        this.ticksFrostbite = source.ticksFrostbite;
        this.damageAccumulation = source.damageAccumulation;
        this.exhaustionAccumulation = source.exhaustionAccumulation;
        this.armorHurtAccumulation = source.armorHurtAccumulation;
        this.shouldAffectByPressure = source.shouldAffectByPressure;
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

    public boolean isShouldAffectByPressure() {
        return this.shouldAffectByPressure;
    }

    public void setShouldAffectByPressure(boolean value) {
        this.shouldAffectByPressure = value;
    }

}