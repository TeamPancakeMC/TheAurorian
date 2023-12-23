package cn.teampancake.theaurorian.common.effect;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.capability.ShieldCap;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.ShieldSyncS2CMessage;
import cn.teampancake.theaurorian.common.registry.TAShields;
import cn.teampancake.theaurorian.common.shield.TempShield;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class TempShieldEffect extends TAMobEffect{

    @Nullable
    private IShield shield = null;

    public TempShieldEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF800000);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_19468_) {
        if(this.shield != null){
            ShieldCap.getCapability(livingEntity).ifPresent(shieldCap -> {
                if(shieldCap.getShieldMap().get(TAShields.TEMP.getId()) instanceof TempShield tempShield){
                    tempShield.setTempShield(this.shield);
                    this.shield = null;
                }
                if (livingEntity instanceof ServerPlayer player){
                    TAMessages.sendToPlayer(new ShieldSyncS2CMessage(shieldCap.serializeNBT()),player);
                }
            });
        }


    }



    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }


    public TempShieldEffect setShield(IShield shield) {
        this.shield = shield;
        return this;
    }
}