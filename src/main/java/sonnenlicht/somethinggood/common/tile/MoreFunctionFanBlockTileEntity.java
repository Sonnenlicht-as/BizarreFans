package sonnenlicht.somethinggood.common.tile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import sonnenlicht.somethinggood.common.block.AbstractFanBlock;
import sonnenlicht.somethinggood.common.block.MoreFunctionFanBlock;
import sonnenlicht.somethinggood.common.mode.FanMode;

import java.util.List;

import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.*;
import static sonnenlicht.somethinggood.client.registry.TileRegistry.FUNCTION_FAN_BLOCK_TILE;

public class MoreFunctionFanBlockTileEntity extends AbstractFanBlockTileEntity {

    private final int zone;

    public MoreFunctionFanBlockTileEntity() {
        super(FUNCTION_FAN_BLOCK_TILE.get());
        this.zone = ABSORB_ZONE.get();
    }

    /** <H2>保留父类方法内容，增加新功能</H2> */
    @Override
    public void tick() {
        super.tick();
        changeMode();
    }

    /**
     * <H2>多功能风扇起作用时的逻辑</H2>
     * 分为四种模式： <br>
     {@link FanMode#NORMAL 正常模式<br>}
     {@link FanMode#XP 经验球吸收模式<br>}
     {@link FanMode#ITEM 物品吸收模式<br>}
     {@link FanMode#DAMAGE 伤害模式<br>}
     * 其中吸收模式的范围为风扇周围 9x9x9 的空间
     */
    public void changeMode(){
        if (getWorld() !=null && getWorld().getGameTime() % 2 == 0 && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock)

            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED)){

                if (getWorld().getBlockState(getPos()).get(MoreFunctionFanBlock.MODE) == FanMode.XP){
                    List<ExperienceOrbEntity> list = getWorld().getEntitiesWithinAABB(ExperienceOrbEntity.class, new AxisAlignedBB(this.getPos()).grow(zone, zone, zone));
                    for (ExperienceOrbEntity entity : list) {
                        Vector3d vector3d = new Vector3d(getPos().getX() - entity.getPosX(), getPos().getY() + 1 - entity.getPosY(), getPos().getZ() - entity.getPosZ());
                        double d1 = vector3d.lengthSquared();
                        if (d1 < 64.0D) {
                            double d2 = 1.0D - Math.sqrt(d1) / 8.0D;
                            entity.setMotion(entity.getMotion().add(vector3d.normalize().scale(d2 * d2 * 0.1D).x * FAN_FORCE.get(),vector3d.normalize().scale(d2 * d2 * 0.1D).y * FAN_FORCE.get(),vector3d.normalize().scale(d2 * d2 * 0.1D).z * FAN_FORCE.get()));
                        }
                    }
                }

                else if (getWorld().getBlockState(getPos()).get(MoreFunctionFanBlock.MODE) == FanMode.ITEM){
                    for (ItemEntity item : getWorld().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(this.getPos()).grow(zone, zone, zone))) {
                        if (item.isAlive()) {
                            double X = pos.getX() - item.getPosX();
                            double Y = pos.getY() - item.getPosY();
                            double Z = pos.getZ() - item.getPosZ();
                            double D = Math.sqrt(X * X + Y * Y + Z * Z);
                            double V = 1.0 - D / 15.0;
                            if (V > 0.0D) {
                                V *= V;
                                item.setMotion(item.getMotion().add(X / D * V * 0.1 * FAN_FORCE.get(), Y / D * V * 0.2*  FAN_FORCE.get(), Z / D * V * 0.1 * FAN_FORCE.get()));
                            }
                        }
                    }
                }

                else if (getWorld().getBlockState(getPos()).get(MoreFunctionFanBlock.MODE) == FanMode.DAMAGE){
                    List<LivingEntity> list = getWorld().getEntitiesWithinAABB(LivingEntity.class, getAABB());
                    for (LivingEntity entity : list) {
                        if(!(entity instanceof PlayerEntity))
                            entity.attackEntityFrom(new DamageSource("fun_damage"), FUNCTION_FAN_DAMAGE.get());
                    }
                }

            }

    }

}
