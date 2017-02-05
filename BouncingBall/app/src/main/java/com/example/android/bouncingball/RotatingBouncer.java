package com.example.android.bouncingball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by abhishek on 04-02-2017.
 */

public class RotatingBouncer extends Bouncer {
    int current_rotation = 0 ;

    Paint another_ball_paint = new Paint() ;

    public RotatingBouncer(  PointF given_position,
                             int    given_color,
                             RectF given_bouncing_area )
    {
        super( given_position, given_color, given_bouncing_area ) ;

        another_ball_paint.setColor( 0xFF007F00 ) ; // dark green
    }


    public void move()
    {
        super.move() ; // run the corresponding upper class method first

        current_rotation  =  current_rotation + 2 ;

        if ( current_rotation >= 360 )
        {
            current_rotation  =  0 ;
        }
    }

    public void draw( Canvas canvas )
    {
        super.draw( canvas ) ; // run the upper class draw() first

        canvas.save() ;  //  Save the original canvas state

        //  First we move the zero point of the coordinate system into
        //  the center point of the ball.
        canvas.translate( object_center_point.x, object_center_point.y ) ;

        //  Rotate the coordinate system as much as is the value of
        //  the data field current_rotation.
        canvas.rotate( current_rotation ) ;

        //  Fill one quarter of the ball with another color.
        canvas.drawArc( new RectF( -bouncer_radius, -bouncer_radius,
                        bouncer_radius,  bouncer_radius ),
                0, 90, true, another_ball_paint ) ;

        //  Fill another quarter of the ball with the new color.
        canvas.drawArc( new RectF( -bouncer_radius, -bouncer_radius,
                        bouncer_radius,  bouncer_radius ),
                180, 90, true, another_ball_paint ) ;

        //  Finally we restore the original coordinate system.
        canvas.restore() ;
    }
}
