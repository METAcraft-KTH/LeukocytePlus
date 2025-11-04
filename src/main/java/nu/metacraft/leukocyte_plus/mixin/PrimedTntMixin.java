package nu.metacraft.leukocyte_plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

@Mixin(PrimedTnt.class)
public abstract class PrimedTntMixin extends Entity {

	@Shadow @Final private static short DEFAULT_FUSE_TIME;

	@Shadow @Nullable public abstract LivingEntity getOwner();

	@Unique
	private int timeSinceFuseIgnited = 0;


	public PrimedTntMixin(EntityType<?> type, Level world) {
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
					target = "Lnet/minecraft/world/level/Level$ExplosionInteraction;TNT:Lnet/minecraft/world/level/Level$ExplosionInteraction;"
			)
	)
	public Level.ExplosionInteraction replaceSourceType(Level.ExplosionInteraction original) {
		return EventHelper.getSourceType(
				original, timeSinceFuseIgnited >= DEFAULT_FUSE_TIME ? ExplosionEvents.TNT_FUSE : ExplosionEvents.TNT_INSTANT, this, getOwner()
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
