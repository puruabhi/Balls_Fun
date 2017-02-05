package com.example.android.bouncingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by abhishek on 04-02-2017.
 */

public class BouncingBallView extends View implements Runnable {
    Thread  thread_that_moves_the_ball ;

    boolean  thread_must_be_executed ;

    int view_width, view_height ;

    ExplodingBouncer ball_on_screen  ;

    public BouncingBallView( Context context )
    {
        super( context ) ;

        setBackgroundColor( 0xFFF0F8FF ) ; // AliceBlue, very light blue
    }

    public void onSizeChanged( int current_width_of_this_view,
                               int current_height_of_this_view,
                               int old_width_of_this_view,
                               int old_height_of_this_view )
    {
        view_width = current_width_of_this_view ;
        view_height = current_height_of_this_view ;

        RectF bouncing_area  =  new RectF( 0, 0, view_width, view_height ) ;

        ball_on_screen  =  new ExplodingBouncer( new PointF( view_width / 2,
                view_height / 2 ),
                Color.GREEN,
                bouncing_area) ;
    }


    public void start_animation_thread()
    {
        if ( thread_that_moves_the_ball  ==  null )
        {
            thread_must_be_executed     =  true ;
            thread_that_moves_the_ball  =  new  Thread( this ) ;

            thread_that_moves_the_ball.start() ;
        }
    }

    public void stop_animation_thread()
    {
        if ( thread_that_moves_the_ball  !=  null )
        {
            thread_must_be_executed  =  false ;
            thread_that_moves_the_ball.interrupt() ;

            thread_that_moves_the_ball  =  null ;
        }
    }

    @Override
    public void run()
    {
        while ( thread_must_be_executed  ==  true )
        {
            if ( ball_on_screen != null )
            {
                ball_on_screen.move() ;
            }

            postInvalidate() ;

            try
            {
                Thread.sleep( 40 ) ;
            }
            catch ( InterruptedException caught_exception )
            {
                // No actions to handle the exception.
            }
        }
    }



    public boolean onTouchEvent ( MotionEvent motion_event )
    {
        if ( motion_event.getAction() == MotionEvent.ACTION_DOWN )
        {
            // When the screen is touched we'll create a new ball if
            // the old ball has been exploded.

            if ( ball_on_screen.is_exploded() )
            {
                RectF bouncing_area  =  new RectF( 0, 0, view_width, view_height ) ;

                ball_on_screen =  new ExplodingBouncer( new PointF( view_width / 2,
                        view_height / 2 ),
                        Color.GREEN,
                        bouncing_area) ;
            }
            else if ( ball_on_screen.contains_point(
                    new PointF( motion_event.getX(),
                            motion_event.getY() ) ) )
            {
                ball_on_screen.explode_ball() ;
            }
        }

        return true ;
    }


    protected void onDraw( Canvas canvas )
    {
        if ( ball_on_screen != null )
        {
            ball_on_screen.draw( canvas ) ;
        }

    }
}
