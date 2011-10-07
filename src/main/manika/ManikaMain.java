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


public class ManikaMain extends JFrame
{
    private static final long serialVersionUID = -2752973810407272588L;

    public ManikaMain()
    {
      setTitle("White Board");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      BasicWhiteBoard board = new BasicWhiteBoard();
      setSize(new Dimension(1000,650));
      add(board);
      pack();
      setLocationRelativeTo(null);      
    }
    
    public static void main(String[] args)
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
