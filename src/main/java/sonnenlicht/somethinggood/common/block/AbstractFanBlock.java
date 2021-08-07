package sonnenlicht.somethinggood.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SuppressWarnings("all")
public class AbstractFanBlock extends DirectionalBlock implements ITileEntityProvider{

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	private static final VoxelShape SHAPE_S = Stream.of(
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 1, 11.2, 9, 23.3, 13.2), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6, 22.3, 10.2, 10, 24.3, 14.2), Block.makeCuboidShape(5, 20.8, 7.3, 11, 25.8, 11.2), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(16.4, 15.2, 1.8, 17.4, 31, 2.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.6, 15.2, 1.8, -0.6, 31, 2.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.8, 22.9, 1.7999999999999998, 8.3, 23.7, 7.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.1, 21.1, 1.5, 10.1, 25.1, 1.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.6999999999999993, 30.1, 5.7, 16.6, 31.1, 6.7), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0.5999999999999996, 17.1, 7.8, 15.4, 29.1, 8.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.5, 30.1, 1.8, 16.6, 31.1, 2.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.6999999999999993, 15.1, 5.7, 16.6, 16.1, 6.7), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.6999999999999993, 15.1, 1.8, 16.6, 16.1, 2.8), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.6, 30.1, 1.9000000000000004, -0.6, 31.1, 6.700000000000001), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(16.4, 30.1, 1.7999999999999998, 17.4, 31.1, 6.7), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.6, 15.1, 1.9000000000000004, -0.6, 16.1, 6.700000000000001), Block.makeCuboidShape(16.4, 15.1, 1.9000000000000004, 17.4, 16.1, 6.700000000000001), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1, 0, 1, 15, 1, 15), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 1, 4.199999999999999, 14, 2, 14.2), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.3, 1, 3.9, 6.3, 2, 4.3), Block.makeCuboidShape(9.3, 1, 3.9, 10.3, 2, 4.2), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR)
	).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_N = Stream.of(
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(6.782080882665051, 1, 2.722459899358549, 8.782080882665051, 23.3, 4.722459899358549), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.782080882665051, 22.3, 1.72245989935855, 9.782080882665051, 24.3, 5.722459899358549), Block.makeCuboidShape(4.782080882665051, 20.8, 4.722459899358549, 10.782080882665051, 25.8, 8.62245989935855), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.6179191173349485, 15.2, 13.122459899358548, -0.6179191173349485, 31, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(16.38208088266505, 15.2, 13.122459899358548, 17.38208088266505, 31, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.48208088266505, 22.9, 8.12245989935855, 8.982080882665052, 23.7, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.682080882665051, 21.1, 14.122459899358548, 10.682080882665051, 25.1, 14.422459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.8179191173349514, 30.1, 9.222459899358547, 16.48208088266505, 31.1, 10.22245989935855), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0.3820808826650506, 17.1, 7.122459899358548, 15.182080882665051, 29.1, 8.12245989935855), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.8179191173349514, 30.1, 13.122459899358548, 17.28208088266505, 31.1, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.8179191173349514, 15.1, 9.222459899358547, 16.48208088266505, 16.1, 10.22245989935855), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-0.8179191173349514, 15.1, 13.122459899358548, 16.48208088266505, 16.1, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(16.38208088266505, 30.1, 9.222459899358547, 17.38208088266505, 31.1, 14.022459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1.6179191173349485, 30.1, 9.222459899358547, -0.6179191173349485, 31.1, 14.122459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(16.38208088266505, 15.1, 9.222459899358547, 17.38208088266505, 16.1, 14.022459899358548), Block.makeCuboidShape(-1.6179191173349485, 15.1, 9.222459899358547, -0.6179191173349485, 16.1, 14.022459899358548), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0.7820808826650509, 0, 0.9224598993585493, 14.782080882665051, 1, 14.922459899358548), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.782080882665051, 1, 1.72245989935855, 13.782080882665051, 2, 11.72245989935855), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.482080882665052, 1, 11.622459899358548, 10.482080882665052, 2, 12.022459899358548), Block.makeCuboidShape(5.48208088266505, 1, 11.72245989935855, 6.48208088266505, 2, 12.022459899358548), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR)
	).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_W = Stream.of(
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2.6522703910118004, 1, 7.070189508346749, 4.6522703910118, 23.3, 9.070189508346749), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.6522703910118013, 22.3, 6.070189508346749, 5.6522703910118, 24.3, 10.070189508346749), Block.makeCuboidShape(4.6522703910118, 20.8, 5.070189508346749, 8.552270391011799, 25.8, 11.070189508346749), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13.052270391011799, 15.2, 16.47018950834675, 14.052270391011799, 31, 17.47018950834675), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13.052270391011799, 15.2, -1.5298104916532518, 14.052270391011799, 31, -0.5298104916532518), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(8.052270391011799, 22.9, 6.870189508346749, 14.052270391011799, 23.7, 8.37018950834675), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(14.052270391011799, 21.1, 5.170189508346748, 14.3522703910118, 25.1, 10.170189508346748), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.1522703910118, 30.1, -0.6298104916532496, 10.1522703910118, 31.1, 16.670189508346752), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.052270391011799, 17.1, 0.6701895083467484, 8.052270391011799, 29.1, 15.470189508346749), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13.052270391011799, 30.1, -1.4298104916532504, 14.052270391011799, 31.1, 16.670189508346752), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.1522703910118, 15.1, -0.6298104916532496, 10.1522703910118, 16.1, 16.670189508346752), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13.052270391011799, 15.1, -0.6298104916532496, 14.052270391011799, 16.1, 16.670189508346752), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.152270391011799, 30.1, -1.5298104916532518, 13.9522703910118, 31.1, -0.5298104916532518), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.1522703910118, 30.1, 16.47018950834675, 14.052270391011799, 31.1, 17.47018950834675), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(9.152270391011799, 15.1, -1.5298104916532518, 13.9522703910118, 16.1, -0.5298104916532518), Block.makeCuboidShape(9.152270391011799, 15.1, 16.47018950834675, 13.9522703910118, 16.1, 17.47018950834675), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0.8522703910118006, 0, 1.0701895083467488, 14.8522703910118, 1, 15.070189508346749), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.6522703910118013, 1, 2.0701895083467488, 11.6522703910118, 2, 14.070189508346749), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(11.552270391011799, 1, 5.370189508346749, 11.9522703910118, 2, 6.370189508346749), Block.makeCuboidShape(11.6522703910118, 1, 9.37018950834675, 11.9522703910118, 2, 10.37018950834675), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR)
	).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	private static final VoxelShape SHAPE_E = Stream.of(
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(11.12981049165325, 1, 6.8522703910118, 13.12981049165325, 23.3, 8.8522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(10.12981049165325, 22.3, 5.8522703910118, 14.129810491653249, 24.3, 9.8522703910118), Block.makeCuboidShape(7.22981049165325, 20.8, 4.8522703910118, 11.12981049165325, 25.8, 10.8522703910118), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 15.2, -1.5477296089881998, 2.729810491653252, 31, -0.5477296089881998), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 15.2, 16.4522703910118, 2.729810491653252, 31, 17.4522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 22.9, 7.552270391011799, 7.72981049165325, 23.7, 9.0522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.4298104916532512, 21.1, 5.7522703910118, 1.729810491653252, 25.1, 10.7522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.6298104916532505, 30.1, -0.7477296089882026, 6.629810491653252, 31.1, 16.5522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.72981049165325, 17.1, 0.45227039101179933, 8.729810491653252, 29.1, 15.252270391011798), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 30.1, -0.7477296089882026, 2.729810491653252, 31.1, 17.3522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(5.6298104916532505, 15.1, -0.7477296089882026, 6.629810491653252, 16.1, 16.5522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 15.1, -0.7477296089882026, 2.729810491653252, 16.1, 16.5522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.8298104916532516, 30.1, 16.4522703910118, 6.629810491653252, 31.1, 17.4522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.729810491653252, 30.1, -1.5477296089881998, 6.629810491653252, 31.1, -0.5477296089881998), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(1.8298104916532516, 15.1, 16.4522703910118, 6.629810491653252, 16.1, 17.4522703910118), Block.makeCuboidShape(1.8298104916532516, 15.1, -1.5477296089881998, 6.629810491653252, 16.1, -0.5477296089881998), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR),
			VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0.9298104916532512, 0, 0.8522703910117997, 14.92981049165325, 1, 14.8522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4.1298104916532505, 1, 1.8522703910117997, 14.129810491653249, 2, 13.8522703910118), VoxelShapes.combineAndSimplify(Block.makeCuboidShape(3.8298104916532516, 1, 9.5522703910118, 4.229810491653252, 2, 10.5522703910118), Block.makeCuboidShape(3.8298104916532516, 1, 5.552270391011799, 4.1298104916532505, 2, 6.552270391011799), IBooleanFunction.OR), IBooleanFunction.OR), IBooleanFunction.OR)
	).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

	public AbstractFanBlock() {
		super(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4f, 8f).setLightLevel((state) -> 0).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid());
		this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, false));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
			case SOUTH:
				return SHAPE_S;
			case EAST:
				return SHAPE_E;
			case WEST:
				return SHAPE_W;
			case NORTH:
			default:
				return SHAPE_N;
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return null;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing()).with(POWERED, false);
	}

	@Nonnull
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		boolean flag = world.isBlockPowered(pos);
		if (flag != state.get(POWERED))
			world.setBlockState(pos, state.with(POWERED, flag), 4);
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (!world.isRemote) {
			boolean flag = !world.isBlockPowered(pos);
			if (flag != state.get(POWERED))
				world.setBlockState(pos, state.with(POWERED, flag), 4);
		}
	}

}
