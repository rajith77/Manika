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
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class Utility 
{
    private Utility(){};

    
    public static Cursor getColorSquaredMouseCursor(float size,Color color)
    {
        int w = (int)size;
        int h = (int)size;
        int pix[] = new int[w * h];
        int index = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) 
            {
                pix[index++] = color.getRGB();
            }
        }
        
        Image img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w,h,pix,0,w));
        return Toolkit.getDefaultToolkit().createCustomCursor(img,new Point(0,0),"ColorSquare" + size + color);
    }
    
}
