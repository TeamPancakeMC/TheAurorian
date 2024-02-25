package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;

public class MiscNBT {

    private int ticksFrostbite;
    private float damageAccumulation;
    private float exhaustionAccumulation;
    private float armorHurtAccumulation;

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.damageAccumulation = compound.getInt("DamageAccumulation");
        this.exhaustionAccumulation = compound.getInt("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getInt("ArmorHurtAccumulation");
    }

    public void copyFrom(MiscNBT source) {
        this.ticksFrostbite = source.ticksFrostbite;
        this.damageAccumulation = source.damageAccumulation;
        this.exhaustionAccumulation = source.exhaustionAccumulation;
        this.armorHurtAccumulation = source.armorHurtAccumulation;
    }

    public int getTicksFrostbite() {
        return this.ticksFrostbite;
    }

    public void setTicksFrostbite(int ticksFrostbite) {
        this.ticksFrostbite = ticksFrostbite;
    }

    public float getDamageAccumulation() {
        return this.damageAccumulation;
    }

    public void setDamageAccumulation(float damageAccumulation) {
        this.damageAccumulation = damageAccumulation;
    }

    public float getExhaustionAccumulation() {
        return this.exhaustionAccumulation;
    }

    public void setExhaustionAccumulation(float exhaustionAccumulation) {
        this.exhaustionAccumulation = exhaustionAccumulation;
    }

    public float getArmorHurtAccumulation() {
        return this.armorHurtAccumulation;
    }

    public void setArmorHurtAccumulation(float armorHurtAccumulation) {
        this.armorHurtAccumulation = armorHurtAccumulation;
    }

}