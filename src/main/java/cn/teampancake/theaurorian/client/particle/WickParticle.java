package cn.teampancake.theaurorian.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WickParticle extends SimpleAnimatedParticle {

    private final int halfLife;

    public WickParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0.0125F);
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.halfLife = this.lifetime / 2;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        this.alpha = this.getGlowBrightness();
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public void tick() {
        this.removed = this.age++ >= this.lifetime;
        this.setSpriteFromAge(this.sprites);
        if (this.age > this.halfLife) {
            this.setAlpha(1.0F - ((float)this.age - (float)this.halfLife) / (float)this.lifetime);
        }
    }

    private float getGlowBrightness() {
        int lifeTime = this.lifetime - this.age;
        if (lifeTime <= this.halfLife) {
            return (float) lifeTime / (float) this.halfLife;
        } else {
            return Math.max(1.0f - (((float) lifeTime - this.halfLife) / this.halfLife), 0);
        }
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        float f = ((float)this.age + scaleFactor) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            WickParticle particle = new WickParticle(level, x, y, z, this.sprite);
            particle.pickSprite(this.sprite);
            particle.scale(1.5F);
            return particle;
        }
    }

}