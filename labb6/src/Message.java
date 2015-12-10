import java.io.Serializable;

/**
 * Created by equadon on 2015-12-10.
 */
class Message implements Serializable {
    enum MessageType {ACK, MOVE, BUILD, QUIT, EXPIRED, REMOVE_PIECE}

    ;   // The different types of messages

    MessageType messageType;      // type of message
    int time;             // time it is sent (seconds)
    int number;           // message sequence number
    Piece.Type pt;          // type of piece in a build
    Board.Position s;     // source position of a move
    Board.Position p;     // destination position of move, or position of build
    boolean white;        // color of sender (only relevant for recordings)
    String text;          // other info, such as reasons for quits

// The constructor just sets the fields from the parameters

    public Message(MessageType mt, int ti, int nu, Board.Position s, Board.Position p, Piece.Type pt, boolean w, String te) {
        messageType = mt;
        time = ti;
        number = nu;
        this.s = s;
        this.p = p;
        this.pt = pt;
        this.white = w;
        text = te;
    }
}
