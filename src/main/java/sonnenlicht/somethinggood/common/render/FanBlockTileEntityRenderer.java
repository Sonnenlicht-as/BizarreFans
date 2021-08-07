package sonnenlicht.somethinggood.common.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sonnenlicht.somethinggood.client.registry.BlockRegistry;
import sonnenlicht.somethinggood.common.block.BaseFanBlock;
import sonnenlicht.somethinggood.common.tile.BaseFanBlockTileEntity;

@OnlyIn(Dist.CLIENT)
public class FanBlockTileEntityRenderer extends TileEntityRenderer<BaseFanBlockTileEntity> {

   private final ModelRenderer leaf;

   private int degree = 0;

   public FanBlockTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
      super(rendererDispatcherIn);
      this.leaf = new ModelRenderer(64, 64, 0, 19);
      leaf.setRotationPoint(-4, -4, 0);
      this.leaf.addBox(0.0F, 0, 0F, 0F, 0F, 0F, 0.0F, false);
   }

   @Override
   public void render(BaseFanBlockTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
      World world = tileEntityIn.getWorld();
      boolean flag = world != null;
      BlockState blockstate = flag ? tileEntityIn.getBlockState() : BlockRegistry.BASE_FAN_BLOCK.get().getDefaultState().with(BaseFanBlock.FACING, Direction.SOUTH);
      Block block = blockstate.getBlock();
      if (degree == 360) {
         degree = 0;
      }
      degree++;
      if (block instanceof BaseFanBlock) {
         matrixStackIn.push();
         float f = blockstate.get(BaseFanBlock.FACING).getOpposite().getHorizontalAngle();
         matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
         matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(degree));
         RenderMaterial RenderMaterial = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/enchanting_table_book"));
         IVertexBuilder ivertexbuilder = RenderMaterial.getBuffer(bufferIn, RenderType::getEntityCutout);
         this.renderModels(matrixStackIn, ivertexbuilder, this.leaf, 2, combinedOverlayIn);
         matrixStackIn.pop();
      }
   }

   private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestBottom, int combinedLightIn, int combinedOverlayIn) {
      chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
   }

}
