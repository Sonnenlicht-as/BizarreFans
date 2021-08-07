package sonnenlicht.somethinggood.common.tile;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import sonnenlicht.somethinggood.common.block.AbstractFanBlock;

import java.util.List;

import static sonnenlicht.somethinggood.client.registry.TileRegistry.FROZEN_FAN_BLOCK_TILE;

public class FrozenFanBlockTileEntity extends AbstractFanBlockTileEntity {

    public FrozenFanBlockTileEntity() {
        super(FROZEN_FAN_BLOCK_TILE.get());
    }

    /** <H2>保留父类方法内容，增加新功能</H2> */
    @Override
    public void tick() {
        super.tick();
        if (getWorld() !=null && getWorld().getGameTime() % 2 == 0 && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED))
                summonFrozenPlate();
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
     * <H2>冰冻风扇起作用时的逻辑</H2>
     * 将水方块变为冰、将岩浆变为石头、在其余部分的方块表面生成雪，对其中生物造成缓慢效果
     */
    public void summonFrozenPlate() {
        BlockPos.getAllInBox(getAABBXZ()).forEach(currentPos -> {
            if (getWorld() != null) {
                BlockPos newPos = new BlockPos(currentPos.getX(), currentPos.getY() - 1, currentPos.getZ());
                BlockState state = getWorld().getBlockState(newPos);
                BlockState states = getWorld().getBlockState(currentPos);
                if (state.getMaterial() == Material.WATER)
                    setPlaceBlock(newPos.toImmutable(), Blocks.ICE.getDefaultState(), getWorld());
                else if (state.getMaterial() == Material.LAVA)
                    setPlaceBlock(newPos.toImmutable(), Blocks.STONE.getDefaultState(), getWorld());
                else if (state.getMaterial() != Material.ICE && state.getMaterial() != Material.LAVA && state.getMaterial() != Material.AIR
                        && (states.getMaterial() == Material.AIR || states.getMaterial() == Material.PLANTS || states.getMaterial() == Material.FIRE))
                    setPlaceBlock(currentPos.toImmutable(), Blocks.SNOW.getDefaultState(), getWorld());

                List<LivingEntity> list = getWorld().getEntitiesWithinAABB(LivingEntity.class, getAABB());
                for (LivingEntity entity : list) {
                    entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS,10,1,false,false,false));
                }
            }
        });
    }


}
