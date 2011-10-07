/*    
 * Copyright 20011 Rajith Attapattu (rajith.attapattu@gmail.com)
 * This file is part of Manika.

 * Manika is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Manika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Manika.  If not, see <http://www.gnu.org/licenses/>.    
 */

package manika;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

public class FreehandDrawingArea extends JPanel implements DrawingArea, MouseListener, MouseMotionListener,  KeyListener, FocusListener
{
    private static final long serialVersionUID = -7769682533715108792L;
    
    protected Point start;
    protected Point end;
    protected float brushSize = 2f;    
    protected Color brushColor = Color.BLACK;
    protected Color BACKGROUND = Color.WHITE;
    protected Font font;
    protected Mode mode = Mode.DRAW;
    protected float eraserSize = 2f;
    protected Stack<Drawing> drawEventStack = new Stack<Drawing>();
    
    protected List<Line2D> lineBuf = new ArrayList<Line2D>(); 
    
    public FreehandDrawingArea()
    {
        this.setBackground(BACKGROUND);
        this.setPreferredSize(new Dimension(800, 600));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }    

    @Override
    public void mousePressed(MouseEvent e) 
    {
       start = new Point(e.getX(), e.getY());
       this.requestFocus();       
    }
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        Graphics2D g = (Graphics2D)this.getGraphics();
        end = new Point(e.getX(), e.getY());
        Line2D line = new Line2D.Double(start.x, start.y, end.x, end.y);
                
        if (mode == Mode.DRAW)
        {
            g.setStroke(new BasicStroke(getBrushSize()));
            g.setPaint(getBrushColor());            
        }
        else
        {
            g.setStroke(new BasicStroke(getEraserSize()));
            g.setPaint(BACKGROUND);
            
        }
        g.draw(line);
        lineBuf.add(line);
        start = end;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) 
    {
        System.out.println("Released ");
        drawEventStack.push(new LineDrawing(lineBuf,
                mode == Mode.DRAW? getBrushColor(): BACKGROUND,
                mode == Mode.DRAW? getBrushSize(): getEraserSize()        
               ));
        lineBuf = new ArrayList<Line2D>();
    }    
    
    public float getBrushSize() 
    {
        return brushSize;
    }

    public void setBrushSize(float brushSize) 
    {
        this.brushSize = brushSize;
        if (mode == Mode.DRAW)
        {
            this.setCursor(Utility.getColorSquaredMouseCursor(brushSize,brushColor));            
        }
    }

    public Color getBrushColor() 
    {
        return brushColor;
    }

    public void setBrushColor(Color brushColor) 
    {
        this.brushColor = brushColor;
        if (mode == Mode.DRAW)
        {
            this.setCursor(Utility.getColorSquaredMouseCursor(brushSize,brushColor));            
        }
    }
    
    public Font getFont()
    {
        return font;
    }
    
    public void setFont(Font font)
    {
        this.font = font;
    }
    
    @Override
    public void clear() 
    {
        Graphics2D g = (Graphics2D)getGraphics();
        Rectangle r = getBounds();
        g.setPaint(BACKGROUND);
        g.fillRect(r.x,r.y,r.width,r.height);
    }

    @Override
    public float getEraserSize() 
    {
        return eraserSize;
    }

    @Override
    public void setEraserSize(float size) 
    {
        eraserSize = size;
        if (mode == Mode.ERASE)
        {
            this.setCursor(Utility.getColorSquaredMouseCursor(eraserSize,Color.GRAY));            
        }
    }

    @Override
    public void undo(int events) 
    {
        for (int i=0; i< events; i++)
        {
            if (drawEventStack.size() > 0)
            {
                drawEventStack.pop();
            }
        } 
        this.repaint();
    }
    
    @Override
    public Mode getMode() 
    {
        return mode;
    }

    @Override
    public void setMode(Mode m) 
    {
        mode = m;
        if (mode == Mode.ERASE)
        {
            this.setCursor(Utility.getColorSquaredMouseCursor(eraserSize,Color.GRAY));
            
        }
        else
        {
            this.setCursor(Utility.getColorSquaredMouseCursor(brushSize,brushColor));
        }
    }
    
    interface Drawing
    {
        public void draw(Graphics2D g);
        public void erase(Graphics2D g);
    }
    
    class LineDrawing implements Drawing
    {
        List<Line2D> lines;
        Color color;
        float brushSize;
        
        LineDrawing(List<Line2D> l, Color c, float size)
        {
            lines = l;
            color = c;
            brushSize = size;
        }
        
        public void erase(Graphics2D g)
        {
            g.setPaint(BACKGROUND);
            g.setStroke(new BasicStroke(brushSize));
            for (Line2D line: lines)
            {
                g.draw(line);
            }
        }
        
        public void draw(Graphics2D g)
        {
            g.setPaint(color);
            g.setStroke(new BasicStroke(brushSize));
            for (Line2D line: lines)
            {
                g.draw(line);
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown()))
        {                 
            undo(1);
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (Drawing d : drawEventStack)
        {
            d.draw((Graphics2D)g);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e) {}    
}
