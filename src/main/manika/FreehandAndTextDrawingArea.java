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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class FreehandAndTextDrawingArea extends FreehandDrawingArea
{    
    private static final long serialVersionUID = -5758598905361017134L;

    public enum DrawingMode { FREEHAND, TEXT}
    
    private Point textStartPoint;
    private Stack<CharachterDrawing> CharachterDrawingBuf = new Stack<CharachterDrawing>();
    private DrawingMode drawingMode = DrawingMode.FREEHAND;
        
    public FreehandAndTextDrawingArea()
    {
        super();
        this.addKeyListener(this);      
    }    

    @Override
    public void mousePressed(MouseEvent e) 
    {
       super.mousePressed(e);
       textStartPoint = start;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        super.mouseDragged(e);
        drawingMode = DrawingMode.FREEHAND;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if (drawingMode == DrawingMode.FREEHAND)
        {
            super.mouseReleased(e);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown()))
        {
            undo(1);
            return;
        }
        
        if (drawingMode == DrawingMode.FREEHAND)
        {
            drawingMode = DrawingMode.TEXT;
            textStartPoint = start;
            CharachterDrawingBuf = new Stack<CharachterDrawing>();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        {
            clearLastChar();
            System.out.println("Backspace pressed ");
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            // need to recalculate x,y
            System.out.println("Enter pressed");
            Graphics2D g = (Graphics2D)this.getGraphics();
            FontMetrics fm = g.getFontMetrics();
            int x = textStartPoint.x;
            int y = start.y + (fm.getHeight() + fm.getHeight()/2);
            start = new Point(x,y);   
            textStartPoint = start;
        }
        else if (isPrintableChar(e.getKeyChar()))
        {
            writeNextChar(e.getKeyChar());
        }
        else
        {
            return;
        }
    }
    
    private void clearLastChar()
    {
        if (!CharachterDrawingBuf.isEmpty())
        {
            Graphics2D g = (Graphics2D)getGraphics();
            CharachterDrawing lastCharachterDrawing = CharachterDrawingBuf.pop();
            drawEventStack.remove(lastCharachterDrawing);
            lastCharachterDrawing.erase(g);
            start = lastCharachterDrawing.getPoint();
        }
    }
    
    private void deleteChar(Graphics2D g, CharachterDrawing cp)
    {
        drawChar(g,cp.getChar(),cp.getPoint(),BACKGROUND,cp.getFont());        
    }
    
    private void writeNextChar(char c)
    {
        Graphics2D g = (Graphics2D)this.getGraphics();
        drawChar(g,c,start,getBrushColor(),getFont());
        CharachterDrawing cp = new CharachterDrawing(c,start,getFont(),getBrushColor());
        CharachterDrawingBuf.push(cp);
        drawEventStack.push(cp);
        start = new Point(start.x + g.getFontMetrics().stringWidth(String.valueOf(c)),start.y);        
    }
    
    private void drawChar(Graphics2D g, CharachterDrawing cp)
    {
        drawChar(g,cp.ch,cp.point,cp.color,cp.f);
    }
    
    private void drawChar(Graphics2D g, char ch, Point p, Color c, Font f)
    {
        g.setPaint(c);        
        g.setFont(f);
        g.drawString(String.valueOf(ch), p.x, p.y);        
    }
    
    private boolean isPrintableChar(char c) 
    {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }
    
    class CharachterDrawing implements Drawing
    {
        private Character ch;
        private Point point;
        private Font f;
        private Color color;
        
        public CharachterDrawing(Character ch, Point p, Font f, Color c) 
        {
            this.ch = ch;
            this.point = p;
            this.f = f;
            this.color = c;
        }

        public Character getChar() 
        {
            return ch;
        }

        public Point getPoint() 
        {
            return point;
        }
        
        public Font getFont()
        {
            return f;
        }
        
        public Color getColor()
        {
            return color;
        }
        
        public void erase(Graphics2D g)
        {
            deleteChar(g,this);
        }
        
        public void draw(Graphics2D g)
        {
            drawChar(g,this);
        }
    }    
}
