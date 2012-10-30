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

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import manika.standard.gui.MainPanel;


public class ManikaMain extends JFrame
{
    private static final long serialVersionUID = -2752973810407272588L;

    private static WhiteboardFactory whiteboardFactory;
    
    public ManikaMain() throws Exception
    {
      whiteboardFactory = createWhiteboardFactory();  
      setTitle("White Board");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(new Dimension(1000,650));
      MainPanel p = new MainPanel();
      p.setPreferredSize(new Dimension(1000,800));
      add(p);
      pack();
      setLocationRelativeTo(null);
    }
    
    private WhiteboardFactory createWhiteboardFactory() throws Exception
    {
        @SuppressWarnings("unchecked")
        Class<WhiteboardFactory> c = 
            (Class<WhiteboardFactory>) Class
                .forName(System.getProperty("manika.whiteboard.factory",
                        "manika.standard.whiteboard.DefaultWhiteboardFactory"));
        
        return c.newInstance();
    }
    
    public static WhiteboardFactory getWhiteboardFactory()
    {
        return whiteboardFactory;
    }
    
    public static void main(String[] args) throws Exception
    {
        try 
        {
            //UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");           
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        ManikaMain wb = new ManikaMain();
        wb.setVisible(true);
    }
    
}
