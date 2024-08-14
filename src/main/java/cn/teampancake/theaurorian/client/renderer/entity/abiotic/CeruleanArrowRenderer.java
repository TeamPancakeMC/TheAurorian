package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.entities.projectile.CeruleanArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CeruleanArrowRenderer<T extends CeruleanArrowEntity> extends ArrowRenderer<T> {

    public CeruleanArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TheAurorian.prefix("textures/entity/cerulean_arrow.png");
    }

}