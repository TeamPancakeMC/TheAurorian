package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.SnowTundraGiantCrabModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowTundraGiantCrabRenderer extends MobRenderer<SnowTundraGiantCrab, SnowTundraGiantCrabModel<SnowTundraGiantCrab>> {

    public SnowTundraGiantCrabRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowTundraGiantCrabModel<>(context.bakeLayer(TAModelLayers.SNOW_TUNDRA_GIANT_CRAB)), 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SnowTundraGiantCrab entity) {
        return AurorianMod.prefix("textures/entity/snow_tundra_giant_crab.png");
    }

}