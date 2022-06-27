package r2dx.stg.factory;

import rsc.parse.MethodParser;
import r2dx.stg.CanvasSTG;
import r2dx.stg.display.Dialog;
import r2dx.stg.display.TextureBinding;
import rutil.container.Array;
import rwt.resource.Reader;

public class DialogFactory {

    public static Dialog create(CanvasSTG canvas, String name) {

        Dialog conv = new Dialog(canvas);

        Array<String> texts = MethodParser.compress(Reader.read(name));
        Array<String> write = new Array<>(texts.size());

        for(int i = 0; i < texts.size(); i++) {
            check(write, texts.get(i), conv);
        }

        conv.texts = write;

        return conv;

    }

    private static void check(Array<String> write, String temp, Dialog conv) {

        int nowIndex = write.size();

        if(MethodParser.isCommand(temp, "set_image")) {
            TextureBinding binding = new TextureBinding();
            if(conv.images.get(nowIndex) != null) {
                binding = conv.images.get(nowIndex);
            }
            binding.add(MethodParser.getInt(temp, "index"), Reader.image(MethodParser.getString(temp, "path")));
            conv.images.set(nowIndex, binding);
        }
        else if(MethodParser.isCommand(temp, "put_dialog")) {
            if(MethodParser.hasKey(temp, "text")) {
                write.add(MethodParser.getString(temp, "text"));
            }
            if(MethodParser.hasKey(temp, "speak")) {
                conv.speak[nowIndex] = MethodParser.getInt(temp, "speak");
            }
            if(MethodParser.hasKey(temp, "join")) {
                conv.join[MethodParser.getInt(temp, "join")] = nowIndex;
            }
            if(MethodParser.hasKey(temp, "image")) {
                TextureBinding binding = new TextureBinding();
                binding.add(MethodParser.getInt(temp, "speak"), Reader.image(MethodParser.getString(temp, "image")));
                conv.images.set(nowIndex, binding);
            }
            if(MethodParser.hasKey(temp, "boss_in")) {
                if(MethodParser.getBoolean(temp, "boss_in")) {
                    conv.bossJoin = nowIndex;
                }
            }
            if(MethodParser.hasKey(temp, "boss_start")) {
                if(MethodParser.getBoolean(temp, "boss_start")) {
                    conv.bossStart = nowIndex;
                }
            }
        }

    }

}
