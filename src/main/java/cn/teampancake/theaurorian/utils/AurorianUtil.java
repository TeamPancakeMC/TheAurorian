package cn.teampancake.theaurorian.utils;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class AurorianUtil {

    /**
     * Will return true a given percentage of the time. Example: 0.75D will
     * return true 75% of the time.
     */
    public static boolean randomChanceOf(double percent) {
        Random r = new Random();
        double gen = r.nextDouble();
        return gen <= percent;
    }

    /**
     * Draws some particles to show where an aabb is.
     */
    public static void renderAABBBounds(Level worldIn, AABB aabb) {
        if (!worldIn.isClientSide) {
            for (double ix = aabb.minY; ix <= aabb.maxX; ix++) {
                for (double iy = aabb.minY; iy <= aabb.maxY; iy++) {
                    for (double iz = aabb.minZ; iz <= aabb.maxZ; iz++) {
                        SimpleParticleType particle = ParticleTypes.CLOUD;
                        if (ix == aabb.minX || ix == aabb.maxX) {
                            worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
                        }
                        if (iy == aabb.minY || iy == aabb.maxY) {
                            worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
                        }
                        if (iz == aabb.minZ || iz == aabb.maxZ) {
                            worldIn.addParticle(particle, ix, iy, iz, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }
}
