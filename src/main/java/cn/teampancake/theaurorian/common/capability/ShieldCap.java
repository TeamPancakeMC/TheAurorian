package cn.teampancake.theaurorian.common.capability;


import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.registry.TACapability;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ShieldCap implements INBTSerializable<CompoundTag> {

    private final Map<ResourceLocation, IShield> SHIELD_MAP = Maps.newHashMap();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        SHIELD_MAP.forEach((key, value) -> compoundTag.put(key.toString(), value.serializeNBT()));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        compoundTag.getAllKeys().forEach(key -> SHIELD_MAP.get(ResourceLocation.tryParse(key)).deserializeNBT(compoundTag.getCompound(key)));
    }

    public static LazyOptional<ShieldCap> getCapability(LivingEntity entity) {
        return entity.getCapability(TACapability.SHIELD_CAP);
    }

    public Map<ResourceLocation, IShield> getShieldMap() {
        return this.SHIELD_MAP;
    }

    public float getMaxShieldValue() {
        float maxShieldValue = 0;
        for (IShield iShield : this.SHIELD_MAP.values()) {
            maxShieldValue += iShield.getMaxShield();
        }
        return maxShieldValue;
    }


    public float damage(LivingEntity entity, DamageSource source, float damage) {
        List<IShield> sortedShieldList = getSortedShieldList();
        return applyShields(entity, source, damage, sortedShieldList, 0);
    }

    private float applyShields(LivingEntity entity, DamageSource source, float damage, List<IShield> shields, int index) {

        if (index >= shields.size() || damage <= 0.0f) {
            return damage;
        }

        IShield currentShield = shields.get(index);
        if (currentShield.getShield() <= 0.0f) {
            if (currentShield.isBroken() && entity instanceof Player){
                currentShield.onBroken(entity);
            }
            return applyShields(entity, source, damage, shields, index + 1);
        }

        //是否允许抵消
        if (currentShield.isDamageNegated(entity, source, damage)) {
            return applyShields(entity, source, damage, shields, index + 1);
        }

        float remainingDamage = currentShield.applyDamageModifiers(entity, source, damage);
        currentShield.consumeShield(damage - remainingDamage);

        if (remainingDamage > 0.0f) {
            return applyShields(entity, source, remainingDamage, shields, index + 1);
        }
        return 0.0f;
    }

    public List<IShield> getSortedShieldList() {
        List<IShield> iShields = new ArrayList<>(SHIELD_MAP.values());
        iShields.sort(Comparator.comparingInt(IShield::getPriority).reversed());
        return iShields;
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {

        private final LazyOptional<ShieldCap> instance;

        public Provider(Collection<RegistryObject<IShield>> entries) {
            Map<ResourceLocation, IShield> map = Maps.newHashMap();
            for (RegistryObject<IShield> entry : entries) {
                IShield iShield = entry.get();
                //copy IShield
                map.put(entry.getId(), iShield.copy());
            }
            this.instance = LazyOptional.of(() -> {
                ShieldCap capability = new ShieldCap();
                capability.SHIELD_MAP.putAll(map);
                return capability;
            });
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return TACapability.SHIELD_CAP.orEmpty(cap, instance);
        }

        @Override
        public CompoundTag serializeNBT() {
            return instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")).serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")).deserializeNBT(nbt);
        }

    }

}