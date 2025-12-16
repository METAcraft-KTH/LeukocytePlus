package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.ExplosionEvents;

@Mixin(MinecartTNT.class)
public abstract class MinecartTNTMixin extends AbstractMinecart {

	@Shadow public abstract boolean isPrimed();

	@Shadow @Nullable private DamageSource ignitionSource;
	@Unique
	private int timeSinceFuseIgnited = 0;
	protected MinecartTNTMixin(EntityType<?> entityType, Level world) {
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
		method = "explode(Lnet/minecraft/world/damagesource/DamageSource;D)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;TNT:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
		)
	)
	public Level.ExplosionInteraction replaceSourceType(Level.ExplosionInteraction original) {
		return EventHelper.getSourceType(
				original, timeSinceFuseIgnited >= 80 ? ExplosionEvents.TNT_MINECART_FUSE : ExplosionEvents.TNT_MINECART_INSTANT,
				this, ignitionSource != null && ignitionSource.getEntity() instanceof LivingEntity igniter ? igniter : null
		);
	}

	@Unique
	private static final String TIME_SINCE_FUSE_IGNITED = "TimeSinceFuseIgnited";

	@Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
	public void writeNBT(ValueOutput nbt, CallbackInfo ci) {
		nbt.putInt(TIME_SINCE_FUSE_IGNITED, timeSinceFuseIgnited);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
	public void readNBT(ValueInput nbt, CallbackInfo ci) {
		timeSinceFuseIgnited = nbt.getIntOr(TIME_SINCE_FUSE_IGNITED, 0);
	}

}
