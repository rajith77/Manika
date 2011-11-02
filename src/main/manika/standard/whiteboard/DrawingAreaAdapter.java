package manika.standard.whiteboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import manika.DrawingArea;

public class DrawingAreaAdapter implements DrawingArea 
{

    @Override
    public float getBrushSize() { return 0; }

    @Override
    public void setBrushSize(float brushSize) {}

    @Override
    public Color getBrushColor() { return null; }

    @Override
    public void setBrushColor(Color brushColor) {}
    
    @Override
    public Font getFont() { return null; }

    @Override
    public void setFont(Font font) {}

    @Override
    public float getEraserSize() { return 0; }

    @Override
    public void setEraserSize(float size) {}

    @Override
    public void undo(int events) {}
    
    @Override
    public Mode getMode() { return null; }

    @Override
    public void setMode(Mode m) {}
    
    @Override
    public void clear() {}
    
    @Override
    public void draw(Graphics2D g) {}

    @Override
    public Dimension getDimensions() { return null; }

}
