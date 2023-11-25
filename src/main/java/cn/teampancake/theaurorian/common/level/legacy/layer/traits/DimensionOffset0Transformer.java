package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

public interface DimensionOffset0Transformer extends DimensionTransformer {

	@Override
	default int getParentX(int x) {
		return x;
	}

	@Override
	default int getParentY(int z) {
		return z;
	}

}