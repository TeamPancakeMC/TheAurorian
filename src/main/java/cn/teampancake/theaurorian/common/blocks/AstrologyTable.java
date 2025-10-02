package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.client.gui.hud.NightBarRender;
import cn.teampancake.theaurorian.common.blocks.entity.AstrologyTableBlockEntity;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.blocks.state.TALootType;
import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.network.FutureNightS2CPacket;
import cn.teampancake.theaurorian.common.network.ShowStarSignScreenS2CPacket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
// 新增导入
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

public class AstrologyTable extends BaseEntityBlock {

	public AstrologyTable() {
		super(TABlockProperties.get().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
				.requiresCorrectToolForDrops().strength(6.0F).sound(SoundType.METAL).lootType(TALootType.SELF).lightLevel(s -> 9).noOcclusion());
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(p -> new AstrologyTable());
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			long dayTime = (level.getDayTime() + 6000L) % 24000L;
			if (dayTime > 6000 && dayTime <= 18000) {
				String key = "message.theaurorian.astrology_table.only_at_night";
				serverPlayer.sendSystemMessage(Component.translatable(key));
				return InteractionResult.CONSUME;
			}

			int[] arr = LevelEventSubscriber.getFuturePhases();
			PacketDistributor.sendToPlayer(serverPlayer,
					new FutureNightS2CPacket(arr[0], arr[1], arr[2]),
					new ShowStarSignScreenS2CPacket());
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.CONSUME;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AstrologyTableBlockEntity(pos, state);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!isNight(level) || isAuroraNight() || random.nextFloat() > 0.15F) return;
		double cx = pos.getX() + 0.5D;
		double cy = pos.getY() + 1.0D;
		double cz = pos.getZ() + 0.5D;
		long t = level.getGameTime();
		double angle = (t * 0.12D) + random.nextDouble() * 0.75D;
		double radius = 0.55D + random.nextDouble() * 0.10D;
		double x = cx + Math.cos(angle) * radius;
		double z = cz + Math.sin(angle) * radius;
		double y = cy + 0.10D + random.nextDouble() * 0.15D;
		double speed = 0.02D;
		double vx = -Math.sin(angle) * speed;
		double vz =  Math.cos(angle) * speed;
		double vy = 0.005D + random.nextDouble() * 0.005D;
		level.addParticle(ParticleTypes.ENCHANT, x, y, z, vx, vy, vz);
		if (random.nextFloat() < 0.05F) {
			double angle2 = angle + (Math.PI * 0.66D);
			double r2 = radius - 0.05D;
			double x2 = cx + Math.cos(angle2) * r2;
			double z2 = cz + Math.sin(angle2) * r2;
			double y2 = cy + 0.15D + random.nextDouble() * 0.10D;
			double vx2 = -Math.sin(angle2) * (speed * 0.85D);
			double vz2 =  Math.cos(angle2) * (speed * 0.85D);
			double vy2 = 0.004D + random.nextDouble() * 0.004D;
			level.addParticle(ParticleTypes.ENCHANT, x2, y2, z2, vx2, vy2, vz2);
		}
	}

	private static boolean isNight(Level level) {
        long dayTime = (level.getDayTime() + 6000L) % 24000L;
		return !(dayTime > 6000 && dayTime <= 18000);
	}

	private static boolean isAuroraNight() {
		if (FMLLoader.getDist() != Dist.CLIENT) {
			return false;
		} else {
            return NightBarRender.nightType == 2;
        }
	}

}