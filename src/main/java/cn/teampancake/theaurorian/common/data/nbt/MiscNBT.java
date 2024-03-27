package cn.teampancake.theaurorian.common.data.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MiscNBT {

    public int ticksFrostbite;
    public int corruptionTime;
    public int validCorruptionTime;
    public int ticksThermalEnhancement;
    public int uninterruptedHurtByMoonQueenCount;
    public float damageAccumulation;
    public float exhaustionAccumulation;
    public float armorHurtAccumulation;
    public boolean immuneToPressure;
    public List<UUID> maxHealthSubtractUuids = new ArrayList<>();

    public void saveNBTData(CompoundTag compound) {
        compound.putInt("TicksFrostbite", this.ticksFrostbite);
        compound.putInt("ValidCorruptionTime", this.validCorruptionTime);
        compound.putInt("TicksThermalEnhancement", this.ticksThermalEnhancement);
        compound.putInt("UninterruptedHurtByMoonQueenCount",
                this.uninterruptedHurtByMoonQueenCount);
        compound.putFloat("DamageAccumulation", this.damageAccumulation);
        compound.putFloat("ExhaustionAccumulation", this.exhaustionAccumulation);
        compound.putFloat("ArmorHurtAccumulation", this.armorHurtAccumulation);
        compound.putBoolean("ImmuneToPressure", this.immuneToPressure);
        compound.put("MaxHealthSubtractUuids", this.saveListTag(this.maxHealthSubtractUuids));
    }

    public void loadNBTData(CompoundTag compound) {
        this.ticksFrostbite = compound.getInt("TicksFrostbite");
        this.corruptionTime = compound.getInt("CorruptionTime");
        this.validCorruptionTime = compound.getInt("MaxCorruptionTime");
        this.ticksThermalEnhancement = compound.getInt("TicksThermalEnhancement");
        this.uninterruptedHurtByMoonQueenCount =
                compound.getInt("UninterruptedHurtByMoonQueenCount");
        this.damageAccumulation = compound.getFloat("DamageAccumulation");
        this.exhaustionAccumulation = compound.getFloat("ExhaustionAccumulation");
        this.armorHurtAccumulation = compound.getFloat("ArmorHurtAccumulation");
        this.immuneToPressure = compound.getBoolean("ImmuneToPressure");
        ListTag listTag = compound.getList("LevelThatFirstTimeLefts", 10);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag tag = listTag.getCompound(i);
            this.maxHealthSubtractUuids.add(tag.getUUID("UUID"));
        }
    }

    private ListTag saveListTag(List<UUID> list) {
        ListTag listTag = new ListTag();
        list.forEach(s -> {
            CompoundTag compound = new CompoundTag();
            compound.putUUID("UUID", s);
            listTag.add(compound);
        });

        return listTag;
    }

}