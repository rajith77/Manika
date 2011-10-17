package manika.standard.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
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

    private ToolBar toolBar;
    private JTabbedPane whiteboardPanel;
    private int index = 0;
    
    public MainPanel()
    {
        setLayout(new BorderLayout());
        add(new MenuPanel(this),BorderLayout.NORTH);
        whiteboardPanel = new JTabbedPane();
        whiteboardPanel.setBorder(null);
        whiteboardPanel.setPreferredSize(new Dimension(1000, 600));
        add(whiteboardPanel,BorderLayout.CENTER);

        //toolBar = new ToolBar();
        //toolBar.setPreferredSize(new Dimension(180,600));        
        //add(toolBar,BorderLayout.EAST); 
    }
    
    public void addWhiteboard(WhiteboardType boardType)
    {
       Whiteboard board = ManikaMain.getWhiteboardFactory().createWhiteBoard(boardType); 
       whiteboardPanel.addTab("board" + index++,
                              board.getImageIcon(),
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
