package nu.metacraft.leukocyte_plus.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import nu.metacraft.leukocyte_plus.EventHelper;
import nu.metacraft.leukocyte_plus.events.FireEvents;

@Mixin(LightningBolt.class)
public abstract class MixinLightningEntity extends Entity {

	public MixinLightningEntity(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow @Nullable public abstract ServerPlayer getCause();

	@Inject(method = "spawnFire", at = @At("HEAD"), cancellable = true)
	public void spawnFire(int spreadAttempts, CallbackInfo ci) {
		if (!EventHelper.shouldPlaceFire(true, FireEvents.LIGHTNING, this, this.getCause())) {
			ci.cancel();
		}
	}

}
