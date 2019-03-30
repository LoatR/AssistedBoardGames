package io.github.oliviercailloux.y2018.assisted_board_games.servlets;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import io.github.oliviercailloux.y2018.assisted_board_games.model.ChessGameEntity;
import io.github.oliviercailloux.y2018.assisted_board_games.model.ChessMoveEntity;
import io.github.oliviercailloux.y2018.assisted_board_games.service.ChessService;

@Path("/game")
public class GameServlet {
	private static final String INITIAL_GAME ="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"; 
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(GameServlet.class.getCanonicalName());

	@PersistenceContext
	private EntityManager em;

	@Inject
	private ChessService chessS;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String createGame() {
		LOGGER.info("Request GET on GameServlet : Adding a new game");
		ChessGameEntity game = new ChessGameEntity();
		chessS.persist(game);
		return String.valueOf(chessS.getLastGameId());
		
	}
	
	@GET
	@Path("getGame")
	@Produces(MediaType.TEXT_PLAIN)
	public String getGame(@QueryParam("game") int idGame) {
		LOGGER.info("Request GET on GameServlet : Returning game :" + idGame);
		ChessGameEntity game= chessS.getGame(idGame);
		List<ChessMoveEntity> moves = game.getMoves();
		Board b= playMoves(moves);
		return b.toString();
			
	}
	@GET 
	@Path("addMove")
	@Produces(MediaType.TEXT_PLAIN)
	public String addMove(@QueryParam("game") int idGame, @QueryParam("from") String from, @QueryParam("to") String to ) {
		LOGGER.info("Request GET on GameServlet : Adding a move to game :" + idGame + " with from = "+ from + " with to = "+ to);
		ChessGameEntity game= chessS.getGame(idGame);
		ChessMoveEntity move = new ChessMoveEntity(from, to);
		game.addMove(move);
		move.setGame(game);
		chessS.persist(move);
		List<ChessMoveEntity> moves = game.getMoves();
		Board b= playMoves(moves);
		return b.toString();
		
	}
	
	@GET
	@Path("getMove")
	@Produces(MediaType.TEXT_PLAIN)
	public String getGame() {
		LOGGER.info("Request GET on GameServlet : Returning moves :");
		List<ChessMoveEntity> moves = chessS.getAllMoves();
		return String.valueOf(moves.get(0).getId_move());
			
	}
	private Board playMoves(List<ChessMoveEntity> allMoves) {
		Board board = new Board();

		for (ChessMoveEntity move : allMoves) {
			board.doMove(new Move(Square.valueOf(move.getFrom()), Square.valueOf(move.getTo())));
		}
		return board;
	}
	
	
	
}
