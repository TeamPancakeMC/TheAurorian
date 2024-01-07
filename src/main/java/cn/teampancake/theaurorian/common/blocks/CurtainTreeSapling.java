package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.grower.CurtainTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;

public class CurtainTreeSapling extends SaplingBlock {

    public CurtainTreeSapling(Properties properties) {
        super(new CurtainTreeGrower(), properties);
    }

}