package com.game.kalah;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.kalah.exception.KalahException;
import com.game.kalah.manager.KalahManager;
import com.game.kalah.manager.KalahManagerImpl;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Player;

@ManagedBean
@SessionScoped
public class KalahManagedBean {
	
	private static final Logger logger = LogManager.getLogger(KalahManagedBean.class);

	private static final int COUNT_OF_PITS = 6;

	private boolean started = false;

	/**
	 * spring jsf integration....
	 */
	@ManagedProperty("#{kalahManagerImpl}")
	private KalahManager kalahManager;

	private String player1Name;

	private String player2Name;

	private KalahBoard board; 
	
	public void startGame(ActionEvent actionEvent) {
		this.board = kalahManager.prepareBoard(this.player1Name, this.player2Name, COUNT_OF_PITS);
		this.started = true;
	}

	public void endGame(ActionEvent actionEvent) {
		this.kalahManager = new KalahManagerImpl();
		this.started = false;
	}

	public void pitClickAction(ActionEvent actionEvent) {
		logger.debug("pitclickaction..");
		String componentId = actionEvent.getComponent().getId();
		String[] ids = componentId.split("_");
		int pitId = new Integer(ids[1]);

		Player own = board.getNextPlayer();
				
		try {
			kalahManager.move(board,own,pitId);	
		} catch (KalahException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
					e.getMessage()));
		}
		
	}



	/**
	 * @return the player1Name
	 */
	public String getPlayer1Name() {
		return player1Name;
	}

	/**
	 * @param player1Name
	 *            the player1Name to set
	 */
	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	/**
	 * @return the player2Name
	 */
	public String getPlayer2Name() {
		return player2Name;
	}

	/**
	 * @param player2Name
	 *            the player2Name to set
	 */
	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public KalahBoard getKalahBoard() {
		return board;
	}

	/**
	 * @return the kalahManager
	 */
	public KalahManager getKalahManager() {
		return kalahManager;
	}

	/**
	 * @param kalahManager
	 *            the kalahManager to set
	 */
	public void setKalahManager(KalahManager kalahManager) {
		this.kalahManager = kalahManager;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	


}
