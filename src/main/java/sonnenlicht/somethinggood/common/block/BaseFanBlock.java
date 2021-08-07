package sonnenlicht.somethinggood.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import sonnenlicht.somethinggood.common.tile.BaseFanBlockTileEntity;

public class BaseFanBlock extends AbstractFanBlock{

	public BaseFanBlock() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new BaseFanBlockTileEntity();
	}

}
