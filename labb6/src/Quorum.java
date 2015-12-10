import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
* Created by equadon on 2015-12-10.
*/
class Quorum extends HeavyPiece {

 final private int OSIZE = board.SIZE / 3;       // size of graphic representation, in pixels

   //--------------------------------------------------------------------
   // Quorum constructor
   //--------------------------------------------------------------------

   Quorum(boolean w, boolean my, Board.Position pos, Board board)
   {
       super(w,my,pos,board);      // construct a piece
       power =2;                   // with power 2
       extent = 3*OSIZE/4;
       }

   //---------------------------------------------------------------------
   // Appearance
   //---------------------------------------------------------------------

    Area appearance(Board.Position pos)                 // Appearance is two overlapping circles
       {Area app = new Area(new Ellipse2D.Float(pos.x-OSIZE/2,
                                   pos.y-OSIZE/2+OSIZE/4,
                                   OSIZE,
                                   OSIZE));
      app.add(new Area(new Ellipse2D.Float(pos.x-OSIZE/2,
                                  pos.y-OSIZE/2-OSIZE/4,
                                  OSIZE,
                                  OSIZE)));
        return app;}

    //---------------------------------------------------------------------
    // drawOutline (overrides Piece.drawOutline in order to draw an extra circle
    //---------------------------------------------------------------------

   @Override
   protected void drawOutline(Graphics page, Board.Position pos, Color color)
   { super.drawOutline(page,  pos,  color);
     page.drawOval(pos.x-OSIZE/2,
                   pos.y-OSIZE/2+OSIZE/4,
                                   OSIZE,
                                   OSIZE);
   }

}   // End class Quorum
