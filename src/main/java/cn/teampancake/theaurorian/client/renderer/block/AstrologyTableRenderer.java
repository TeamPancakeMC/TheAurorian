package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.entity.AstrologyTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AstrologyTableRenderer extends GeoBlockRenderer<AstrologyTableBlockEntity> {

	public AstrologyTableRenderer(BlockEntityRendererProvider.Context context) {
		super(new DefaultedBlockGeoModel<>(TABlockEntityTypes.ASTROLOGY_TABLE.getId()));
	}

	@Override
	public RenderType getRenderType(AstrologyTableBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityCutout(texture);
	}

} 