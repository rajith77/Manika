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

package manika.standard.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import manika.DrawingArea;
import manika.ManikaMain;
import manika.Whiteboard;
import manika.WhiteboardType;
import manika.util.Utility;

public class MainPanel extends JPanel 
{
    private static final long serialVersionUID = -7292521568520803316L;

    private ToolPanel toolBar;
    private JTabbedPane whiteboardPanel;
    private int index = 0;
    private JLabel status = new JLabel();
    
    public MainPanel()
    {
        setLayout(new BorderLayout());
        add(new MenuPanel(this),BorderLayout.NORTH);
        whiteboardPanel = new JTabbedPane();
        whiteboardPanel.setBorder(null);
        whiteboardPanel.setPreferredSize(new Dimension(1025, 800));
        add(whiteboardPanel,BorderLayout.CENTER);

        
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createLineBorder(getBackground(), 10));
        footer.setPreferredSize(new Dimension(1000, 50));
        footer.add(status, BorderLayout.WEST);
        add(footer,BorderLayout.SOUTH);
        
        JPanel rightMargin = new JPanel();
        rightMargin.setPreferredSize(new Dimension(25, 800));
        add(rightMargin,BorderLayout.WEST);
        
        JPanel leftMargin = new JPanel();
        leftMargin.setPreferredSize(new Dimension(25, 800));
        add(leftMargin,BorderLayout.EAST); 
    }
    
    public void addWhiteboard(WhiteboardType boardType)
    {
       Whiteboard board = ManikaMain.getWhiteboardFactory().createWhiteBoard(boardType); 
       whiteboardPanel.addTab("board" + index++,
                              null, //board.getImageIcon(),
                              board.getDisplay(),
                              board.getDescription()
                              );
       int current = whiteboardPanel.getTabCount()-1;
       //whiteboardPanel.setTabComponentAt(current, new JTextField());
       whiteboardPanel.setSelectedIndex(current);       
    }
    
    
    public void saveWhiteboard()
    {
        JPanel panel = (JPanel)whiteboardPanel.getSelectedComponent();
        if (panel == null)
        {
            System.out.println("There is no whiteboard available");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(Utility.getManikaFileFilter());
        fileChooser.setSelectedFile(new File(whiteboardPanel.getTitleAt(whiteboardPanel.getSelectedIndex()) + ".manika" ));
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            file = Utility.fixExtension(file,"manika");
            whiteboardPanel.setTitleAt(whiteboardPanel.getSelectedIndex(), 
                                       file.getName().substring(0, file.getName().lastIndexOf('.')));
            writePanelToFile(panel,file);
                
        }
        status.setText("Saved " + whiteboardPanel.getTitleAt(whiteboardPanel.getSelectedIndex()));
    }
    
    public void saveAllWhiteboards()
    {
        JFileChooser fileChooser = new JFileChooser("Select directory to save files");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            for (int i=0; i<whiteboardPanel.getTabCount();i++)
            {
                JPanel panel = (JPanel)whiteboardPanel.getComponentAt(i);
                writePanelToFile(panel,new File(file,whiteboardPanel.getTitleAt(i)+ ".manika"));
            }
        }
        status.setText("Saved all whiteboard sessions to " + fileChooser.getSelectedFile());
    }
    
    public void writePanelToFile(JPanel panel, File file)
    {
        try 
        {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(panel);
            out.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Unable to save the whiteboard session");
            e.printStackTrace();
        }
    }
    
    public void exportWhiteboard()
    {
        Whiteboard board = (Whiteboard)whiteboardPanel.getSelectedComponent();
        if (board == null)
        {
            System.out.println("There is no whiteboard available");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(whiteboardPanel.getTitleAt(whiteboardPanel.getSelectedIndex()) + ".png" ));
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            exportDrawingAreaToPng(board.getDrawingArea(),file);
                
        }
        status.setText("Exported " + whiteboardPanel.getTitleAt(whiteboardPanel.getSelectedIndex()) + 
                " as " + fileChooser.getSelectedFile());
    }
    
    public void exportAllWhiteboards()
    {
        JFileChooser fileChooser = new JFileChooser("Select directory to export files");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fileChooser.getSelectedFile();
            for (int i=0; i<whiteboardPanel.getTabCount();i++)
            {
                Whiteboard board = (Whiteboard)whiteboardPanel.getComponentAt(i);
                exportDrawingAreaToPng(board.getDrawingArea(),new File(file,whiteboardPanel.getTitleAt(i)+ ".png"));
            }
        }
        status.setText("Exported all whiteboard sessions to " + fileChooser.getSelectedFile());
    }
    
    public void exportDrawingAreaToPng(DrawingArea drawingArea, File file)
    {
        try 
        {
            BufferedImage bi = new BufferedImage((int)drawingArea.getDimensions().getWidth(), 
                                                 (int)drawingArea.getDimensions().getHeight(), 
                                                 BufferedImage.TYPE_INT_ARGB); 
            Graphics2D g = bi.createGraphics();
            drawingArea.draw(g);
            g.dispose();
            ImageIO.write(bi,"png",Utility.fixExtension(file,"png"));
        } 
        catch (Exception e) 
        {
            System.out.println("Unable to export the whiteboard session");
            e.printStackTrace();
        }
    }
}
