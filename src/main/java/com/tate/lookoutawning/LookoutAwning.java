package com.tate.lookoutawning;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(LookoutAwning.MOD_ID)
public class LookoutAwning {
    public static final String MOD_ID = "lookoutawning";
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Block> CANVAS_AWNING = BLOCKS.register("canvas_awning",
        () -> new AwningBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(0.8F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Item> CANVAS_AWNING_ITEM = ITEMS.register("canvas_awning",
        () -> new BlockItem(CANVAS_AWNING.get(), new Item.Properties()));

    public LookoutAwning() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        bus.addListener(this::addToTab);
    }
    private void addToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) event.accept(CANVAS_AWNING_ITEM);
    }

    public static class AwningBlock extends HorizontalDirectionalBlock {
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
        // Four shallow steps match the low canvas roof; collision leaves the space below open.
        private static final VoxelShape NORTH = Shapes.or(
            Block.box(0, 12, 0, 16, 14, 4), Block.box(0, 10, 4, 16, 12, 8),
            Block.box(0, 8, 8, 16, 10, 12), Block.box(0, 6, 12, 16, 8, 16));
        private static final VoxelShape SOUTH = Shapes.or(
            Block.box(0, 6, 0, 16, 8, 4), Block.box(0, 8, 4, 16, 10, 8),
            Block.box(0, 10, 8, 16, 12, 12), Block.box(0, 12, 12, 16, 14, 16));
        private static final VoxelShape EAST = Shapes.or(
            Block.box(0, 6, 0, 4, 8, 16), Block.box(4, 8, 0, 8, 10, 16),
            Block.box(8, 10, 0, 12, 12, 16), Block.box(12, 12, 0, 16, 14, 16));
        private static final VoxelShape WEST = Shapes.or(
            Block.box(0, 12, 0, 4, 14, 16), Block.box(4, 10, 0, 8, 12, 16),
            Block.box(8, 8, 0, 12, 10, 16), Block.box(12, 6, 0, 16, 8, 16));

        public AwningBlock(Properties properties) {
            super(properties);
            registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        }
        @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
            return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        }
        @Override public BlockState rotate(BlockState state, Rotation rotation) {
            return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
        }
        @Override public BlockState mirror(BlockState state, Mirror mirror) {
            return state.rotate(mirror.getRotation(state.getValue(FACING)));
        }
        @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }
        @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return switch (state.getValue(FACING)) {
                case NORTH -> NORTH; case SOUTH -> SOUTH; case EAST -> EAST; case WEST -> WEST;
                default -> NORTH;
            };
        }
    }
}
