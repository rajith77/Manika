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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class BasicWhiteBoard extends JPanel implements Whiteboard
{
    private static final long serialVersionUID = 6784951888674471647L;
    
    public BasicWhiteBoard()
    {
        setLayout(new BorderLayout());
        FreehandAndTextDrawingArea drawingArea = new FreehandAndTextDrawingArea();
        drawingArea.setPreferredSize(new Dimension(800, 600));
        add(drawingArea,BorderLayout.CENTER); 
        ToolBar toolBar = new ToolBar();
        toolBar.setPreferredSize(new Dimension(180,600));
        toolBar.addToolBox(ToolBox.getModeToolPanel(drawingArea));
        toolBar.addToolBox(ToolBox.getMarkerToolPanel(drawingArea));
        toolBar.addToolBox(ToolBox.getEraserToolPanel(drawingArea));
        toolBar.addToolBox(ToolBox.getFontToolPanel(drawingArea));
        add(toolBar,BorderLayout.EAST); 
    }

}
