package sonnenlicht.somethinggood.common.tile;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeTileEntity;
import sonnenlicht.somethinggood.client.registry.ConfigRegistry;
import sonnenlicht.somethinggood.common.block.AbstractFanBlock;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.FAN_FORCE;
import static sonnenlicht.somethinggood.client.registry.ConfigRegistry.FAN_RANGE;

public class AbstractFanBlockTileEntity extends TileEntity implements ITickableTileEntity, IForgeTileEntity {

    /** <H2>声明碰撞箱参数</H2> */
    public float xPos, yPos, zPos, xVec, yVec, zVec;


    public AbstractFanBlockTileEntity(TileEntityType<?> baseFanBlockTileEntityTileEntityType) {
        super(baseFanBlockTileEntityTileEntityType);
    }

    /** <H2>同步数据包</H2> */
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    /** <H2>同步Tag</H2> */
    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    /** <H2>发送数据包、更新碰撞</H2> */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
        if (getWorld() != null && !getWorld().isRemote) {
            final BlockState state = getWorld().getBlockState(getPos());
            setAABB();
            getWorld().notifyBlockUpdate(getPos(), state, state, 8);
            markDirty();
        }
    }

    /** <H2>每tick调用更新</H2> */
    @Override
    public void tick() {
        if (getWorld() !=null && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock && getWorld().getGameTime() % 2 == 0)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED))
                setPushing();
        if (getWorld() !=null && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock && getWorld().getGameTime() % 100 == 0)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED) && ConfigRegistry.PLAY_SOUND.get())
                getWorld().playSound(null, pos, SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.BLOCKS,0.5F,1);
        if (getWorld() !=null && getWorld().getBlockState(getPos()).getBlock() instanceof AbstractFanBlock && getWorld().getGameTime() % 2 == 0)
            if (getWorld().getBlockState(getPos()).get(AbstractFanBlock.POWERED) && ConfigRegistry.SUMMON_PARTICLE.get()){
                Random rand = new Random();
                for(int i = 0; i < 4; ++i) {
                    double px = (float)pos.getZ() + rand.nextFloat();
                    double py = (float)pos.getY() + rand.nextFloat();
                    int j = rand.nextInt(4) - 1;
                    double pz = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                    double vx = ((double)rand.nextFloat() - 0.5D) / 2.0D;
                    double vy = ((double)rand.nextFloat() - 0.5D) / 2.0D;
                    double vz = rand.nextFloat() * 2.0F * (float)j;
                    if(this instanceof BaseFanBlockTileEntity)
                        getWorld().addParticle(ParticleTypes.WHITE_ASH, pz, py + 1, px, vz, vy, vx);
                    if(this instanceof FireFanBlockTileEntity)
                        getWorld().addParticle(ParticleTypes.FLAME, pz, py + 1, px, vz, vy, vx);
                    if(this instanceof FrozenFanBlockTileEntity)
                        getWorld().addParticle(ParticleTypes.NAUTILUS, pz, py + 1, px, vz, vy, vx);
                    if(this instanceof MoreFunctionFanBlockTileEntity)
                        getWorld().addParticle(ParticleTypes.WHITE_ASH, pz, py + 1, px, vz, vy, vx);
                }
            }
        if (!getWorld().isRemote)
            setAABB();
    }


    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putFloat("xPos", xPos);
        nbt.putFloat("yPos", yPos);
        nbt.putFloat("zPos", zPos);
        nbt.putFloat("xVec", xVec);
        nbt.putFloat("yVec", yVec);
        nbt.putFloat("zVec", zVec);
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        xPos = nbt.getFloat("xPos");
        yPos = nbt.getFloat("yPos");
        zPos = nbt.getFloat("zPos");
        xVec = nbt.getFloat("xVec");
        yVec = nbt.getFloat("yVec");
        zVec = nbt.getFloat("zVec");
    }

    /** <H2>设置碰撞箱、判断方向、设置不会影响风力的方块</H2> */
    private void setAABB() {
        if(getWorld() !=null){
            BlockState state = getWorld().getBlockState(getPos());
            Direction direction = state.get(AbstractFanBlock.FACING).getOpposite();
            int i;
            for (i = 1; i < 6; i++) {
                BlockState avoid = getWorld().getBlockState(getPos().offset(direction, i));
                if (!(avoid.getBlock() instanceof AirBlock) && avoid.getMaterial() != Material.TALL_PLANTS && !(avoid.getBlock() instanceof SnowBlock) && !(avoid.getBlock() instanceof FireBlock))
                    break;
            }
            xPos = 1;yPos = 1;zPos = 1;xVec = 1;yVec = 1;zVec = 1;
            if (direction == Direction.UP) {
                yPos = i + FAN_RANGE.get();
                yVec = -1;
            }
            if (direction == Direction.DOWN) {
                yPos = -1;
                yVec = i + FAN_RANGE.get();
            }
            if (direction == Direction.WEST) {
                xPos = -1;
                xVec = i + FAN_RANGE.get();
            }
            if (direction == Direction.EAST) {
                xPos = i + FAN_RANGE.get();
                xVec = -1;
            }
            if (direction == Direction.NORTH) {
                zPos = -1;
                zVec = i + FAN_RANGE.get();
            }
            if (direction == Direction.SOUTH) {
                zPos = i + FAN_RANGE.get();
                zVec = -1;
            }
            getWorld().notifyBlockUpdate(getPos(), state, state, 8);
        }
    }



    /** <H2>获取碰撞箱</H2> */
    public AxisAlignedBB getAABB() {
        return new AxisAlignedBB(
                getPos().getX() - xVec,
                getPos().getY() - yVec,
                getPos().getZ() - zVec,
                getPos().getX() + 1D + xPos,
                getPos().getY() + 1D + yPos,
                getPos().getZ() + 1D + zPos
        );
    }

    /** <H2>风扇基础功能</H2> */
    public void setPushing() {
        if(getWorld() != null){
            BlockState state = getWorld().getBlockState(getPos());
            Direction facing = state.get(AbstractFanBlock.FACING).getOpposite();
            List<Entity> list = getWorld().getEntitiesWithinAABB(LivingEntity.class, getAABB());
            for (Entity entity : list) {
                entity.addVelocity(
                        MathHelper.sin((float) (facing.getOpposite().getHorizontalAngle() * Math.PI / 180.0F)) * 0.5D * FAN_FORCE.get(),
                        0D,
                        -MathHelper.cos((float) (facing.getOpposite().getHorizontalAngle() * Math.PI / 180.0F)) * 0.5D * FAN_FORCE.get()
                );
            }
        }
    }


    /** <H2>获取渲染箱</H2> */
    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
                getPos().getX() - xVec,
                getPos().getY() - yVec,
                getPos().getZ() - zVec,
                getPos().getX() + 1D + xPos,
                getPos().getY() + 1D + yPos,
                getPos().getZ() + 1D + zPos
        );
    }

}
