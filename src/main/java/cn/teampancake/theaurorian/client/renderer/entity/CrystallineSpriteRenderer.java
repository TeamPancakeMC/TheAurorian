package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.CrystallineSpriteModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.CrystallineSprite;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystallineSpriteRenderer extends MobRenderer<CrystallineSprite, CrystallineSpriteModel<CrystallineSprite>> {

    public CrystallineSpriteRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystallineSpriteModel<>(context.bakeLayer(TAModelLayers.CRYSTALLINE_SPRITE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CrystallineSprite entity) {
        return TheAurorian.prefix("textures/entity/crystalline_sprite.png");
    }

}