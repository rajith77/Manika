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

package manika.standard.whiteboard;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import manika.DrawingArea;
import manika.Whiteboard;
import manika.standard.drawingarea.FreehandAndTextDrawingArea;
import manika.standard.gui.ToolBar;
import manika.standard.toolbox.DefaultToolBox;
import manika.util.Utility;

public class BasicBoard extends JPanel implements Whiteboard
{
    private static final long serialVersionUID = 6784951888674471647L;
    
    private FreehandAndTextDrawingArea drawingArea;
    
    public BasicBoard()
    {
        setPreferredSize(new Dimension(1000,600));
        setLayout(new BorderLayout());
        drawingArea = new FreehandAndTextDrawingArea();
        drawingArea.setPreferredSize(new Dimension(800, 600));
        add(drawingArea);
        add(getToolBox(),BorderLayout.EAST);
    }
    
    protected JPanel getToolBox()
    {
        ToolBar toolBar = new ToolBar();
        toolBar.setPreferredSize(new Dimension(180,600));
        toolBar.addToolBox(DefaultToolBox.getModeToolPanel(drawingArea));
        toolBar.addToolBox(DefaultToolBox.getMarkerToolPanel(drawingArea));
        toolBar.addToolBox(DefaultToolBox.getEraserToolPanel(drawingArea));
        toolBar.addToolBox(DefaultToolBox.getFontToolPanel(drawingArea));
        return toolBar;
    }

    @Override
    public String getDescription() 
    {
        return "Basic Whiteboard";
    }

    @Override
    public ImageIcon getImageIcon() 
    {
        return Utility.createImageIcon(getClass().getResource("whiteboard-icon.png"));
    }

    @Override
    public JPanel getDisplay() 
    {
        return this;
    }

    @Override
    public DrawingArea getDrawingArea() 
    {
        return drawingArea;
    }

}
