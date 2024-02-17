package Snakey.Model;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

/**
 * This is the class called SnakeObject,
 * providing the basic properties and functionalities common to the snake.
 * @link N/A
 * @author Qiao Lu
 * */
public abstract class SnakeObject
{
    private int m_x;
    private int m_y;
    private Image m_i;
    private int m_w;
    private int m_h;

    private boolean m_l;

    public int GetX() {
        return m_x;
    }

    public void SetX(int m_x) {
        this.m_x = m_x;
    }

    public int GetY() {
        return m_y;
    }

    public void SetY(int m_y) {this.m_y = m_y;}

    public Image GetI() {return this.m_i;}

    /**
     * This method is to set the new image for the object and update its dimensions.
     * @param m_i The new image to be set
     * @return N/A
     * @see SnakeObject#GetI()#SetW(int)#SetH(int)
     * @since Snakey Snakey 2.0 from Qiao Lu
     * @version M1-updated
     * @deprecated N/A
     */
    public void SetI(Image m_i) {
        this.m_i = m_i;
        if (m_i == null) {
            System.out.println("The image is null, cannot get width.");
        } else {
            int width = this.GetI().getWidth(null);
            int height = this.GetI().getHeight(null);
            this.SetW(width);
            this.SetH(height);
        }
    }

    public int GetW() {return m_w;}

    public void SetW(int m_i) {this.m_w = m_i;}

    public int GetH() {return m_h;}

    public void SetH(int m_i) {this.m_h = m_i;}

    public boolean IsL() {return m_l;}

    public void SetL(boolean m_l) {this.m_l = m_l;}

    public abstract void Draw(GraphicsContext gc);

    /**
     * This method is to use the object's current position (x, y)
     * and dimensions (width and height) to create a new Rectangle object.
     * @return A new Rectangle object represents the bounding box of the snake object
     * @since Snakey Snakey 2.0 from Qiao Lu
     * @version M1-updated
     * @deprecated N/A
     */
    public Rectangle GetRectangle()
    {
        return new Rectangle(m_x, m_y, m_w, m_h);
    }
}