package com.example.android.bouncingball;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * Created by abhishek on 04-02-2017.
 */

public class GraphicalObject {
    PointF object_center_point ;

    // object_velocity specifies the number of pixels the object
    // will be moved in a single movement operation.
    float object_velocity   =  4.0F ;
    int   object_color    =  Color.RED ;


    public PointF get_object_position()
    {
        return object_center_point ;
    }

    public void set_color( int new_color )
    {
        object_color  =  new_color ;
    }

    public void move_right()
    {
        object_center_point.set( object_center_point.x + object_velocity,
                object_center_point.y ) ;
    }


    public void move_left()
    {
        object_center_point.set( object_center_point.x - object_velocity,
                object_center_point.y ) ;
    }

    public void move_up()
    {
        object_center_point.set( object_center_point.x,
                object_center_point.y - object_velocity);
    }

    public void move_down()
    {
        object_center_point.set( object_center_point.x,
                object_center_point.y + object_velocity);
    }


    public void  move_this_object( float movement_in_direction_x,
                                   float movement_in_direction_y )
    {
        object_center_point.set(
                object_center_point.x  +  movement_in_direction_x,
                object_center_point.y  +  movement_in_direction_y ) ;
    }

    public void move_to_position( float new_position_x,
                                  float new_position_y )
    {
        object_center_point.set( new_position_x, new_position_y ) ;
    }
}
