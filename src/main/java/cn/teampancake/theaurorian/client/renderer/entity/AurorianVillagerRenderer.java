package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.registry.TAVillagerProfession;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerData;

public class AurorianVillagerRenderer extends LivingEntityRenderer<AurorianVillager, PlayerModel<AurorianVillager>> {

    private static final ResourceLocation ASTROLOGER  = AurorianMod.prefix("textures/entity/astrologer.png");
    private static final ResourceLocation ASTROLOGER_2  = AurorianMod.prefix("textures/entity/astrologer_2.png");


    public AurorianVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianVillager aurorianVillager) {
        VillagerData data = aurorianVillager.getVillagerData();
        if(data.getProfession().equals(TAVillagerProfession.PROF_ASTROLOGER.get())){
            return ASTROLOGER;
        }

        //Default Villager
        return ASTROLOGER;
    }

    @Override
    protected void scale(AurorianVillager pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime) {
        float $$3 = 0.9375F;
        if (pLivingEntity.isBaby()) {
            $$3 *= 0.5F;
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }

        pMatrixStack.scale($$3, $$3, $$3);
    }
}
