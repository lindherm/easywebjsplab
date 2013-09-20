package com.gp.gpscript.script;

public interface ApduChannel
{
  public int init(String p1, String p2);
  public byte[] reset();
  public byte[] sendApdu(int CLA,
                         int INS,
                         int P1,
                         int P2,
                         byte[] toSendData,
                         int LE);
  public byte[] sendApdu(byte[] toSendData,
                                  int len);

}