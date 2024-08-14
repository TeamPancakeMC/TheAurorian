package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

public interface DimensionOffset1Transformer extends DimensionTransformer {

	@Override
    default int getParentX(int x) {
		return x - 1;
	}

	@Override
	default int getParentY(int z) {
		return z - 1;
	}

}