package delta.cion.tokyo.worldprovider;

import delta.cion.tokyo.api.command.DeltaCommand;
import delta.cion.tokyo.api.event.DeltaEvent;
import delta.cion.tokyo.api.plugin.Plugin;
import delta.cion.tokyo.worldprovider.command.*;
import delta.cion.tokyo.worldprovider.event.PlayerBorderEvent;
import delta.cion.tokyo.worldprovider.event.PlayerConnectionEvent;
import delta.cion.tokyo.worldprovider.event.PlayerDamageEvent;
import delta.cion.tokyo.worldprovider.event.PlayerItemEvent;
import delta.cion.tokyo.worldprovider.pvp.DamageBasics;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.player.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldProvider extends Plugin {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorldProvider.class);

	private static Plugin instance;

	private static final Pos SPAWN_POSITION = new Pos(0.5, 50.0, 0.5);
	private static final Pos MOB_POSITION = new Pos(0.5, 50.0, 3.5);

	private static final DeltaCommand SET_COMMAND = new WpSet();
	private static final DeltaCommand SHOW_COMMAND = new WpShow();
	private static final DeltaCommand RELOAD_COMMAND = new WpReload();

	private static DeltaEvent<AsyncPlayerConfigurationEvent> CONNECTION_EVENT;
	private static DeltaEvent<PlayerDisconnectEvent> DISCONNECT_EVENT;

	private static DeltaEvent<PlayerBlockBreakEvent> BLOCK_BREAK;
	private static DeltaEvent<PlayerBlockPlaceEvent> BLOCK_PLACE;

	private static DeltaEvent<PlayerMoveEvent> WORLD_BORDER;
	private static DeltaEvent<ItemDropEvent> DROP_ITEM;

	private static DeltaEvent<PlayerUseItemEvent> OPEN_BOOK;

	private static DeltaEvent<EntityDamageEvent> PLAYER_DAMAGE_EVENT;
	private static DeltaEvent<EntityAttackEvent> ENTITY_ATTACK_EVENT;

	private static final DeltaCommand TEST_UNIT_SPAWN_COMMAND = new TestUnit();
	private static final DeltaCommand GAMEMODE_COMMAND = new Gamemode();
	private static final DeltaCommand GET_COMMAND = new GetCommand();

	public static Plugin getInstance() {
		return instance;
	}

	public WorldProvider(String id, String name, String version) {
		super(id, name, version);
	}


	public static Pos getSpawnPosition() {
		return SPAWN_POSITION;
	}

	public static Pos getMobPosition() {
		return MOB_POSITION;
	}

	@Override
	public void onEnable() {
		if (instance == null) instance= this;

		LOGGER.info("Register WP commands");

		SET_COMMAND.register();
		SHOW_COMMAND.register();
		RELOAD_COMMAND.register();

		TEST_UNIT_SPAWN_COMMAND.register();
		GAMEMODE_COMMAND.register();
		GET_COMMAND.register();

		LOGGER.info("Register WP events");

		DamageBasics.registerAll();
		WORLD_BORDER = PlayerBorderEvent.playerMoveEvent();
		BLOCK_BREAK = PlayerBorderEvent.playerBlockBreakEvent();
		BLOCK_PLACE = PlayerBorderEvent.playerBlockPlaceEvent();
		OPEN_BOOK = PlayerItemEvent.playerDamageEvent();
		DROP_ITEM = PlayerItemEvent.itemDropEvent();

		DROP_ITEM.register();
		OPEN_BOOK.register();
		WORLD_BORDER.register();
		BLOCK_BREAK.register();
		BLOCK_PLACE.register();

		PlayerConnectionEvent.init();
		CONNECTION_EVENT = PlayerConnectionEvent.connectPlayer();
		DISCONNECT_EVENT = PlayerConnectionEvent.exitPlayer();
		PLAYER_DAMAGE_EVENT = PlayerDamageEvent.playerDamageEvent();
		ENTITY_ATTACK_EVENT = PlayerDamageEvent.entityAttackEvent();

		CONNECTION_EVENT.register();
		DISCONNECT_EVENT.register();
		PLAYER_DAMAGE_EVENT.register();
		ENTITY_ATTACK_EVENT.register();
	}

	@Override
	public void onDisable() {
		LOGGER.info("Unregister WP commands");

		SET_COMMAND.unregister();
		SHOW_COMMAND.unregister();
		RELOAD_COMMAND.unregister();

		TEST_UNIT_SPAWN_COMMAND.unregister();
		GAMEMODE_COMMAND.unregister();
		GET_COMMAND.unregister();

		LOGGER.info("Unregister WP events");

		PlayerConnectionEvent.close();

		WORLD_BORDER.unregister();
		DROP_ITEM.unregister();
		BLOCK_BREAK.unregister();
		BLOCK_PLACE.unregister();
		OPEN_BOOK.unregister();
		CONNECTION_EVENT.unregister();
		DISCONNECT_EVENT.unregister();
		PLAYER_DAMAGE_EVENT.unregister();
		ENTITY_ATTACK_EVENT.unregister();
	}

}
