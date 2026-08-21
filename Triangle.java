import java.awt.*;
import java.lang.Math;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static int VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private char direction;
    
    
    


    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(int x, int y){
        height = 12;
        width = 12;
        this.xPosition = x;
        this.yPosition = y;
        color = "black";
        isVisible = false;
        direction = 'S';
    } 
    
    public char getDirection(){
        return direction;
    }
    
    
    public void setCoordinates(int xPosition,int yPosition)
    {
        erase();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        draw();
    } 
    
    public int[] getCoordinates()
    {
        
        return new int[]{xPosition, yPosition};
    }    
    
    /*
     * Change the orientation of the triangle
     */
    
    public void turn()
    {
        erase();
        switch (direction){
            case 'S': direction = 'W'; break;
            case 'W': direction = 'N'; break;
            case 'N' : direction = 'E'; break;
            case 'E' : direction = 'S'; break;
        }
        draw();
    }
    
        
    public int area(){
        return (height*width)/2;
        
    }
    
    public void equilateral(){
        int area = area();
        double s = Math.sqrt((4*area)/Math.sqrt(3));
        changeSize((int) (Math.sqrt(3.0)/2*s),(int)s);
        
    }
    
    
    public void walk(int times){
        int i;
        for (i=0;i<Math.abs(times);i++){
            if (times>0){
                moveRight();
                moveDown();
            }
            else if(times<0){
                moveLeft();
                moveDown();
            }    
        }
        }
    
        
    public Triangle(String color, int width, int height){
        this.color = color;
        this.width = width;
        this.height = height;
        xPosition = 140;
        yPosition = 15;
        isVisible = false;
    }    
    
    public double perimetro(){
        int base = width/2;
        double hipotenusa = Math.sqrt((base*base)+(height*height));
        double perimetro = width + hipotenusa*2;
        return perimetro;
    
    }
    
    
    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    
    /**
     * Creation of the method draw with the direction implemented
     */
    private void draw()
{
    if(isVisible){
        Canvas canvas = Canvas.getCanvas();
        int[] xpoints;
        int[] ypoints;
        switch (direction){
            case 'N':
                xpoints = new int[]{ xPosition, xPosition + width/2, xPosition - width/2 };
                ypoints = new int[]{ yPosition - height/2, yPosition + height/2, yPosition + height/2 };
                break;
            case 'S':
                xpoints = new int[]{ xPosition, xPosition + width/2, xPosition - width/2 };
                ypoints = new int[]{ yPosition + height/2, yPosition - height/2, yPosition - height/2 };
                break;
            case 'E':
                xpoints = new int[]{ xPosition + height/2, xPosition - height/2, xPosition - height/2 };
                ypoints = new int[]{ yPosition, yPosition - width/2, yPosition + width/2 };
                break;
            case 'W':
                xpoints = new int[]{ xPosition - height/2, xPosition + height/2, xPosition + height/2 };
                ypoints = new int[]{ yPosition, yPosition - width/2, yPosition + width/2 };
                break;
            default:
                xpoints = new int[]{ xPosition, xPosition + width/2, xPosition - width/2 };
                ypoints = new int[]{ yPosition - height/2, yPosition + height/2, yPosition + height/2 };
        };
        canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
        canvas.wait(10);
    }
}

    /*
     * Erase the triangle on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
