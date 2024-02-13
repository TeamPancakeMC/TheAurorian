package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.data.pack.MaxShieldLoader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.Map;

public class MaxShieldData implements INBTSerializable<CompoundTag> {

    private final IShield shield;
    private float maxShield;
    public Map<String, List<ResourceLocation>> MAP = Maps.newHashMap();
    public List<ResourceLocation> Dimension = Lists.newArrayList();
    public List<ResourceLocation> Item = Lists.newArrayList();
    public List<ResourceLocation> Achievements = Lists.newArrayList();
    public List<ResourceLocation> Entity = Lists.newArrayList();
    public List<ResourceLocation> Armor = Lists.newArrayList();
    public List<ResourceLocation> Buff = Lists.newArrayList();

    public MaxShieldData(IShield shield,float maxShield) {
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
        ResourceLocation registryName = shield.getRegistryName();
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
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("maxShield", maxShield);
        nbt.put("Dimension", serializeList(Dimension));
        nbt.put("Item", serializeList(Item));
        nbt.put("Achievements", serializeList(Achievements));
        nbt.put("Entity", serializeList(Entity));
        nbt.put("Armor", serializeList(Armor));
        nbt.put("Buff", serializeList(Buff));
        return nbt;
    }

    private Tag serializeList(List<ResourceLocation> biome) {
        ListTag nbt = new ListTag();
        biome.forEach(resourceLocation -> nbt.add(StringTag.valueOf(resourceLocation.toString())));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        MAP.forEach((s, resourceLocations) -> resourceLocations.clear());
        this.maxShield = nbt.getFloat("maxShield");
        deserializeList(nbt.getList("Dimension", 10), Dimension);
        deserializeList(nbt.getList("Item", 10), Item);
        deserializeList(nbt.getList("Achievements", 10), Achievements);
        deserializeList(nbt.getList("Entity", 10), Entity);
        deserializeList(nbt.getList("Armor", 10), Armor);
        deserializeList(nbt.getList("Buff", 10), Buff);
    }

    private void deserializeList(ListTag nbtList, List<ResourceLocation> locations) {
        nbtList.forEach(tag -> locations.add(new ResourceLocation(tag.getAsString())));
    }

    public void register() {
        MAP.put("dimension", Dimension);
        MAP.put("item", Item);
        MAP.put("achievements", Achievements);
        MAP.put("entity", Entity);
        MAP.put("armor", Armor);
        MAP.put("buff", Buff);
    }

}