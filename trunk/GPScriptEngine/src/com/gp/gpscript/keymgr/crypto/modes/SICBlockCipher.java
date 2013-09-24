package com.gp.gpscript.keymgr.crypto.modes;

import com.gp.gpscript.keymgr.crypto.BlockCipher;
import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.DataLengthException;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithIV;

import java.math.BigInteger;

/**
 * Implements the Segmented Integer Counter (SIC) mode on top of a simple
 * block cipher.
 */
public class SICBlockCipher implements BlockCipher
{
  private BlockCipher     cipher = null;
  private int             blockSize;
  private byte[]          IV;
  private byte[]          counter;
  private byte[]          counterOut;
  private boolean         encrypting;

  private final BigInteger ONE = BigInteger.valueOf(1);


  /**
   * Basic constructor.
   *
   * @param c the block cipher to be used.
   */
  public SICBlockCipher(BlockCipher c) {
    this.cipher = c;
    this.blockSize = cipher.getBlockSize();
    this.IV = new byte[blockSize];
    this.counter = new byte[blockSize];
    this.counterOut = new byte[blockSize];
  }


  /**
   * return the underlying block cipher that we are wrapping.
   *
   * @return the underlying block cipher that we are wrapping.
   */
  public BlockCipher getUnderlyingCipher() {
    return cipher;
  }


  public void init(boolean forEncryption, CipherParameters params)
      throws IllegalArgumentException {
    this.encrypting = forEncryption;

    if (params instanceof ParametersWithIV) {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      byte[]           iv      = ivParam.getIV();
      System.arraycopy(iv, 0, IV, 0, IV.length);

      reset();
      cipher.init(true, ivParam.getParameters());
    }
  }


  public String getAlgorithmName() {
    return cipher.getAlgorithmName() + "/SIC";
  }


  public int getBlockSize() {
    return cipher.getBlockSize();
  }


  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
      throws DataLengthException, IllegalStateException {
    cipher.processBlock(counter, 0, counterOut, 0);

    //
    // XOR the counterOut with the plaintext producing the cipher text
    //
    for (int i = 0; i < counterOut.length; i++) {
      out[outOff + i] = (byte)(counterOut[i] ^ in[inOff + i]);
    }

    BigInteger bi = new BigInteger(counter);
    bi.add(ONE);
    counter = bi.toByteArray();

    return counter.length;
  }


  public void reset() {
    System.arraycopy(IV, 0, counter, 0, counter.length);
    cipher.reset();
  }
}
