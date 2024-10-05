package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class MoonQueenSwordRenderer<T extends MoonQueenSword> extends GeoEntityRenderer<T> {

    public MoonQueenSwordRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(TAEntityTypes.MOON_QUEEN_SWORD.getId()));
    }

    @Override
    protected void applyRotations(T entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale) {
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        Optional<GeoBone> optional = this.model.getBone("sword");
        if (optional.isPresent()) {
            GeoBone bone = optional.get();
            bone.setPosX(-8.0F);
        }
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return 15;
    }

}