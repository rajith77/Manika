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

package manika.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.ImageIcon;

public class Utility 
{
    private Utility(){};

    private final static FileNameExtensionFilter manikaFilter = new FileNameExtensionFilter("manika files","manika");
    
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
    
    public static ImageIcon createImageIcon(java.net.URL imgURL) 
    {
        return new ImageIcon(imgURL);
    }
    
    public static FileFilter getManikaFileFilter()
    {
        return manikaFilter;
    }
    
    public static File fixExtension(File file,String extension)
    {
        if (file.getAbsolutePath().endsWith(extension))
        {
            return file;
        }
        else
        {
            return new File(file.getParentFile(),file.getName() + "." + extension);
        }
    }
    
}
