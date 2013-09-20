package com.gp.gpscript.script;

public interface GPKeyCryptoEngine
{
   public int init(String p1, String p2);
   public int init(String p1, String p2,String p3);

   public void close();

   
   
   
   /**
    * mac
    * @param p1 the key used for mac
    * @param p2 mech-ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data 
    * @param p4 Initial vector
    * @param p5 pan
    * @return a new ByteString of the decrypted data
    */
   public NativeByteString macConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5);
   /**
    * Decrypt Perform a decryption operation
    * @param p1 the key used for decrypt
    * @param p2 mech-ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data to encrypt
    * @param p4 Initial vector
    * @param p5 pan
    * @return a new ByteString of the decrypted data
    */
   public NativeByteString decryptConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5);
   /**
    * encrypt
    * @param p1 key to use in the encrypt option
    * @param p2 mech=ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data to encrypt
    * @param p4 initial vector
    * @param p5 pan
    * @return a ByteString object containing the encrypted object
    */
   public NativeByteString encryptConnByPan(NativeByteString p1,Number p2,NativeByteString p3,NativeByteString p4,String p5);
   
  
   /**
    * deriveKey
    * @param p1 key to use in the encrypt option
    * @param p2 mech=ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data to encrypt
    * @param p4 initial vector
    * @param p5 pan
    * @return a ByteString object containing the encrypted object
    */
   public NativeByteString deriveKeyConnByPan(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4,String p5,NativeKey p6);
   /**
    * wrapKeyConnByPan
    * @param p1 key to use in the decrptEncrypt option
    * @param p2 mech=ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data to encrypt
    * @param p4 initial vector
    * @param p5 pan
    * @return a ByteString object containing the encrypted object
    */
   public NativeByteString wrapKeyConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5);  
   /**
    * decryptEncryptConnByPan
    * @param p1 key to use in the decrptEncrypt option
    * @param p2 mech=ABC_DES_ECB/ABC_DES_CBC
    * @param p3 data to encrypt
    * @param p4 initial vector
    * @param p5 pan
    * @return a ByteString object containing the encrypted object
    */
   public NativeByteString decryptEncryptConnByPan(NativeKey p1, Number p2, NativeByteString p3, Number p4,NativeByteString p5, NativeByteString p6, NativeByteString p7, String p8);   
   /**
    * connect
    * @param p1 pan
    * @return success:0
    */
   public NativeByteString connectByPan(String p1);
 
   /**
    * disconnByPan
    * @param p1 pan
    * @return success:0
    */
   public NativeByteString disconnByPan(String p1);
  
   
   
   
   
   public NativeByteString deriveKeyLmk(Number p1, Number p2, Number p3,NativeByteString p4,NativeByteString p5,NativeByteString p6);
   
   public NativeByteString decryptEncryptLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, Number p6, Number p7, Number p8, NativeByteString p9, NativeByteString p10, NativeByteString p11);

   public NativeByteString wrapToLmk(Number p1, Number p2, Number p3,NativeByteString p4,Number p5, Number p6,NativeByteString p7,NativeByteString p8);
   
   public NativeByteString edk(Number p1,NativeByteString p2,NativeByteString p3);
   
   public NativeByteString inputKey(NativeByteString p1,NativeByteString p2,NativeByteString p3);
   
   public NativeByteString getKey(NativeKey p1);

      /**
     * Decrypt Perform a decryption operation
     * @param p1 the key used for decrypt
     * @param p2 mech-DES_ECB/DES_CBC/RSA (RSA is not supplied now)
     * @param p3 data to encrypt
     * @param p4 Initial vector
     * @return a new ByteString of the decrypted data
     */
    public NativeByteString decrypt(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4);


    /**
     * decryptEncrypt Perform a decryption and encryption operation
     * @param p1 the key used for decrypt
     * @param p2 decrypt mech--DES_ECB/DES_CBC/RSA (RSA is not supplied now)
     * @param p3 the key used for encrypt
     * @param p4 decrypt mech--DES_ECB/DES_CBC/RSA (RSA is not supplied now)
     * @param p5 data to de/encrypt
     * @param p6 Initial vector for decrypt
     * @param p7 Initial vector for encrypt
     * @return a new ByteString of the result of decrypted and encrypted data
     */
    public NativeByteString decryptEncrypt(NativeKey p1,Number p2,NativeKey p3,Number p4,NativeByteString p5,NativeByteString p6,NativeByteString p7);

    /**
     * derive the key using the data and master key
     * @param p1 masterkey
     * @param p2 mech=DES_ECB/DES_CBC
     * @param p3 data for derive
     * @param p4 derivedKey
     */
    public void deriveKey(NativeKey p1,Number p2,NativeByteString p3,NativeKey p4);
    public void deriveOddKey(NativeKey p1,Number p2,NativeByteString p3,NativeKey p4);

    /**
     * create a digest of the data,using the algorithm specified by the mech parameter
     * @param p1 mech=SHA-1/MD5
     * @param p2 data
     * @return a ByteString containing the digest of data
     */
    public NativeByteString digest(Number p1,NativeByteString p2);

    /**
     * encrypt
     * @param p1 key to use in the encrypt option
     * @param p2 mech=DES_ECB/DES_CBC/RSA
     * @param p3 data to encrypt
     * @param p4 initial vector
     * @return a ByteString object containing the encrypted object
     */
    public NativeByteString encrypt(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4);

    /**
     * generateKey create a 8/16 bytes key
     * @param p1 mech=DES_KEY_GEN(8 bytes)/DES2_KEY_GEN(16 bytes)
     * @param p2 the key object containing the created key
     */
    public void generateKey(Number p1,NativeKey p2);
    /**
     * generateKeyPair create a public/private key pair
     * need update the database
     * @param p1 mech=RSA_KEY_PAIR_GEN
     * @param p2 public key
     * @param p3 private key
     */
    public void generateKeyPair(Number p1,NativeKey p2,NativeKey p3);

    /**
     * generateRandom
     * @param p1 the length of the random data
     * @return a bytestring  with length bytes of random data
     */
    public  NativeByteString generateRandom(Number p1);
    /**
     * sign
     * @param p1 signing key
     * @param p2 signing mech=DES_MAC(3DES)/RSA
     * @param p3 data
     * @return the signatrue of data
     */
    public NativeByteString sign(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4);

    public boolean verify(NativeKey p1,Number p2,NativeByteString p3,NativeByteString p4);
    /**
     * wrap to encrypt a key
     * @param p1 wrapKey
     * @param p2 wrapMech
     * @param p3 keyToWrap
     * @param p4 keyResult
     * @param p5 initial vector
     */
    public void wrap(NativeKey p1,Number p2,NativeKey p3,NativeKey p4,NativeByteString p5);

    /**
     * unwrap to decrypt a key using the key returned by getWrapKey() method
     * @param p1 unwrapMech
     * @param p2 keyToUnwrap
     * @param p3 keyResult
     * @param p4 initial vector
     */
    public void unwrap(Number p1,NativeKey p2,NativeKey p3,NativeByteString p4);

    /**
     * unwrapWrap decrypt keyToUnwrap using the key returned by getWrapKey() method
     * and wrap the result by wrapKey
     * @param p1 unWrapMech
     * @param p2 wrapKey
     * @param p3 wrapMech
     * @param p4 keyToUnwrap
     * @param p5 keyResult
     * @param p6 unwrapIV
     * @param p7 wrapIV
     */
    public void unwrapWrap(Number p1,NativeKey p2,Number p3,NativeKey p4,NativeKey p5,NativeByteString p6,NativeByteString p7);


}