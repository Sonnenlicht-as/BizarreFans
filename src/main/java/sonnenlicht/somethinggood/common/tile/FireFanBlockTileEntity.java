package sonnenlicht.somethinggood.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import sonnenlicht.somethinggood.common.block.AbstractFanBlock;

import java.util.List;

import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.FIRE_FAN_DAMAGE;
import static sonnenlicht.somethinggood.client.registry.TileRegistry.FIRE_FAN_BLOCK_TILE;

public class FireFanBlockTileEntity extends AbstractFanBlockTileEntity {

    public FireFanBlockTileEntity() {
        super(FIRE_FAN_BLOCK_TILE.get());
    }

    /** <H2>保留父类方法内容，增加新功能</H2> */
    @Override
    public void tick() {
        super.tick();
        if (getWorld() !=null && getWorld().getGameTime() % 2 == 0 && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED))
                summonFirePlate();
    }

    /** <H2>碰撞箱逻辑，各方向无Y轴范围</H2> */
    public AxisAlignedBB getAABBXZ() {
        return new AxisAlignedBB(
                getPos().getX() - xVec,
                getPos().getY(),
                getPos().getZ() - zVec,
                getPos().getX() + xPos,
                getPos().getY(),
                getPos().getZ() + zPos
        );
    }

    /** <H2>安全地放置方块，并进行事件调整</H2> */
    public static void setPlaceBlock(BlockPos pos, BlockState state, World world) {
        net.minecraftforge.common.util.BlockSnapshot before = net.minecraftforge.common.util.BlockSnapshot.create(world.getDimensionKey(), world, pos, 3);
        world.setBlockState(pos, state);
        BlockEvent.EntityPlaceEvent evt = new BlockEvent.EntityPlaceEvent(before, Blocks.AIR.getDefaultState(), null);
        MinecraftForge.EVENT_BUS.post(evt);
        if (evt.isCanceled()) {
            world.restoringBlockSnapshots = true;
            before.restore(true, false);
            world.restoringBlockSnapshots = false;
        }
    }

    /**
     * <H2>高温风扇起作用时的逻辑</H2>
     * 将水方块变为空气、将冰变为水、对风向范围内实体造成燃烧及燃烧伤害
     */
    public void summonFirePlate() {
        BlockPos.getAllInBox(getAABBXZ()).forEach(currentPos -> {
            if (getWorld() != null) {
                BlockPos newPos = new BlockPos(currentPos.getX(), currentPos.getY() - 1, currentPos.getZ());
                BlockState state = getWorld().getBlockState(newPos);
                if (state.getMaterial() == Material.WATER)
                    setPlaceBlock(newPos.toImmutable(), Blocks.AIR.getDefaultState(), getWorld());
                else if (state.getMaterial() == Material.ICE)
                    setPlaceBlock(newPos.toImmutable(), Blocks.WATER.getDefaultState(), getWorld());
                else if (state.getMaterial() == Material.SNOW)
                    setPlaceBlock(newPos.toImmutable(), Blocks.AIR.getDefaultState(), getWorld());
                List<LivingEntity> list = getWorld().getEntitiesWithinAABB(LivingEntity.class, getAABB());
                for (LivingEntity entity : list) {
                    entity.setFire(5);
                    entity.attackEntityFrom(DamageSource.IN_FIRE, FIRE_FAN_DAMAGE.get());
                }
            }
        });
    }

}
