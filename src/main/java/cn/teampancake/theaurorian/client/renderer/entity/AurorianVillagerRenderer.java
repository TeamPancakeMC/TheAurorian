package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.registry.TAVillagerProfession;
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
}
