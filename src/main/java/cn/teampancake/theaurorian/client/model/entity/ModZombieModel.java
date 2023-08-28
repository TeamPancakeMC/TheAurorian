package cn.teampancake.theaurorian.client.model.entity;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

public class ModZombieModel<T extends Monster> extends AbstractZombieModel<T> {

    public ModZombieModel(ModelPart root) {
        super(root);
    }

    @Override
    public boolean isAggressive(T entity) {
        return entity.isAggressive();
    }

}