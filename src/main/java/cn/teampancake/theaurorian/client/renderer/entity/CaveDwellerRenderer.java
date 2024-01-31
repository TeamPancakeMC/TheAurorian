package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.CaveDwellerModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.CaveDweller;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CaveDwellerRenderer extends MobRenderer<CaveDweller, CaveDwellerModel<CaveDweller>> {

    public CaveDwellerRenderer(EntityRendererProvider.Context context) {
        super(context, new CaveDwellerModel<>(context.bakeLayer(TAModelLayers.CAVE_DWELLER)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(CaveDweller entity) {
        return AurorianMod.prefix("textures/entity/cave_dweller.png");
    }

}