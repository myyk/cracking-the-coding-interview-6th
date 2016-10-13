package com.github.myyk.cracking.chapter7;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Implement a NxN jigsaw puzzle and solver.
 *
 * This implementation is pretty incomplete because I feel like enough is there.
 *   I realize that I maybe should have avoided going into the display, but that
 *   is kind of how I might lay out the simple Jigsaw app, though maybe this should
 *   be a JigsawPuzzleApp that has a Jigsaw in it along with the other things. I could
 *   have done more about the edge and inner and outer, but figured, I wasn't like
 *   actually drawing these and it doesn't matter that much. It depends how close to
 *   simulating the logic to solve it I was going to do, but didn't do it so, didn't
 *   matter much in the end.
 */
public class JigsawPuzzle {
  
  private Piece[][] board;
  private Display display;
  private PieceGenerator generator = new PieceGenerator();
  private Solver solver = new Solver();
  private Set<Connection> knownConnections = new HashSet<Connection>();

  public JigsawPuzzle(int n, byte[] image) {
    board = new Piece[n][n];
    display = new Display(board, generator.generatePieces(image));
  }

  public class PieceGenerator {
    public List<Piece> generatePieces(byte[] image) {
      // generates Pieces from an image
      List<Piece> pieces = null;
      return shuffle(pieces);
    }

    public List<Piece> shuffle(List<Piece> pieces) {
      return null;
    }
  }

  public List<Piece> generatePieces(byte[] image) {
    // generates Pieces from an image
    List<Piece> pieces = null;
    return shuffle(pieces);
  }

  public List<Piece> shuffle(List<Piece> pieces) {
    return null;
  }

  public boolean fitsWith(PieceEdge a, PieceEdge b) {
    if (oppositeSide(a.side, b.side, true)) {
      if (a.side == Side.Top) {
        return a.piece.x == b.piece.x && a.piece.y == b.piece.y-1;
      } else if (a.side == Side.Bottom) {
        return a.piece.x == b.piece.x && a.piece.y-1 == b.piece.y;
      } else if (a.side == Side.Right) {
        return a.piece.x+1 == b.piece.x && a.piece.y == b.piece.y;
      } else {
        return a.piece.x == b.piece.x+1 && a.piece.y == b.piece.y;
      }
    } else {
      return false;
    }
  }

  private boolean oppositeSide(Side a, Side b, boolean andSwap) {
    return (a == Side.Top && b == Side.Bottom) ||
        (a == Side.Right && b == Side.Left) ||
        (andSwap ? oppositeSide(b, a, false) : false);
  }

  public void addConnection(PieceEdge a, PieceEdge b) {
    // add connection to knownConnections
  }

  public Display getDisplay() {
    return display;
  }

  public void setDisplay(Display display) {
    this.display = display;
  }

  public class Connection {
    Piece a;
    Piece b;
    Side aSide;  
  }

  public class PieceEdge {
    final Piece piece;
    final Side side;

    public PieceEdge(Piece piece, Side side) {
      super();
      this.piece = piece;
      this.side = side;
    }
  }

  public enum Side {
    Top, Bottom, Right, Left;
  }

  public class Piece {
    final Picture picture;
    /* Position of where it belongs in final image*/
    final int x;
    final int y;

    public PieceEdge top() {
      return new PieceEdge(this, Side.Top);
    }
    public PieceEdge bottom() {
      return new PieceEdge(this, Side.Bottom);
    }
    public PieceEdge right() {
      return new PieceEdge(this, Side.Right);
    }
    public PieceEdge left() {
      return new PieceEdge(this, Side.Left);
    }

    public Piece(Picture picture, int x, int y) {
      super();
      this.picture = picture;
      this.x = x;
      this.y = y;
    }
  }

  public class PieceView {
    final Piece piece;
    private int rotationDegrees;
    private int x;
    private int y;

    public PieceView(Piece piece, int rotationDegrees) {
      super();
      this.piece = piece;
      this.rotationDegrees = rotationDegrees;
    }
    public int getRotationDegrees() {
      return rotationDegrees;
    }
    public void setRotationDegrees(int rotationDegrees) {
      this.rotationDegrees = rotationDegrees;
    }
    public int getX() {
      return x;
    }
    public void setX(int x) {
      this.x = x;
    }
    public int getY() {
      return y;
    }
    public void setY(int y) {
      this.y = y;
    }
  }

  public class Picture {
    final byte[] image;

    public Picture(byte[] image) {
      super();
      this.image = image;
    }
  }

  public class Display {
    Piece[][] board;
    Set<PieceView> pieces;

    public Display(Piece[][] board, List<Piece> pieces) {
      this.board = board;
      this.pieces = createPieceViews(pieces);
    }

    private Set<PieceView> createPieceViews(List<Piece> pieces) {
      return null;
    }

    public void update() {
      // redraw the view
    }

    public void update(JigsawPuzzle puzzle) {
      // updates using data from the puzzle, moving pieces onto the board and out of pieces
      // if they have already been placed
    }
  }

  public class Solver {
    JigsawPuzzle puzzle;
    Set<Piece> unconnectedPieces;

    public boolean isSolved() {
      return unconnectedPieces.isEmpty();
    }

    public void solve() {
      // start with a piece

      while (!isSolved()) {
        // get an unconnected edge from the connected graph.
        // find the connection for that edge.

        //TODO remove break
        break;
      }
    }
  }
}
