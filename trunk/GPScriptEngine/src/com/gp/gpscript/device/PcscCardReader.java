package com.gp.gpscript.device;

import com.gp.gpscript.script.ApduChannel;
import com.watchdata.cardpcsc.CardPcsc;
import com.watchdata.util.DataUtil;

public class PcscCardReader implements ApduChannel {
	public CardPcsc cardPcsc;
	public PcscCardReader(String reader){
		cardPcsc=new CardPcsc();
		cardPcsc.connectReader(reader);
	}

	@Override
	public int init(String p1, String p2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] reset() {
		// TODO Auto-generated method stub
		return cardPcsc.resetCard();
	}

	@Override
	public byte[] sendApdu(int CLA, int INS, int P1, int P2, byte[] toSendData, int LE) {
		if (LE==-1) {
			LE=0x00;
		}
		// TODO Auto-generated method stub
		return DataUtil.hex2byte(cardPcsc.SendApdu(CLA, INS, P1, P2, toSendData, LE));
	}

	@Override
	public byte[] sendApdu(byte[] toSendData, int len) {
		// TODO Auto-generated method stub
		return cardPcsc.SendApdu(toSendData).getBytes();
	}

}
