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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import manika.DrawingArea.Mode;

public class ToolBox 
{
    private static Float[] eraserSize = 
        new Float[]{1f, 2f, 5f, 10f, 20f, 50f};
    
    private static Float[] brushSize = 
        new Float[]{1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f};
    
    private static Color[] brushColor =
        new Color[] {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.WHITE};
    
    private static String[] fonts =
        GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getAvailableFontFamilyNames();
    
    private static Integer[] fontSizes = new Integer[] 
         {10, 12, 14, 16, 18, 20, 22, 24, 30, 36, 48, 72};
    
    private ToolBox(){}
    
    
    public static JPanel getModeToolPanel(final DrawingArea drawingArea)
    {
        JPanel modePanel = new JPanel();
        modePanel.setLayout(null);
        modePanel.setPreferredSize(new Dimension(170,60));
        modePanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Mode",
                                            TitledBorder.CENTER,
                                            TitledBorder.CENTER));
        ButtonGroup modeSelector = new ButtonGroup();
        final JRadioButton drawMode = new JRadioButton("Draw",true);
        drawMode.setActionCommand("draw");
        final JRadioButton eraseMode = new JRadioButton("Erase",false);
        eraseMode.setActionCommand("erase");
        
        modeSelector.add(drawMode);
        modeSelector.add(eraseMode);
        drawMode.setBounds(5, 20, 70, 20);
        modePanel.add(drawMode);
        eraseMode.setBounds(80, 20, 70, 20);
        modePanel.add(eraseMode);
        
        ActionListener l = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if ("draw".equals(e.getActionCommand()))
                {
                    drawingArea.setMode(Mode.DRAW);
                }
                else
                {
                    drawingArea.setMode(Mode.ERASE);
                }
            }
            
        };
        drawMode.addActionListener(l);
        eraseMode.addActionListener(l);
        return modePanel;
    }
    
    public static JPanel getEraserToolPanel(final DrawingArea drawingArea)
    {
        JPanel eraserToolPanel = new JPanel();
        eraserToolPanel.setPreferredSize(new Dimension(170,130));
        eraserToolPanel.setLayout(null);
        eraserToolPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Eraser",
                                TitledBorder.CENTER,
                                TitledBorder.CENTER));
                
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setBounds(15, 30, 70, 20);
        eraserToolPanel.add(sizeLabel);
        
        final JComboBox eraserSizeList = new JComboBox(eraserSize);
        eraserSizeList.setSelectedItem(1f);
        eraserSizeList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                drawingArea.setEraserSize((Float)eraserSizeList.getSelectedItem());
            }
            
        });
        eraserSizeList.setBounds(60, 30, 70, 22);
        eraserToolPanel.add(eraserSizeList);

        
        Integer[] undoCount = new Integer[20];
        for (int i=0;i<undoCount.length;i++) { undoCount[i] = i+1; }
        final JComboBox undoCountList = new JComboBox(undoCount);
        undoCountList.setSelectedItem(1);
        
        JButton undoButton = new JButton("Undo last");
        undoButton.setBounds(15, 60, 90, 22);
        undoButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                drawingArea.undo((Integer)undoCountList.getSelectedItem());
            }
            
        });
        eraserToolPanel.add(undoButton);
        undoCountList.setBounds(108, 60, 50, 22);
        eraserToolPanel.add(undoCountList);
        
        final JButton clearBoard = new JButton("Clear Board");
        clearBoard.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                drawingArea.clear();
            }
            
        });
        clearBoard.setBounds(15, 95, 120, 22);
        eraserToolPanel.add(clearBoard);
        
        return eraserToolPanel;
    }
    public static JPanel getMarkerToolPanel(final DrawingArea drawingArea)
    {
        JPanel markerToolPanel = new JPanel();
        markerToolPanel.setPreferredSize(new Dimension(170,100));
        markerToolPanel.setLayout(null);
        markerToolPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Marker",
                                TitledBorder.CENTER,
                                TitledBorder.CENTER));
                
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setBounds(15, 30, 50, 20);
        markerToolPanel.add(sizeLabel);
        
        final JComboBox markerSizeList = new JComboBox(brushSize);
        markerSizeList.setSelectedItem(1f);
        markerSizeList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                drawingArea.setBrushSize((Float)markerSizeList.getSelectedItem());
            }
            
        });
        markerSizeList.setBounds(60, 30, 70, 22);
        markerToolPanel.add(markerSizeList);
                
        JLabel colorLabel = new JLabel("Color");
        colorLabel.setBounds(15, 60, 40, 20);
        markerToolPanel.add(colorLabel);
        
        final JComboBox brushColorList = new JComboBox(brushColor);
        brushColorList.setSelectedItem(Color.BLACK);
        brushColorList.setBackground(Color.BLACK);
        brushColorList.setRenderer(new ColorCellRenderer(brushColorList.getRenderer()));
        brushColorList.addActionListener(new ActionListener()        
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Color c = (Color)brushColorList.getSelectedItem();
                brushColorList.setBackground(c);
                drawingArea.setBrushColor(c);
            }
            
        });
        brushColorList.setBounds(60,60,70,22);
        markerToolPanel.add(brushColorList);
        
        return markerToolPanel;
    }
    
    public static JPanel getFontToolPanel(final DrawingArea drawingArea)
    {
        JPanel fontTool = new JPanel();
        fontTool.setLayout(null);
        fontTool.setPreferredSize(new Dimension(170,130));
        fontTool.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Font",
                                            TitledBorder.CENTER,
                                            TitledBorder.CENTER));
        
        final JComboBox fontList = new JComboBox(fonts);
        final JComboBox fontSizeList = new JComboBox(fontSizes);
        final JCheckBox bold = new JCheckBox("Bold");
        final JCheckBox italics = new JCheckBox("Italic");
        
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Font font = new Font((String)fontList.getSelectedItem(),
                                     getFontStyle(bold.isSelected(),italics.isSelected()), 
                                     (Integer)fontSizeList.getSelectedItem());
                drawingArea.setFont(font);
            }
            
        };
                
        fontList.setRenderer(new FontCellRenderer());
        fontList.addActionListener(listener);
        fontList.setSelectedItem("Monospaced");
        fontList.setBounds(15,30,120,22);
        fontTool.add(fontList);
        
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setBounds(15, 60, 30, 20);
        fontTool.add(sizeLabel);
        
        fontSizeList.addActionListener(listener);
        fontSizeList.setSelectedItem(16);
        fontSizeList.setBounds(55, 60, 70, 22);
        fontTool.add(fontSizeList);
        
        bold.addActionListener(listener);
        bold.setBounds(15, 90, 60, 20);
        fontTool.add(bold);
        
        italics.addActionListener(listener);
        italics.setBounds(75, 90, 70, 20);
        fontTool.add(italics);
        
        return fontTool;        
    }
    
    static int getFontStyle(boolean bold, boolean italics)
    {
        int i = Font.PLAIN;
        i = bold ? i + Font.BOLD : i;
        i = italics ? i + Font.ITALIC : i;
        return i;
    }
    
    static class ColorCellRenderer implements ListCellRenderer 
    {
       private final ListCellRenderer wrapped;
       
       public ColorCellRenderer(ListCellRenderer listCellRenderer) 
       {
         this.wrapped = listCellRenderer;
         System.out.println(wrapped);
       }
       
       public Component getListCellRendererComponent(JList list, Object value, 
                                                     int index, boolean isSelected, 
                                                     boolean cellHasFocus) 
       {
            Component renderer = 
                wrapped.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
            renderer.setBackground((Color)value);
            return renderer;
       }
    }
    
    static class FontCellRenderer extends DefaultListCellRenderer 
    {
        private final static Dimension preferredSize = new Dimension(90, 20);
        
        public FontCellRenderer() 
        {            
        }
        
        public Component getListCellRendererComponent(JList list, Object
               value, int index, boolean isSelected, boolean cellHasFocus) 
        {
           setText((String)value);
           setFont(new Font((String)value, Font.PLAIN, 14));
           setPreferredSize(preferredSize);
           return this;
        }
    }
}
