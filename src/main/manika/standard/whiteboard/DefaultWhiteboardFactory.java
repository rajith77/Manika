package manika.standard.whiteboard;

import manika.Whiteboard;
import manika.WhiteboardFactory;
import manika.WhiteboardType;

public class DefaultWhiteboardFactory implements WhiteboardFactory {

    public Whiteboard createWhiteBoard(WhiteboardType type) 
    {
        if (WhiteboardType.BasicBoard == type)
        {
            return new BasicBoard();
        }
        else
        {
            throw new IllegalArgumentException("Unsupported type " + type);
        }
    }

}
