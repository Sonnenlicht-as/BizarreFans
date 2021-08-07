package sonnenlicht.somethinggood.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import sonnenlicht.somethinggood.common.mode.FanMode;
import sonnenlicht.somethinggood.common.tile.MoreFunctionFanBlockTileEntity;

import javax.annotation.Nonnull;


public class MoreFunctionFanBlock extends AbstractFanBlock{

	public static final EnumProperty<FanMode> FAN_MODE = EnumProperty.create("fan_mode", FanMode.class);
	public static final EnumProperty<FanMode> MODE = MoreFunctionFanBlock.FAN_MODE;

	public MoreFunctionFanBlock() {
		super();
		this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, false).with(MODE, FanMode.NORMAL));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, MODE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(POWERED, false).with(MODE, FanMode.NORMAL);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new MoreFunctionFanBlockTileEntity();
	}

	@Nonnull
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		if(!worldIn.isRemote && state.get(POWERED)){
			if (state.get(MODE) == FanMode.NORMAL) {
				worldIn.setBlockState(pos, state.with(MODE,  FanMode.XP), 4);
				player.sendMessage(new TranslationTextComponent("fan_mode.xp"), player.getUniqueID());
			}
			else if(state.get(MODE) == FanMode.XP){
				worldIn.setBlockState(pos, state.with(MODE,  FanMode.ITEM), 4);
				player.sendMessage(new TranslationTextComponent("fan_mode.item"), player.getUniqueID());
			}
			else if(state.get(MODE) == FanMode.ITEM){
				worldIn.setBlockState(pos, state.with(MODE,  FanMode.DAMAGE), 4);
				player.sendMessage(new TranslationTextComponent("fan_mode.damage"), player.getUniqueID());
			}
			else if(state.get(MODE) == FanMode.DAMAGE){
				worldIn.setBlockState(pos, state.with(MODE,  FanMode.NORMAL), 4);
				player.sendMessage(new TranslationTextComponent("fan_mode.normal"), player.getUniqueID());
			}
		}
		return ActionResultType.SUCCESS;
	}

}
