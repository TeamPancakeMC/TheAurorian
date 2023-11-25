package cn.teampancake.theaurorian.common.level.chunk;

public interface NoiseModifier {

    NoiseModifier PASS_THROUGH = ((density, height, zWidth, xWidth) -> density);

    double modifyNoise(double density, int height, int zWidth, int xWidth);

}