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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import manika.ManikaMain;
import manika.WhiteboardType;

public class MenuPanel extends JPanel implements ActionListener
{    
    private static final long serialVersionUID = 438762552476210677L;
    
    private MainPanel mainPanel;

    public MenuPanel(MainPanel panel)
    {
        mainPanel = panel;
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JButton newBut = new JButton("New");
        newBut.setActionCommand("new");
        newBut.setMnemonic(KeyEvent.VK_N);
        newBut.addActionListener(this);
        add(newBut);
        
        JButton openBut = new JButton("Open");
        add(openBut);
        
        JButton saveBut = new JButton("Save");
        saveBut.setActionCommand("save");
        saveBut.addActionListener(this);
        add(saveBut);
        
        JButton saveAllBut = new JButton("Save All");
        saveAllBut.setActionCommand("save-all");
        saveAllBut.addActionListener(this);
        add(saveAllBut);
        
        JButton exportBut = new JButton("Export");
        exportBut.setActionCommand("export");
        exportBut.addActionListener(this);
        add(exportBut);
        
        JButton exportAllBut = new JButton("Export All");
        exportAllBut.setActionCommand("export-all");
        exportAllBut.addActionListener(this);
        add(exportAllBut);
        
        JButton printBut = new JButton("Print");
        printBut.setActionCommand("print");
        printBut.addActionListener(this);
        add(printBut);
        
        JButton printAllBut = new JButton("Print All");
        printAllBut.setActionCommand("print-all");
        printAllBut.addActionListener(this);
        add(printAllBut);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if ("new".equals(e.getActionCommand()))
        {
            WhiteboardType type =  
                (WhiteboardType)JOptionPane.showInputDialog(mainPanel,
                                                            "Please select the whiteboard type",
                                                            "Whiteboard Types",
                                                            JOptionPane.INFORMATION_MESSAGE,
                                                            null,
                                                            WhiteboardType.values(), 
                                                            WhiteboardType.BasicBoard);
           
            if (type != null)
            {
                mainPanel.addWhiteboard(type);
            }
        }
        else if ("save".equals(e.getActionCommand()))
        {
            mainPanel.saveWhiteboard();
        }
        else if ("save-all".equals(e.getActionCommand()))
        {
            mainPanel.saveAllWhiteboards();
        }
        else if ("export".equals(e.getActionCommand()))
        {
            mainPanel.exportWhiteboard();
        }
        else if ("export-all".equals(e.getActionCommand()))
        {
            mainPanel.exportAllWhiteboards();
        }
    }
}
