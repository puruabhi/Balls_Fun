package com.example.android.balls_fun;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by abhishek on 05-02-2017.
 */

public class MovingBall {
    private static int WIDTH;
    private static int HEIGHT;
    private int x;
    private int y;
    private Bitmap ball;
    private float movingDirection;
    private int velocity;

    private int radius;

    //Constructors
    public MovingBall(Bitmap ball, int WIDTH, int HEIGHT){
        this.ball = ball;
        this.x = (int)(Math.random()*(double)WIDTH);
        this.y = (int)(Math.random()*(double)HEIGHT);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        radius = ball.getHeight()/2;
        movingDirection = (float)(Math.random()*Math.PI*2);
        this.velocity = 20;
    }

    public MovingBall(Bitmap ball, int x, int y, int WIDTH, int HEIGHT){
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        radius = ball.getHeight()/2;
        //System.out.println(WIDTH);
        //System.out.println(HEIGHT);
        movingDirection = (float)(Math.random()*Math.PI*2);
        velocity = 15;
    }

    public MovingBall(Bitmap ball, int x, int y, int WIDTH, int HEIGHT, float movingDirection){
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        radius = ball.getHeight()/2;
        this.movingDirection = movingDirection;
        velocity = 20;
    }

    public MovingBall(Bitmap ball, int x, int y, int WIDTH, int HEIGHT, int velocity){
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        radius = ball.getHeight()/2;
        movingDirection = (float)(Math.random()*Math.PI*2);
        this.velocity = velocity;
    }

    public MovingBall(Bitmap ball, int x, int y, int WIDTH, int HEIGHT, float movingDirection,int velocity){
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        radius = ball.getHeight()/2;
        this.movingDirection = movingDirection;
        this.velocity = velocity;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBall() {
        return ball;
    }

    public void setBall(Bitmap ball) {
        this.ball = ball;
    }

    public float getMovingDirection() {
        return movingDirection;
    }

    public void setMovingDirection(float movingDirection) {
        this.movingDirection = movingDirection;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getRadius() {
        return radius;
    }

    public void move(){
        x += velocity*Math.cos(movingDirection);
        y += velocity*Math.sin(movingDirection);

        System.out.println(x);
        System.out.println(y);

        if(y<=(ball.getHeight())/2) movingDirection = 2 * (float) Math.PI - movingDirection;
        if (x <= (ball.getWidth()/2)) {
            movingDirection = (float) Math.PI - movingDirection ;
            if(movingDirection<0)movingDirection+=2*(float)Math.PI;
        }
        if(y+(ball.getHeight())/2>=HEIGHT) movingDirection = 2 * (float) Math.PI - movingDirection;
        if (x+(ball.getWidth()/2)>=WIDTH){
            movingDirection = (float) Math.PI - movingDirection ;
            if(movingDirection<0)movingDirection+=2*(float)Math.PI;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(ball,x-(ball.getWidth()/2),y-(ball.getHeight()/2),null);
    }

}
