package io.github.oliviercailloux.y2018.assistedboardgames.assisted_board_games;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.assistedboardgames.assisted_board_games.chess.ChessGame;
import io.github.oliviercailloux.y2018.assistedboardgames.assisted_board_games.chess.ChessState;
import junit.framework.TestCase;

public class ChessStateTest extends TestCase {
	
	@Test
	public void testEncodeJson() {
		ChessGame game = new ChessGame(true);
		ChessState state = new ChessState(1, game.get_board());
		
		JSONObject jsonStateTest = state.encodeJson();
		assertEquals(1, jsonStateTest.get("id"));
	}
	
	@Test
	public void testDecodeStateJson() {
		ChessGame game = new ChessGame(true);
		ChessState state1 = new ChessState(1, game.get_board());
		
		JSONObject jsonStateTest = state1.encodeJson();
		ChessState state2 = ChessState.decodeStateJson(jsonStateTest);
		
		assertEquals(state1.getIdState(), state2.getIdState());
		assertEquals(state1.get_board()[0][2], state2.get_board()[0][2]);
		assertEquals(state1.get_board()[7][4], state2.get_board()[7][4]);
		assertEquals(state1.get_board()[3][3], state2.get_board()[3][3]);
	}
}
