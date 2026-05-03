package capsule.blocks;

import capsule.CapsuleMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CapsuleBlocks {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CapsuleMod.MODID);
    private static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(CapsuleMod.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CapsuleMod.MODID);

    public static final DeferredBlock<BlockCapsuleMarker> CAPSULE_MARKER = BLOCKS.register("capsulemarker", BlockCapsuleMarker::new);
    public static final DeferredItem<BlockItem> CAPSULE_MARKER_ITEM = BLOCKITEMS.registerSimpleBlockItem("capsulemarker", CAPSULE_MARKER);
    public static final Supplier<BlockEntityType<BlockEntityCapture>> MARKER_TE = BLOCK_ENTITIES.register("capsulemarker_te", () -> BlockEntityType.Builder.of(BlockEntityCapture::new, CAPSULE_MARKER.get()).build(null));

    public static void registerBlocks(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        BLOCKITEMS.register(modEventBus);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerBlockEntitiesRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MARKER_TE.get(), CaptureBER::new);
    }
}
