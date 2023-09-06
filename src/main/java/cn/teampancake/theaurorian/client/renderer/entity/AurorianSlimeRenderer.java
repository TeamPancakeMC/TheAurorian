package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.AurorianSlime;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSlimeRenderer extends MobRenderer<AurorianSlime, SlimeModel<AurorianSlime>> {

    public AurorianSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModModelLayers.AURORIAN_SLIME)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianSlime entity) {
        return AurorianMod.prefix("textures/entity/aurorian_slime.png");
    }

}