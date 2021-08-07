package sonnenlicht.somethinggood.common.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import sonnenlicht.somethinggood.common.tile.FireFanBlockTileEntity;

public class FireFanBlock extends AbstractFanBlock{

	public FireFanBlock() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new FireFanBlockTileEntity();
	}

}
