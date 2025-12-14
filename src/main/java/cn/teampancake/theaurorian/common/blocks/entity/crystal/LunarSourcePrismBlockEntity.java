package cn.teampancake.theaurorian.common.blocks.entity.crystal;

import cn.teampancake.theaurorian.common.blocks.crystal.AbstractLunarCrystal;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class LunarSourcePrismBlockEntity extends AbstractLunarCrystalBlockEntity {

    private static final int MAX_CONNECTION_DISTANCE = 20;
    private int moonlight = 0;
    private int maxMoonlight = 3000;
    public int beamColor;
    public float beamRadius;
    public float glowRadius;
    @Nullable
    private BlockPos connectedTarget = null;

    public LunarSourcePrismBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.LUNAR_SOURCE_PRISM.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LunarSourcePrismBlockEntity blockEntity) {
        if (blockEntity.activating && --blockEntity.activeTime == 0) {
            level.setBlockAndUpdate(pos, state.setValue(AbstractLunarCrystal.ACTIVATED, true));
            blockEntity.activating = false;
            blockEntity.activated = true;
            blockEntity.updateBlock();
        }

        if (blockEntity.activated && blockEntity.connectedTarget != null) {
            BlockEntity targetEntity = level.getBlockEntity(blockEntity.connectedTarget);
            if (!(targetEntity instanceof AbstractLunarCrystalBlockEntity)) {
                blockEntity.connectedTarget = null;
                blockEntity.updateBlock();
            }
        }

        if (blockEntity.activated && blockEntity.connectedTarget == null) {
            BlockPos target = findNearestPrism(level, pos);
            if (target != null) {
                blockEntity.connectedTarget = target;
                blockEntity.beamColor = 0xB8FFFFFF;
                blockEntity.beamRadius = 0.15F;
                blockEntity.glowRadius = 0.2F;
                blockEntity.updateBlock();
            }
        }

        if (blockEntity.moonlight < blockEntity.maxMoonlight && !blockEntity.activated) {
            boolean canSeeSky = level.canSeeSky(pos.above());
            boolean isAurorian = TACommonUtils.isAurorianDimension(level);
            if (canSeeSky && (isAurorian || level.isNight())) {
                int tickOffset = Math.abs(pos.hashCode() % 20);
                if ((level.getGameTime() + tickOffset) % 20 == 0) {
                    blockEntity.moonlight = Math.min(blockEntity.moonlight + 10, blockEntity.maxMoonlight);
                    blockEntity.updateBlock();
                }

                if (level instanceof ServerLevel serverLevel) {
                    int particleOffset = Math.abs(pos.hashCode() % 5);
                    if ((level.getGameTime() + particleOffset) % 5 == 0) {
                        spawnChargingParticles(serverLevel, pos.below());
                    }
                }
            }
        }
    }

    @Nullable
    private static BlockPos findNearestPrism(Level level, BlockPos sourcePos) {
        BlockPos nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        int searchRadius = MAX_CONNECTION_DISTANCE;
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos checkPos = sourcePos.offset(x, y, z);
                    double distance = sourcePos.distSqr(checkPos);
                    if (distance > Mth.square(searchRadius) || distance == 0) continue;
                    BlockState state = level.getBlockState(checkPos);
                    if (state.getBlock() instanceof AbstractLunarCrystal) {
                        if (state.getValue(AbstractLunarCrystal.HALF) == DoubleBlockHalf.LOWER) {
                            if (distance < nearestDistance) {
                                nearest = checkPos;
                                nearestDistance = distance;
                            }
                        }
                    }
                }
            }
        }
        
        return nearest;
    }

    private static void spawnChargingParticles(ServerLevel level, BlockPos pos) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 1.5;
        double centerZ = pos.getZ() + 0.5;
        double particleY = pos.getY() + 3.0 + level.getRandom().nextDouble() * 2.0;
        double particleX = centerX + (level.getRandom().nextDouble() - 0.5);
        double particleZ = centerZ + (level.getRandom().nextDouble() - 0.5);
        double dx = centerX - particleX;
        double dy = centerY - particleY;
        double dz = centerZ - particleZ;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double speed = Math.max(0.15, distance * 0.08);
        double speedX = (dx / distance) * speed;
        double speedY = (dy / distance) * speed * 2.0D;
        double speedZ = (dz / distance) * speed;
        level.sendParticles(ParticleTypes.END_ROD, particleX, particleY, particleZ, 3, speedX, speedY, speedZ, 0.02);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.moonlight = tag.getInt("Moonlight");
        this.maxMoonlight = tag.getInt("MaxMoonlight");
        this.beamColor = tag.getInt("BeamColor");
        this.beamRadius = tag.getFloat("BeamRadius");
        this.glowRadius = tag.getFloat("GlowRadius");
        NbtUtils.readBlockPos(tag, "ConnectedTarget").ifPresent(pos -> this.connectedTarget = pos);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Moonlight", this.moonlight);
        tag.putInt("MaxMoonlight", this.maxMoonlight);
        tag.putInt("BeamColor", this.beamColor);
        tag.putFloat("BeamRadius", this.beamRadius);
        tag.putFloat("GlowRadius", this.glowRadius);
        if (this.connectedTarget != null) {
            NbtUtils.writeBlockPos(this.connectedTarget);
        }
    }

    public int getMoonlight() {
        return this.moonlight;
    }

    public int getMaxMoonlight() {
        return this.maxMoonlight;
    }

    @Nullable
    public BlockPos getConnectedTarget() {
        return this.connectedTarget;
    }

}