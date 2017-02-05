package com.example.android.bouncingball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by abhishek on 04-02-2017.
 */

public class Bouncer extends GraphicalObject {
    float bouncer_radius  =  30 ;

    // bouncer_direction is an angle in radians. This angle specifies
    // the direction where the bouncer will be moved next.
    float bouncer_direction  =  (float) ( Math.random() * Math.PI * 2 );

    RectF bouncing_area ;

    public Bouncer( PointF given_position,
                    int    given_color,
                    RectF  given_bouncing_area )
    {
        object_center_point =  given_position ;
        object_color        =  given_color ;
        bouncing_area       =  given_bouncing_area ;
    }

    public float get_bouncer_radius()
    {
        return bouncer_radius ;
    }

    public void shrink()
    {
        //  The if-construct ensures that the ball does not become
        //  too small.

        if ( bouncer_radius  > 5 )
        {
            bouncer_radius  -=  3 ;
        }
    }


    public void enlarge()
    {
        bouncer_radius  = bouncer_radius + 3 ;
    }


    public void set_radius( int new_radius )
    {
        if ( new_radius  >  3 )
        {
            bouncer_radius  =  (float) new_radius ;
        }
    }

    public boolean contains_point( PointF given_point )
    {
        //  Here we use the Pythagorean theorem to calculate the distance
        //  from the given point to the center point of the ball.
        //  See the note at the end of this file.

        float distance_from_given_point_to_ball_center  = (float)

                Math.sqrt(

                        Math.pow( object_center_point.x  -  given_point.x, 2 )  +
                                Math.pow( object_center_point.y  -  given_point.y, 2 )  ) ;

        return ( distance_from_given_point_to_ball_center  <=  bouncer_radius ) ;
    }

    //  The move() method is supposed to be called something like
    //  25 times a second.

    public void move()
    {
        //  In the following statement a minus sign is needed when the
        //  y coordinate is calculated. The reason for this is that the
        //  y direction in the graphical coordinate system is 'upside down'.

        object_center_point.set(
                object_center_point.x +
                        object_velocity * (float)Math.cos(bouncer_direction),
                object_center_point.y -
                        object_velocity * (float)Math.sin(bouncer_direction));

        //  Now, after we have moved this bouncer, we start finding out whether
        //  or not it has hit a wall or some other obstacle. If a hit occurs,
        //  a new direction for the bouncer must be calculated.

        //  The following four if constructs must be four separate ifs.
        //  If they are replaced with an if - else if - else if - else if
        //  construct, the program will not work when the bouncer enters
        //  a corner in an angle of 45 degrees (i.e. Math.PI / 4).

        if ( object_center_point.y - bouncer_radius <=  bouncing_area.top )
        {
            //  The bouncer has hit the northern 'wall' of the bouncing area.

            bouncer_direction = 2 * (float) Math.PI - bouncer_direction ;
        }

        if ( object_center_point.x - bouncer_radius <=  bouncing_area.left )
        {
            //  The western wall has been reached.

            bouncer_direction = (float) Math.PI - bouncer_direction ;
        }

        if ( object_center_point.y  +  bouncer_radius >= bouncing_area.bottom )
        {
            //  Southern wall has been reached.

            bouncer_direction = 2 * (float) Math.PI - bouncer_direction ;
        }

        if ( object_center_point.x  +  bouncer_radius >= bouncing_area.right )
        {
            //  Eastern wall reached.

            bouncer_direction = (float) Math.PI - bouncer_direction ;
        }
    }


    public void draw( Canvas canvas )
    {
        Paint filling_paint = new Paint() ;
        filling_paint.setStyle( Paint.Style.FILL ) ;
        filling_paint.setColor( object_color ) ;
        Paint outline_paint = new Paint() ;
        outline_paint.setStyle( Paint.Style.STROKE ) ;
        // Default color for a Paint is black.

        canvas.drawCircle( object_center_point.x, object_center_point.y,
                bouncer_radius, filling_paint ) ;

        canvas.drawCircle( object_center_point.x, object_center_point.y,
                bouncer_radius, outline_paint ) ;
    }
}
