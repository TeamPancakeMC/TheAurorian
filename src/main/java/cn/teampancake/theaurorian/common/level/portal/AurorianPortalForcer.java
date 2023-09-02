package cn.teampancake.theaurorian.common.level.portal;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.registry.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings({"SpellCheckingInspection", "OptionalGetWithoutIsPresent"})
@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AurorianPortalForcer implements ITeleporter {

    public static final TicketType<BlockPos> AURORIAN_PORTAL = TicketType.create("aurorian_portal", Vec3i::compareTo, 300);
    public static Holder<PoiType> poi = null;

    @SubscribeEvent
    public static void registerPointOfInterest(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.POI_TYPES, registerHelper -> {
            BlockState state = ModBlocks.AURORIAN_PORTAL.get().defaultBlockState();
            PoiType poiType = new PoiType(ImmutableSet.of(state), 0, 1);
            registerHelper.register("aurorian_portal", poiType);
            poi = ForgeRegistries.POI_TYPES.getHolder(poiType).get();
        });
    }

    private final ServerLevel level;
    private final BlockPos entityEnterPos;

    public AurorianPortalForcer(ServerLevel worldServer, BlockPos entityEnterPos) {
        this.level = worldServer;
        this.entityEnterPos = entityEnterPos;
    }

    public Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos p_192986_, boolean p_192987_, WorldBorder worldBorder) {
        PoiManager poiManager = this.level.getPoiManager();
        int i = p_192987_ ? 16 : 128;
        poiManager.ensureLoadedAndValid(this.level, p_192986_, i);
        Optional<PoiRecord> optional = poiManager.getInSquare((holder) -> holder.is(poi.unwrapKey().get()), p_192986_, i, PoiManager.Occupancy.ANY)
                .filter((poiRecord) -> worldBorder.isWithinBounds(poiRecord.getPos()))
                .sorted(Comparator.<PoiRecord>comparingDouble((poiRecord) -> poiRecord.getPos().distSqr(p_192986_))
                        .thenComparingInt((poiRecord) -> poiRecord.getPos().getY()))
                .filter((poiRecord) -> this.level.getBlockState(poiRecord.getPos()).hasProperty(BlockStateProperties.HORIZONTAL_AXIS)).findFirst();
        return optional.map((poiRecord) -> {
            BlockPos blockPos = poiRecord.getPos();
            this.level.getChunkSource().addRegionTicket(AURORIAN_PORTAL, new ChunkPos(blockPos), 3, blockPos);
            BlockState blockstate = this.level.getBlockState(blockPos);
            return BlockUtil.getLargestRectangleAround(blockPos, blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS),
                    21, Direction.Axis.Y, 21, (pos) -> this.level.getBlockState(pos) == blockstate);
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos pos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d0 = -1.0D;
        BlockPos blockPos = null;
        double d1 = -1.0D;
        BlockPos blockPos1 = null;
        WorldBorder worldBorder = this.level.getWorldBorder();
        int i = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos blockPos$mutableBlockpos = pos.mutable();
        for (BlockPos.MutableBlockPos blockPos$mutableBlockpos1 : BlockPos.spiralAround(pos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, blockPos$mutableBlockpos1.getX(), blockPos$mutableBlockpos1.getZ()));
            if (worldBorder.isWithinBounds(blockPos$mutableBlockpos1) && worldBorder.isWithinBounds(blockPos$mutableBlockpos1.move(direction, 1))) {
                blockPos$mutableBlockpos1.move(direction.getOpposite(), 1);
                for (int l = j; l >= this.level.getMinBuildHeight(); --l) {
                    blockPos$mutableBlockpos1.setY(l);
                    if (this.canPortalReplaceBlock(blockPos$mutableBlockpos1)) {
                        int i1;
                        for (i1 = l; l > this.level.getMinBuildHeight() && this.canPortalReplaceBlock(blockPos$mutableBlockpos1.move(Direction.DOWN)); --l) {
                        }
                        if (l + 4 <= i) {
                            int j1 = i1 - l;
                            if (j1 <= 0 || j1 >= 3) {
                                blockPos$mutableBlockpos1.setY(l);
                                if (this.canHostFrame(blockPos$mutableBlockpos1, blockPos$mutableBlockpos, direction, 0)) {
                                    double d2 = pos.distSqr(blockPos$mutableBlockpos1);
                                    boolean flag1 = this.canHostFrame(blockPos$mutableBlockpos1, blockPos$mutableBlockpos, direction, -1);
                                    boolean flag2 = this.canHostFrame(blockPos$mutableBlockpos1, blockPos$mutableBlockpos, direction, 1);
                                    if (flag1 && flag2 && (d0 == -1.0D || d0 > d2)) {
                                        d0 = d2;
                                        blockPos = blockPos$mutableBlockpos1.immutable();
                                    }
                                    if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                                        d1 = d2;
                                        blockPos1 = blockPos$mutableBlockpos1.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (d0 == -1.0D && d1 != -1.0D) {
            blockPos = blockPos1;
            d0 = d1;
        }
        if (d0 == -1.0D) {
            int k1 = Math.max(this.level.getMinBuildHeight() + 1, 70);
            int i2 = i - 9;
            if (i2 < k1) {
                return Optional.empty();
            }
            blockPos = (new BlockPos(pos.getX(), Mth.clamp(pos.getY(), k1, i2), pos.getZ())).immutable();
            Direction direction1 = direction.getClockWise();
            if (!worldBorder.isWithinBounds(blockPos)) {
                return Optional.empty();
            }
            for (int i3 = -1; i3 < 2; ++i3) {
                for (int j3 = 0; j3 < 2; ++j3) {
                    for (int k3 = -1; k3 < 3; ++k3) {
                        BlockState blockState = k3 < 0 ? ModBlocks.AURORIAN_PORTAL_FRAME.get().defaultBlockState() : Blocks.AIR.defaultBlockState();
                        blockPos$mutableBlockpos.setWithOffset(blockPos, j3 * direction.getStepX() + i3 * direction1.getStepX(), k3, j3 * direction.getStepZ() + i3 * direction1.getStepZ());
                        this.level.setBlockAndUpdate(blockPos$mutableBlockpos, blockState);
                    }
                }
            }
        }
        for (int l1 = -1; l1 < 3; ++l1) {
            for (int j2 = -1; j2 < 4; ++j2) {
                if (l1 == -1 || l1 == 2 || j2 == -1 || j2 == 3) {
                    blockPos$mutableBlockpos.setWithOffset(blockPos, l1 * direction.getStepX(), j2, l1 * direction.getStepZ());
                    this.level.setBlock(blockPos$mutableBlockpos, ModBlocks.AURORIAN_PORTAL_FRAME.get().defaultBlockState(), 3);
                }
            }
        }
        BlockState blockState = ModBlocks.AURORIAN_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, axis);
        for (int k2 = 0; k2 < 2; ++k2) {
            for (int l2 = 0; l2 < 3; ++l2) {
                blockPos$mutableBlockpos.setWithOffset(blockPos, k2 * direction.getStepX(), l2, k2 * direction.getStepZ());
                this.level.setBlock(blockPos$mutableBlockpos, blockState, 18);
                this.level.getPoiManager().add(blockPos$mutableBlockpos, poi);
            }
        }
        return Optional.of(new BlockUtil.FoundRectangle(blockPos.immutable(), 2, 3));
    }

    @SuppressWarnings("deprecation")
    private boolean canHostFrame(BlockPos pos, BlockPos.MutableBlockPos mutableBlockPos, Direction p_77664_, int p_77665_) {
        Direction direction = p_77664_.getClockWise();
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 4; ++j) {
                mutableBlockPos.setWithOffset(pos, p_77664_.getStepX() * i + direction.getStepX() * p_77665_, j, p_77664_.getStepZ() * i + direction.getStepZ() * p_77665_);
                if (j < 0 && !this.level.getBlockState(mutableBlockPos).isSolid()) {
                    return false;
                }
                if (j >= 0 && !this.canPortalReplaceBlock(mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel server, float yaw, Function<Boolean, Entity> repositionEntity) {
        PortalInfo portalinfo = getPortalInfo(entity, server);
        if (entity instanceof ServerPlayer player) {
            player.setServerLevel(server);
            server.addDuringPortalTeleport(player);
            entity.setYRot(portalinfo.yRot % 360.0F);
            entity.setXRot(portalinfo.xRot % 360.0F);
            entity.moveTo(portalinfo.pos.x, portalinfo.pos.y, portalinfo.pos.z);
            CriteriaTriggers.CHANGED_DIMENSION.trigger(player, currentWorld.dimension(), server.dimension());
            return entity;
        } else {
            Entity entityNew = entity.getType().create(server);
            if (entityNew != null) {
                entityNew.restoreFrom(entity);
                entityNew.moveTo(portalinfo.pos.x, portalinfo.pos.y, portalinfo.pos.z, portalinfo.yRot, entityNew.getXRot());
                entityNew.setDeltaMovement(portalinfo.speed);
                server.addDuringTeleport(entityNew);
            }
            return entityNew;
        }
    }

    private PortalInfo getPortalInfo(Entity entity, ServerLevel server) {
        WorldBorder worldborder = server.getWorldBorder();
        double d0 = Math.max(-2.9999872E7D, worldborder.getMinX() + 16.);
        double d1 = Math.max(-2.9999872E7D, worldborder.getMinZ() + 16.);
        double d2 = Math.min(2.9999872E7D, worldborder.getMaxX() - 16.);
        double d3 = Math.min(2.9999872E7D, worldborder.getMaxZ() - 16.);
        double d4 = DimensionType.getTeleportationScale(entity.level().dimensionType(), server.dimensionType());
        BlockPos blockpos1 = BlockPos.containing(Mth.clamp(entity.getX() * d4, d0, d2), entity.getY(), Mth.clamp(entity.getZ() * d4, d1, d3));
        return this.getExitPortal(entity, blockpos1, worldborder).map(repositioner -> {
            BlockState blockState = entity.level().getBlockState(this.entityEnterPos);
            Direction.Axis direction$axis;
            Vec3 vector3d;
            if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                direction$axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                Predicate<BlockPos> posPredicate = pos -> entity.level().getBlockState(pos) == blockState;
                BlockUtil.FoundRectangle foundRectangle = BlockUtil.getLargestRectangleAround(this.entityEnterPos, direction$axis, 21, Direction.Axis.Y, 21, posPredicate);
                vector3d = AurorianPortalShape.getRelativePosition(foundRectangle, direction$axis, entity.position(), entity.getDimensions(entity.getPose()));
            } else {
                direction$axis = Direction.Axis.X;
                vector3d = new Vec3(0.5, 0, 0);
            }
            return AurorianPortalShape.createPortalInfo(server, repositioner, direction$axis, vector3d, entity, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
        }).orElse(new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot()));
    }

    protected Optional<BlockUtil.FoundRectangle> getExitPortal(Entity entity, BlockPos pos, WorldBorder worldBorder) {
        Optional<BlockUtil.FoundRectangle> optional = this.findPortalAround(pos, false, worldBorder);
        if (entity instanceof ServerPlayer) {
            if (optional.isPresent()) {
                return optional;
            } else {
                BlockState state = entity.level().getBlockState(this.entityEnterPos);
                Direction.Axis direction$axis = state.getOptionalValue(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
                return this.createPortal(pos, direction$axis);
            }
        } else {
            return optional;
        }
    }

    private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos pos) {
        BlockState blockstate = this.level.getBlockState(pos);
        return blockstate.canBeReplaced() && blockstate.getFluidState().isEmpty();
    }

}