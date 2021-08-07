package sonnenlicht.somethinggood.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import sonnenlicht.somethinggood.common.tile.FrozenFanBlockTileEntity;

public class FrozenFanBlock extends AbstractFanBlock{

	public FrozenFanBlock() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new FrozenFanBlockTileEntity();
	}

}
