package cn.teampancake.theaurorian.client.model.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class SnowTundraGiantCrabModel extends DefaultedEntityGeoModel<SnowTundraGiantCrab> {

    public SnowTundraGiantCrabModel() {
        super(AurorianMod.prefix("snow_tundra_giant_crab"));
    }

}