package cn.teampancake.theaurorian.common.shields;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.data.pack.MaxShieldLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.List;
import java.util.Map;

public class MaxShieldData implements INBTSerializable<CompoundTag> {

    private final IShield shield;
    private float maxShield;
    public Map<String, List<ResourceLocation>> MAP = Maps.newHashMap();
    public List<ResourceLocation> dimension = Lists.newArrayList();
    public List<ResourceLocation> item = Lists.newArrayList();
    public List<ResourceLocation> achievements = Lists.newArrayList();
    public List<ResourceLocation> entity = Lists.newArrayList();
    public List<ResourceLocation> armor = Lists.newArrayList();
    public List<ResourceLocation> buff = Lists.newArrayList();

    public MaxShieldData(IShield shield, float maxShield) {
        this.shield = shield;
        this.maxShield = maxShield;
        this.register();
    }

    public float getMaxShield() {
        return this.maxShield;
    }

    public void setMaxShield(float maxShield) {
        this.maxShield = maxShield;
    }

    public void consumeMaxShield(float maxShield) {
        this.maxShield = Math.max(this.maxShield - maxShield, 0);
    }

    public void increaseMaxShield(float maxShield) {
        this.maxShield = Math.max(this.maxShield + maxShield, 0);
    }

    public boolean Vaild(String type, ResourceLocation location, Boolean bool) {
        ResourceLocation registryName = this.shield.getRegistryName();
        if (MaxShieldLoader.MAP.get(type).containsKey(registryName)) {
            if (bool){
                return MaxShieldLoader.MAP.get(type).get(registryName).containsKey(location) && !MAP.get(type).contains(location);
            }else {
                return MaxShieldLoader.MAP.get(type).get(registryName).containsKey(location) && MAP.get(type).contains(location);
            }
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("maxShield", this.maxShield);
        nbt.put("Dimension", serializeList(this.dimension));
        nbt.put("Item", serializeList(this.item));
        nbt.put("Achievements", serializeList(this.achievements));
        nbt.put("Entity", serializeList(this.entity));
        nbt.put("Armor", serializeList(this.armor));
        nbt.put("Buff", serializeList(this.buff));
        return nbt;
    }

    private Tag serializeList(List<ResourceLocation> biome) {
        ListTag nbt = new ListTag();
        biome.forEach(resourceLocation -> nbt.add(StringTag.valueOf(resourceLocation.toString())));
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        MAP.forEach((s, resourceLocations) -> resourceLocations.clear());
        this.maxShield = nbt.getFloat("maxShield");
        deserializeList(nbt.getList("Dimension", 10), this.dimension);
        deserializeList(nbt.getList("Item", 10), this.item);
        deserializeList(nbt.getList("Achievements", 10), this.achievements);
        deserializeList(nbt.getList("Entity", 10), this.entity);
        deserializeList(nbt.getList("Armor", 10), this.armor);
        deserializeList(nbt.getList("Buff", 10), this.buff);
    }

    private void deserializeList(ListTag nbtList, List<ResourceLocation> locations) {
        nbtList.forEach(tag -> locations.add(ResourceLocation.tryParse(tag.getAsString())));
    }

    public void register() {
        MAP.put("dimension", this.dimension);
        MAP.put("item", this.item);
        MAP.put("achievements", this.achievements);
        MAP.put("entity", this.entity);
        MAP.put("armor", this.armor);
        MAP.put("buff", this.buff);
    }

}