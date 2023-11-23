package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.level.portal.AurorianPortalForcer;
import cn.teampancake.theaurorian.registry.TADimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianPortal extends NetherPortalBlock {

    public AurorianPortal(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {}

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canChangeDimensions() && entity.level() instanceof ServerLevel serverLevel) {
            ResourceKey<Level> aurorianKey = TADimensions.AURORIAN_DIMENSION;
            ResourceKey<Level> resourceKey = entity.level().dimension() == aurorianKey ? Level.OVERWORLD : aurorianKey;
            ServerLevel newServerLevel = serverLevel.getServer().getLevel(resourceKey);
            if (newServerLevel != null && entity instanceof Player && !entity.isPassenger()) {
                entity.setPortalCooldown();
                entity.changeDimension(newServerLevel, new AurorianPortalForcer(newServerLevel, entity.getOnPos()));
            }
        }
    }

}