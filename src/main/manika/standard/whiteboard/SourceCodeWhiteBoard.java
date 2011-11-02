

package manika.standard.whiteboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import manika.DrawingArea;
import manika.Whiteboard;
import manika.standard.drawingarea.FreehandDrawingArea;
import manika.standard.gui.ToolPanel;
import manika.standard.toolbox.DefaultToolBox;
import manika.util.Utility;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class SourceCodeWhiteBoard extends JPanel implements Whiteboard, ComponentListener
{
    private static final long serialVersionUID = 6784951888674471647L;
    
    private JPanel codeArea;
    private FreehandDrawingArea drawingArea;
    private JLayeredPane layeredPane;
    final RSyntaxTextArea textArea = new RSyntaxTextArea();;
    private JPanel drawingTools;
    private JPanel fileTools;
    private ToolPanel toolPanel;
    
    public SourceCodeWhiteBoard()
    {
        setPreferredSize(new Dimension(950,600));
        setLayout(new BorderLayout());
        
        layeredPane = new JLayeredPane();
        codeArea = getCodeAreaPanel();
        codeArea.setBounds(0, 0, 760,650);
        layeredPane.add(codeArea,JLayeredPane.DEFAULT_LAYER);
                
        drawingArea =  new FreehandDrawingArea(); 
        drawingArea.setBounds(0, 0, 750,640);
        drawingArea.setOpaque(false);
        layeredPane.add(drawingArea,JLayeredPane.DEFAULT_LAYER);
        
        layeredPane.moveToFront(codeArea);
        layeredPane.setPreferredSize(new Dimension(800,700));
        
        add(layeredPane,BorderLayout.CENTER);
        
        fileTools = getFileTools();
        drawingTools = getDrawingTools();
        
        toolPanel = getToolPanel();
        toolPanel.addToolBox(fileTools);
        add(toolPanel,BorderLayout.EAST);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addComponentListener(this);
    }
    
    protected ToolPanel getToolPanel()
    {
        ToolPanel toolBar = new ToolPanel();
        toolBar.setPreferredSize(new Dimension(180,600));
        toolBar.addToolBox(getModeOptionsPanel());
        return toolBar;
    }
    
    @Override
    public String getDescription() 
    {
        return "SourceCode Whiteboard";
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
    
    public JPanel getCodeAreaPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        RTextScrollPane sp = new RTextScrollPane(textArea);
        panel.add(sp);
        return panel;
    }
        
    public JPanel getDrawingTools()
    {
        ToolPanel toolBar = new ToolPanel();
        toolBar.setPreferredSize(new Dimension(180,600));
        toolBar.addToolBox(DefaultToolBox.getModeToolPanel(drawingArea));
        toolBar.addToolBox(DefaultToolBox.getMarkerToolPanel(drawingArea));
        toolBar.addToolBox(DefaultToolBox.getEraserToolPanel(drawingArea));
        return toolBar;
    }
    
    public JPanel getFileTools()
    {
        DrawingArea drawingArea = new DrawingAreaAdapter()
        {
            @Override
            public void setFont(Font font) 
            {
                textArea.setFont(font);
            }            
        };
        
        ToolPanel toolBar = new ToolPanel();
        toolBar.setPreferredSize(new Dimension(180,600));
        toolBar.addToolBox(DefaultToolBox.getFontToolPanel(drawingArea));
        toolBar.addToolBox(getFilePanel());
        return toolBar;
    }
    
    public JPanel getModeOptionsPanel()
    {
        JPanel modePanel = new JPanel();
        modePanel.setLayout(null);
        modePanel.setPreferredSize(new Dimension(170,80));
        modePanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Mode",
                                            TitledBorder.CENTER,
                                            TitledBorder.CENTER));
        ButtonGroup modeSelector = new ButtonGroup();
        final JRadioButton edit = new JRadioButton("Edit",true);
        edit.setActionCommand("edit");
        final JRadioButton annotate = new JRadioButton("Annotate",false);
        annotate.setActionCommand("annotate");
        
        modeSelector.add(edit);
        modeSelector.add(annotate);
        edit.setBounds(5, 20, 100, 20);
        modePanel.add(edit);
        annotate.setBounds(5, 42, 120, 20);
        modePanel.add(annotate);
        
        ActionListener l = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if ("edit".equals(e.getActionCommand()))
                {
                    System.out.println("Edit selected");
                    textArea.setEditable(true);
                    layeredPane.moveToFront(codeArea);
                    toolPanel.remove(drawingTools);
                    toolPanel.add(fileTools);
                    toolPanel.repaint();
                }
                else
                {
                    System.out.println("Annotated selected");
                    textArea.setEditable(false);
                    layeredPane.moveToFront(drawingArea);
                    toolPanel.remove(fileTools);
                    toolPanel.add(drawingTools);
                    toolPanel.repaint();
                }
            }
            
        };
        edit.addActionListener(l);
        annotate.addActionListener(l);
        return modePanel;
    }
    
    public JPanel getFilePanel()
    {
        final JComboBox syntaxList = new JComboBox(RTextAreaHelper.getSupportedSyntaxStyles().toArray());
        
        JPanel filePanel = new JPanel();
        filePanel.setLayout(null);
        filePanel.setPreferredSize(new Dimension(170,100));
        filePanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File List",
                                            TitledBorder.CENTER,
                                            TitledBorder.CENTER));
        
        final JComboBox fileList = new JComboBox();
        fileList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                File file = (File)fileList.getSelectedItem();
                loadFile(file);
                String syntax = RTextAreaHelper.getSyntaxStyle(file);
                textArea.setSyntaxEditingStyle(syntax); 
                syntaxList.setSelectedItem(syntax);
            }
            
        });
        fileList.setBounds(5, 20, 100, 22);
        fileList.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object
                   value, int index, boolean isSelected, boolean cellHasFocus) 
            {
               if (value != null)
               {
                   setText(((File)value).getName());
               }
               return this;
            }
        });
        filePanel.add(fileList);
        
        JButton openButton = new JButton("File");
        openButton.setBounds(112, 20, 50, 22);
        openButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser fc = new JFileChooser();
                int retVal = fc.showOpenDialog(layeredPane);
                if (retVal == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fc.getSelectedFile();
                    fileList.addItem(file);
                    fileList.setSelectedItem(file);
                    String syntax = RTextAreaHelper.getSyntaxStyle(file);
                    textArea.setSyntaxEditingStyle(syntax); 
                    syntaxList.setSelectedItem(syntax);
                }
            }
            
        });
        filePanel.add(openButton);
        
        syntaxList.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                textArea.setSyntaxEditingStyle((String)syntaxList.getSelectedItem());
            }
            
        });
        syntaxList.setBounds(5, 45, 100, 22);
        syntaxList.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object
                   value, int index, boolean isSelected, boolean cellHasFocus) 
            {
               if (value != null)
               {
                   String s = (String)value;
                   setText(s.substring(s.indexOf("/")+1));
               }
               return this;
            }
        });
        filePanel.add(syntaxList);

        return filePanel;
    }
    
    public void loadFile(File file) 
    {

        System.out.println("DEBUG: " + file.getAbsolutePath());
        if (file.isDirectory()) 
        { 
           JOptionPane.showMessageDialog(this,
                 file.getAbsolutePath() + " is a directory",
                 "Error",
                 JOptionPane.ERROR_MESSAGE);
           return;
        }
        else if (!file.isFile()) 
        {
           JOptionPane.showMessageDialog(this,
                 "No such file: " + file.getAbsolutePath(),
                 "Error",
                 JOptionPane.ERROR_MESSAGE);
           return;
        }

        try 
        {
           BufferedReader r = new BufferedReader(new FileReader(file));
           textArea.read(r, null);
           r.close();
        } 
        catch (IOException ioe) 
        {
           ioe.printStackTrace();
           UIManager.getLookAndFeel().provideErrorFeedback(textArea);
        }

    }
    
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void componentMoved(ComponentEvent e) {}
    
    @Override
    public void componentShown(ComponentEvent e) 
    {
        
    }
    
    @Override
    public void componentResized(ComponentEvent e) 
    {
        Rectangle bounds = layeredPane.getBounds();
        codeArea.setBounds(bounds);
        drawingArea.setBounds(bounds.x,bounds.y,bounds.width-10,bounds.height-10);
        layeredPane.repaint();
        codeArea.repaint();
        drawingArea.repaint();
    }
}
