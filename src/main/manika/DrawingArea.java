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

public interface DrawingArea 
{
    public enum Mode {DRAW , ERASE};
    
    public float getBrushSize();

    public void setBrushSize(float brushSize);

    public Color getBrushColor();

    public void setBrushColor(Color brushColor);
    
    public Font getFont();
    
    public void setFont(Font font);
    
    public float getEraserSize();
    
    public void setEraserSize(float size);
    
    public void undo(int events);
    
    public Mode getMode();
    
    public void setMode(Mode m);    
    
    public void clear();
}
