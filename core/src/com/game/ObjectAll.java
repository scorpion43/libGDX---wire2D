package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.object.Map;
import com.game.object.creature.Player;
import com.game.operations.Testy;

import java.util.ArrayList;

/**
 * Created by Mazek27 on 21.03.2016.
 */

public class ObjectAll {
    public static ArrayList<Map> ObjectMap;
    public int aMap;

    Viewport viewport;
    public Player mPlayer;

    public ObjectAll(){
        mPlayer = new Player ("lili");
        aMap = 0;

        ObjectMap = new ArrayList<Map> ();
        ObjectMap.add (new Map("shop_place"));


    }

    public void render(){
        //ObjectMap.get (aMap).render (camera);
    }

    public void changeMap(ArrayList<Map> objectMap, String index, String name){
        for(int i = 0 ; i < objectMap.size (); i++){
            if(objectMap.get (i).mName == name){
                this.aMap = i;
                break;
            }

            if(i == objectMap.size () -1){
                ObjectMap.add (new Map(name));
                this.aMap = ObjectMap.size () - 1;
            }

        }

        TiledMapTileLayer TMTL = (TiledMapTileLayer) ObjectMap.get (aMap).mMap.getLayers ().get (0);

        for(int i = 0; i < 40; i++){
            for(int j = 0; j < 20; j++){
                if(Testy.isSpawn (TMTL,i,j)){
                    if(Testy.index (TMTL,i,j).equals (index)){
                        mPlayer.position.set(i * 32, j * 32);
                        break;
                    }
                }
            }
        }
    }
}
