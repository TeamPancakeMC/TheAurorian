package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.client.model.entity.ModZombieModel;
import cn.teampancake.theaurorian.common.entities.monster.UndeadKnight;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UndeadKnightRenderer extends HumanoidMobRenderer<UndeadKnight, AbstractZombieModel<UndeadKnight>> {

    public UndeadKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new ModZombieModel<>(context.bakeLayer(ModModelLayers.UNDEAD_KNIGHT)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new ModZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new ModZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(UndeadKnight livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(UndeadKnight entity) {
        return AurorianMod.prefix("textures/entity/undead_knight.png");
    }

}