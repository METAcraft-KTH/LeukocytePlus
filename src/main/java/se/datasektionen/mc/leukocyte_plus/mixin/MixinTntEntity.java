package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.datasektionen.mc.leukocyte_plus.EventHelper;
import se.datasektionen.mc.leukocyte_plus.events.ExplosionEvents;

@Mixin(TntEntity.class)
public abstract class MixinTntEntity extends Entity {

	@Shadow @Nullable private LivingEntity causingEntity;

	@Shadow @Final private static int DEFAULT_FUSE;
	@Unique
	private int timeSinceFuseIgnited = 0;


	public MixinTntEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	public void fuseTick(CallbackInfo ci) {
		timeSinceFuseIgnited++;
	}


	@ModifyExpressionValue(
			method = "explode",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/World$ExplosionSourceType;TNT:Lnet/minecraft/world/World$ExplosionSourceType;"
			)
	)
	public World.ExplosionSourceType replaceSourceType(World.ExplosionSourceType original) {
		return EventHelper.getSourceType(
				original, timeSinceFuseIgnited >= DEFAULT_FUSE ? ExplosionEvents.TNT_FUSE : ExplosionEvents.TNT_INSTANT, this, this.causingEntity
		);
	}

	@Unique
	private static final String TIME_SINCE_FUSE_IGNITED = "TimeSinceFuseIgnited";

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	public void writeNBT(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt(TIME_SINCE_FUSE_IGNITED, timeSinceFuseIgnited);
	}
	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	public void readNBT(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains(TIME_SINCE_FUSE_IGNITED)) {
			timeSinceFuseIgnited = nbt.getInt(TIME_SINCE_FUSE_IGNITED);
		}
	}


}
