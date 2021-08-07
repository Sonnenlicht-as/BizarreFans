package sonnenlicht.somethinggood.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import sonnenlicht.somethinggood.common.tile.PullFanBlockTileEntity;

public class PullFanBlock extends AbstractFanBlock{

	public PullFanBlock() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new PullFanBlockTileEntity();
	}

}
