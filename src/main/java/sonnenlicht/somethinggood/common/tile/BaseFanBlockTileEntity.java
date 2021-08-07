package sonnenlicht.somethinggood.common.tile;

import static sonnenlicht.somethinggood.client.registry.TileRegistry.BASE_FAN_BLOCK_TILE;

public class BaseFanBlockTileEntity extends AbstractFanBlockTileEntity {

    public BaseFanBlockTileEntity() {
        super(BASE_FAN_BLOCK_TILE.get());
    }

    /** <H2>保留父类方法内容</H2> */
    @Override
    public void tick() {
        super.tick();
    }
}
