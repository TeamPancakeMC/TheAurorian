package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPigRenderer extends MobRenderer<AurorianPig, PigModel<AurorianPig>> {

    public AurorianPigRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModModelLayers.AURORIAN_PIG)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianPig pEntity) {
        return AurorianMod.prefix("textures/entity/aurorian_pig.png");
    }

}