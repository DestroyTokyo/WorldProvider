package delta.cion.tokyo.worldprovider.config;

import delta.cion.tokyo.worldprovider.room.RoomRandomizer;

public interface DungeonLevel {

	String name();
	long level();

	RoomRandomizer ROOM_RANDOMIZER();

}
