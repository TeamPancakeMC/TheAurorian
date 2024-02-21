package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.client.model.entity.SnowTundraGiantCrabModel;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class SnowTundraGiantCrabRenderer extends GeoEntityRenderer<SnowTundraGiantCrab> {

    public SnowTundraGiantCrabRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowTundraGiantCrabModel());
    }

}