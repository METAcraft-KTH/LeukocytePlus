package se.datasektionen.mc.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.datasektionen.mc.leukocyte_plus.EventHelper;
import se.datasektionen.mc.leukocyte_plus.events.ExplosionEvents;

@Mixin(TntMinecartEntity.class)
public abstract class MixinTntMinecartEntity extends AbstractMinecartEntity {

	@Shadow public abstract boolean isPrimed();

	@Unique
	private int timeSinceFuseIgnited = 0;
	protected MixinTntMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		method = "tick",
		at = @At("HEAD")
	)
	public void fuseTick(CallbackInfo ci) {
		if (this.isPrimed()) {
			timeSinceFuseIgnited++;
		}
	}

	@ModifyExpressionValue(
		method = "explode(Lnet/minecraft/entity/damage/DamageSource;D)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/World$ExplosionSourceType;TNT:Lnet/minecraft/world/World$ExplosionSourceType;"
		)
	)
	public World.ExplosionSourceType replaceSourceType(World.ExplosionSourceType original) {
		return EventHelper.getSourceType(
				original, timeSinceFuseIgnited >= 80 ? ExplosionEvents.TNT_MINECART_FUSE : ExplosionEvents.TNT_MINECART_INSTANT,
				this, null
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
