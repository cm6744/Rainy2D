package r2dx.stg.factory;

import rsc.parse.MethodParser;
import r2dx.stg.CanvasSTG;
import r2dx.stg.action.BulletActRead;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementPlayer;
import rutil.container.Array;
import rutil.container.Map;
import rwt.resource.Reader;

public class ActFactory {

    //set these before using!!
    public static CanvasSTG canvas;
    public static ElementPlayer player;
    public static Map<ElementBullet> bm;

    public static BulletActRead read(String path) {

        Array<String> commands = MethodParser.compress(Reader.read(path));
        BulletActRead act = new BulletActRead(canvas, player);

        for(int i = 0; i < commands.size(); i++) {
            cb(act, commands.get(i));
        }

        return act;

    }

    private static void cb(BulletActRead act, String command) {

        BulletActRead.Actor actor = act.cra();

        if(MethodParser.isCommand(command, "b_act")) {
            actor.start = MethodParser.getInt(command, "start");
            actor.end = MethodParser.getInt(command, "end");
            actor.space = MethodParser.getInt(command, "space");
            actor.period = MethodParser.getInt(command, "period");
            actor.turnPlayer = MethodParser.getBoolean(command, "turn_player");
            actor.speedTurn = MethodParser.getDouble(command, "speed_turn");
            actor.speedLimit = MethodParser.getDouble(command, "speed_limit");
            actor.angleTurn = MethodParser.getDouble(command, "angle_turn");
        }

        act.actors.add(actor);

    }

}
