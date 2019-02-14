package io.github.oliviercailloux.y2018.assisted_board_games.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChessMoveEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id_move;
	
	private String  from ;
	private String  to ; 
	
	@ManyToOne
	private ChessStateEntity state;
	
	
	public int getId_move() {
		return id_move;
	} 

	public String getFrom() {
		return from ;
	}
	
	public String getTo() {
		return to ;
	}
	
	public void setFrom(String from) {
		this.from = from ;
	}
	
	public void setTo(String to) {
		this.to = to ;
	}

}
