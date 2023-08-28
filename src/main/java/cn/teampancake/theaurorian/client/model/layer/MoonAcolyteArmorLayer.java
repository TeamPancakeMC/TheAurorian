package cn.teampancake.theaurorian.client.model.layer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.ModModelLayers;
import cn.teampancake.theaurorian.client.model.entity.ModZombieModel;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class MoonAcolyteArmorLayer<T extends MoonAcolyte, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation LAYER_LOCATION = AurorianMod.prefix("textures/entity/moon_acolyte_layer.png");
    private final ModZombieModel<T> model;

    public MoonAcolyteArmorLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new ModZombieModel<>(modelSet.bakeLayer(ModModelLayers.MOON_ACOLYTE_OUTER_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LAYER_LOCATION, poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, 1.0F, 1.0F, 1.0F);
    }

}