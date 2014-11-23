package com.example.livewallpaper;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


public class Scene{

    
	private Bitmap background;
//	private Paint background;
	
    //For phone screen size
    private WindowManager wm;
    private Display display;
    private int screenWidth;
    private int screenHeight;
    
    //Used to get random location for snowflake at top of screen
    private Random rand = new Random();
    
    //count of snowflakes
    private int snowflakeCount = 10;
    
    //Snowflake locations (X and Y)
    private int[] snowflakesX = new int[snowflakeCount];
    private int[] snowflakesY = new int[snowflakeCount];
    private int[] snowflakeSpeed = new int[snowflakeCount];
    private int[] snowflakeDirection = new int[snowflakeCount];
    private Bitmap images; // = new Bitmap[snowflakeCount];
    
    //snowflake image height
    private int snowflakeHeight;
    
    //snowflake distance from another
    private int snowflakeDistance;
    
    
    public Scene() {
       

//    	background = new Paint();
//    	background.setColor(0xff8aa8a0);
        
        //getting phone screen size
        wm = (WindowManager) WallpaperActivity.c.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        
    	//getting background
    	background = BitmapFactory.decodeResource(WallpaperActivity.c.getResources(), R.drawable.background);
    	background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
        
        snowflakeHeight = (int) Math.sqrt((double) (screenWidth * screenHeight / 8));
        
        snowflakeDistance = (int) (screenHeight + 400) / snowflakeCount;
        
        //Initialize snowflake locations
        for(int i = 0; i < snowflakeCount; i++){
        	snowflakesX[i] = rand.nextInt(screenWidth) - (snowflakeHeight / 2);
        	snowflakesY[i] = snowflakeDistance * i;
        	snowflakeSpeed[i] = rand.nextInt(11) + 5;
        	snowflakeDirection[i] = rand.nextInt(7) - 3;
        }
        
        images = BitmapFactory.decodeResource(WallpaperActivity.c.getResources(), R.drawable.snowflake);
    	images = Bitmap.createScaledBitmap(images, snowflakeHeight, snowflakeHeight, true);
    }
    
    public synchronized void update() {
    	
    	 for(int i = 0; i < snowflakeCount; i++){
    		 if(snowflakesY[i] > screenHeight + snowflakeHeight) {
    			 snowflakesX[i] = rand.nextInt(screenWidth);
    			 snowflakesY[i] = 0 - snowflakeHeight;
    			 snowflakeSpeed[i] = rand.nextInt(11) + 5;
    		 }else{
    			 snowflakesY[i] += snowflakeSpeed[i];
    			 snowflakesX[i] += snowflakeDirection[i];
    		 }
    	 }
    	 
    }

    public synchronized void draw(Canvas canvas) {
    	
    	//setting background
    	canvas.drawBitmap(background, 0, 0, null);
//    	canvas.drawPaint(background);
    	
        //draw android
        for(int i = 0; i < snowflakeCount; i++){
           canvas.drawBitmap(images, snowflakesX[i], snowflakesY[i], null);
        }
    }

}
