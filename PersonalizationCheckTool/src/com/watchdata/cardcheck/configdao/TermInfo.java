package com.watchdata.cardcheck.configdao;

import com.watchdata.cardcheck.utils.Config;

public class TermInfo {
	String terminal_perform;

	public String getTerminal_perform() {
		return terminal_perform;
	}

	public void setTerminal_perform(String terminal_perform) {
		this.terminal_perform = terminal_perform;
	}

	public TermInfo getTermInfo(String sectionName) {
		TermInfo terminfo = new TermInfo();
		terminfo.setTerminal_perform(Config.getValue(sectionName, "terminal_perform"));

		return terminfo;
	}

}
