package io.github.oliviercailloux.y2018.assisted_board_games.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/***
 * 
 * @author Delmas Douo Bougna
 *
 */

@Entity
public class ChessMoveEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id_move;
	
	private String  squareFrom ;
	private String  squareTo ; 
	
	@ManyToOne
	private ChessStateEntity state;
	
	public ChessMoveEntity() {
	}
	public ChessMoveEntity(String from, String to) {
		this.squareFrom=from;
		this.squareTo=to;
	}
	
	public int getId_move() {
		return id_move;
	} 

	public String getFrom() {
		return squareFrom ;
	}
	
	public String getTo() {
		return squareTo ;
	}
	
	public void setFrom(String from) {
		this.squareFrom = from ;
	}
	
	public void setTo(String to) {
		this.squareTo = to ;
	}
	public void setState(ChessStateEntity state) {
		this.state=state;
	}

}
