package sonnenlicht.somethinggood.common;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sonnenlicht.somethinggood.client.registry.ItemRegistry;

public abstract class FanGroup extends ItemGroup {

    public static final ItemGroup FAN_GROUP = new FanGroup(ItemGroup.GROUPS.length, "fan") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistry.BASE_FAN_BLOCK_ITEM.get());
        }
    };

    public FanGroup(int index, String label) {
        super(index, label);
    }

}