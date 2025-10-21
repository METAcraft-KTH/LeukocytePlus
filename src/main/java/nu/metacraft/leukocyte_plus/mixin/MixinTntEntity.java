package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.*;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;

@Mixin(TntEntity.class)
public abstract class MixinTntEntity extends Entity {

	@Shadow @Final private static short DEFAULT_FUSE;

	@Shadow @Nullable public abstract LivingEntity getOwner();

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
				original, timeSinceFuseIgnited >= DEFAULT_FUSE ? ExplosionEvents.TNT_FUSE : ExplosionEvents.TNT_INSTANT, this, getOwner()
		);
	}

	@Unique
	private static final String TIME_SINCE_FUSE_IGNITED = "TimeSinceFuseIgnited";

	@Inject(method = "writeCustomData", at = @At("HEAD"))
	public void writeNBT(WriteView nbt, CallbackInfo ci) {
		nbt.putInt(TIME_SINCE_FUSE_IGNITED, timeSinceFuseIgnited);
	}

	@Inject(method = "readCustomData", at = @At("HEAD"))
	public void readNBT(ReadView nbt, CallbackInfo ci) {
		timeSinceFuseIgnited = nbt.getInt(TIME_SINCE_FUSE_IGNITED, 0);
	}


}
