package com.example.android.bouncingball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by abhishek on 04-02-2017.
 */

public class ExplodingBouncer extends RotatingBouncer {
    static final int BALL_ALIVE_AND_WELL  =  0 ;
    static final int BALL_EXPLODING       =  1 ;
    static final int BALL_EXPLODED        =  2 ;

    int ball_state  =  BALL_ALIVE_AND_WELL ;

    int explosion_color_alpha_value = 0 ;

    public ExplodingBouncer( PointF given_position,
                             int    given_color,
                             RectF given_bouncing_area  )
    {
        super( given_position, given_color, given_bouncing_area ) ;
    }

    public void explode_ball()
    {
        ball_state = BALL_EXPLODING ;
        enlarge() ; // make the ball somewhat larger in explosion
        enlarge() ;
    }

    public boolean is_exploded()
    {
        return ( ball_state == BALL_EXPLODED ) ;
    }

    public void move()
    {
        //  The ball will not move if it is exploding or exploded.

        if ( ball_state == BALL_ALIVE_AND_WELL )
        {
            super.move() ; // move the ball with the superclass method
        }
    }

    public void draw( Canvas canvas )
    {
        if ( ball_state == BALL_ALIVE_AND_WELL )
        {
            super.draw( canvas ) ; // run the upper class draw() first
        }
        else if ( ball_state == BALL_EXPLODING )
        {
            if ( explosion_color_alpha_value > 0xFF )
            {
                ball_state = BALL_EXPLODED ;
            }
            else
            {
                // The ball will be 'exploded' by drawing a transparent
                // yellow ball over the original ball.
                // As the opaqueness of the yellow color gradually increases,
                // the ball becomes ultimately completely yellow in
                // the final stage of the explosion.

                super.draw( canvas ) ; // draw the original ball first

                Paint explosion_paint  =  new Paint() ;

                explosion_paint.setColor( Color.YELLOW ) ;
                explosion_paint.setAlpha( explosion_color_alpha_value ) ;
                explosion_paint.setStyle( Paint.Style.FILL ) ;

                canvas.drawCircle( object_center_point.x, object_center_point.y,
                        bouncer_radius, explosion_paint ) ;

                explosion_color_alpha_value += 4 ; // decrease transparency
            }
        }
    }
}
