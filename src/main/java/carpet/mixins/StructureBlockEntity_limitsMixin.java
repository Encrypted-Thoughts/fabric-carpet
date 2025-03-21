package carpet.mixins;

import carpet.CarpetSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockEntity_limitsMixin
{
    @ModifyConstant(
            method = "loadAdditional",
            constant = @Constant(intValue = StructureBlockEntity.MAX_SIZE_PER_AXIS)
    )
    private int positiveLimit(int original) {
        return CarpetSettings.structureBlockLimit;
    }

    @ModifyConstant(
            method = "loadAdditional",
            constant = @Constant(intValue = -StructureBlockEntity.MAX_SIZE_PER_AXIS)
    )
    private int negativeLimit(int original) {
        return -CarpetSettings.structureBlockLimit;
    }

    @ModifyArg(
            method = "saveStructure(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Vec3i;ZLjava/lang/String;Z)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;fillFromWorld(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Vec3i;ZLnet/minecraft/world/level/block/Block;)V"
            ),
            index = 4
    )
    private static Block ignoredBlock(Block original) {
        return CarpetSettings.structureBlockIgnoredBlock;
    }
}
