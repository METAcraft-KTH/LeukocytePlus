package nu.metacraft.leukocyte_plus;

import xyz.nucleoid.leukocyte.rule.ProtectionRule;

public class LeukocytePlusRules {

	public static final ProtectionRule CREEPER_EXPLOSION = register("creeper_explosion");
	public static final ProtectionRule END_CRYSTAL_EXPLOSION = register("end_crystal_explosion");
	public static final ProtectionRule BED_EXPLOSION = register("bed_explosion");
	public static final ProtectionRule BED_FIRE = register("bed_fire");
	public static final ProtectionRule RESPAWN_ANCHOR_EXPLOSION = register("respawn_anchor_explosion");
	public static final ProtectionRule RESPAWN_ANCHOR_FIRE = register("respawn_anchor_fire");
	public static final ProtectionRule TNT_EXPLOSION = register("tnt_explosion");
	public static final ProtectionRule TNT_INSTANT_EXPLOSION = register("tnt_instant_explosion");
	public static final ProtectionRule TNT_FUSE_EXPLOSION = register("tnt_fuse_explosion");
	public static final ProtectionRule TNT_MINECART_EXPLOSION = register("tnt_minecart_explosion");
	public static final ProtectionRule TNT_MINECART_FUSE_EXPLOSION = register("tnt_minecart_fuse_explosion");
	public static final ProtectionRule TNT_MINECART_INSTANT_EXPLOSION = register("tnt_minecart_instant_explosion");
	public static final ProtectionRule FIREBALL_EXPLOSION = register("fireball_explosion");
	public static final ProtectionRule EXPLOSIVE_FIREBALL_FIRE = register("explosive_fireball_fire");
	public static final ProtectionRule LIGHTNING_FIRE = register("lightning_fire");
	public static final ProtectionRule WITHER_GRIEFING = register("wither_griefing");
	public static final ProtectionRule ENTITY_BLOCK_PLACE = register("entity_block_place");
	public static final ProtectionRule ENTITY_BLOCK_BREAK = register("entity_block_break");
	public static final ProtectionRule ENTITY_BLOCK_EDIT = register("entity_block_edit");
	public static final ProtectionRule ARMOUR_STAND_PLACE = register("armour_stand_place");
	public static final ProtectionRule ARMOUR_STAND_BREAK = register("armour_stand_break");
	public static final ProtectionRule ARMOUR_STAND_EDIT = register("armour_stand_edit");
	public static final ProtectionRule PAINTING_BREAK = register("painting_break");
	public static final ProtectionRule PAINTING_PLACE = register("painting_place");
	public static final ProtectionRule ITEM_FRAME_PLACE = register("item_frame_place");
	public static final ProtectionRule ITEM_FRAME_BREAK = register("item_frame_break");
	public static final ProtectionRule ITEM_FRAME_EDIT = register("item_frame_edit");
	public static final ProtectionRule END_CRYSTAL_PLACE = register("end_crystal_place");
	public static final ProtectionRule END_CRYSTAL_BREAK = register("end_crystal_break");
	public static final ProtectionRule FLUID_PLACE = register("fluid_place");
	public static final ProtectionRule FLUID_PICKUP = register("fluid_pickup");
	public static final ProtectionRule WATER_PLACE = register("water_place");
	public static final ProtectionRule PLACE_FISH = register("fish_place");
	public static final ProtectionRule WATER_PICKUP = register("water_pickup");
	public static final ProtectionRule PICKUP_FISH = register("fish_pickup");
	public static final ProtectionRule LAVA_PLACE = register("lava_place");
	public static final ProtectionRule LAVA_PICKUP = register("lava_pickup");
	public static final ProtectionRule PLACE_FIRE = register("fire_place");
	public static final ProtectionRule PROJECTILES_BREAK_BLOCKS = register("projectiles_break_blocks");
	public static final ProtectionRule KEEP_INVENTORY = register("keep_inventory");


	public static void init() {

	}

	private static ProtectionRule register(String id) {
		return ProtectionRule.register(id);
	}

}
