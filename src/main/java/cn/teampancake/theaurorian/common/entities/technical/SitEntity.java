package cn.teampancake.theaurorian.common.entities.technical;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;

public class SitEntity extends Entity {

    public SitEntity(EntityType<SitEntity> type, Level level) {
        super(type, level);
    }

    public SitEntity(Level level, BlockPos pos, double yOffset) {
        super(TAEntityTypes.SIT.get(), level);
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + yOffset;
        double z = pos.getZ() + 0.5D;
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.getPassengers().isEmpty() && this.tickCount > 40) {
            this.discard();
        }
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

}