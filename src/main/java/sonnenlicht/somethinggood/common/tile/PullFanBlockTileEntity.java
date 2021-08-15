package sonnenlicht.somethinggood.common.tile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import sonnenlicht.somethinggood.client.registry.ConfigRegistry;
import sonnenlicht.somethinggood.common.block.AbstractFanBlock;

import java.util.Random;

import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.ABSORB_ZONE;
import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.FAN_FORCE;
import static sonnenlicht.somethinggood.client.registry.TileRegistry.PULL_FAN_BLOCK_TILE;

public class PullFanBlockTileEntity extends AbstractFanBlockTileEntity {

    private final int zone;

    public PullFanBlockTileEntity() {
        super(PULL_FAN_BLOCK_TILE.get());
        this.zone = ABSORB_ZONE.get();
    }

    /** <H2>保留父类方法内容，增加新功能</H2> */
    @Override
    public void tick() {
        super.tick();
        if (getWorld() !=null && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock && getWorld().getGameTime() % 100 == 0)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED) && ConfigRegistry.PLAY_SOUND.get())
                getWorld().playSound(null, pos, SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.BLOCKS,0.5F,1);
        if (getWorld() !=null && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock && getWorld().getGameTime() % 2 == 0){
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED) && ConfigRegistry.SUMMON_PARTICLE.get()){
                setPushing();
                Random rand = new Random();
                for(int i = 0; i < 4; ++i) {
                    double px = (float)pos.getZ() + rand.nextFloat();
                    double py = (float)pos.getY() + rand.nextFloat();
                    int j = rand.nextInt(4) - 1;
                    double pz = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                    double vx = ((double)rand.nextFloat() - 0.5D) / 2.0D;
                    double vy = ((double)rand.nextFloat() - 0.5D) / 2.0D;
                    double vz = rand.nextFloat() * 2.0F * (float)j;
                    getWorld().addParticle(ParticleTypes.ENCHANTED_HIT, pz, py + 1, px, vz, vy, vx);
                }
            }
        }
    }

    /**
     * <H2>聚集风扇起作用时的逻辑</H2>
     * 将范围内活实体聚集于风扇处
     */
    @Override
    public void setPushing() {
        if (getWorld() != null) {
            for (LivingEntity living : getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPos()).grow(zone, zone, zone))) {
                if (living.isAlive() && !(living instanceof PlayerEntity)) {
                    double X = pos.getX() - living.getPosX();
                    double Y = pos.getY() - living.getPosY();
                    double Z = pos.getZ() - living.getPosZ();
                    double D = Math.sqrt(X * X + Y * Y + Z * Z);
                    double V = 1.0 - D / 15.0;
                    if (V > 0.0D) {
                        V *= V;
                        living.setMotion(living.getMotion().add(X / D * V * 0.6 * FAN_FORCE.get(), Y / D * V * 0.3 * FAN_FORCE.get(), Z / D * V * 0.6 * FAN_FORCE.get()));
                    }
                }
            }
        }
    }

}
