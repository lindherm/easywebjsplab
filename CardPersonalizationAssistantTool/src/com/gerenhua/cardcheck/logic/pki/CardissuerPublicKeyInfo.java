package com.gerenhua.cardcheck.logic.pki;

public class CardissuerPublicKeyInfo {
	private String head;
	private String certType;// 证书格式
	private String cid;// 发卡行标识
	private String expireDate;// 证书失效日期
	private String csid;// 证书序列号
	
	private String hashMech;// hash算法标识
	private String publicKeyMech;//发卡行公钥算法标识
	private String publicKeyLength;// 发卡行公钥长度
	private String publicExponentLength;// 发卡行公钥指数长度
	private String pubMod;// 公钥模值
	private String hash;// hash值
	private String rail;
	
	private boolean success = false;//是否恢复成功标识
	private String errorCode;//出错代码

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getRail() {
		return rail;
	}

	public void setRail(String rail) {
		this.rail = rail;
	}

	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPublicKeyMech() {
		return publicKeyMech;
	}

	public void setPublicKeyMech(String publicKeyMech) {
		this.publicKeyMech = publicKeyMech;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCsid() {
		return csid;
	}

	public void setCsid(String csid) {
		this.csid = csid;
	}

	public String getHashMech() {
		return hashMech;
	}

	public void setHashMech(String hashMech) {
		this.hashMech = hashMech;
	}

	public String getPublicKeyLength() {
		return publicKeyLength;
	}

	public void setPublicKeyLength(String publicKeyLength) {
		this.publicKeyLength = publicKeyLength;
	}

	public String getPublicExponentLength() {
		return publicExponentLength;
	}

	public void setPublicExponentLength(String publicExponentLength) {
		this.publicExponentLength = publicExponentLength;
	}

	public String getPubMod() {
		return pubMod;
	}

	public void setPubMod(String pubMod) {
		this.pubMod = pubMod;
	}

	@Override
	public String toString() {
		return "CardissuerPublicKeyInfo [head=" + head + ", certType=" + certType + ", cid=" + cid + ", expireDate=" + expireDate + ", csid=" + csid + ", hashMech=" + hashMech + ", publicKeyMech=" + publicKeyMech + ", publicKeyLength=" + publicKeyLength + ", publicExponentLength=" + publicExponentLength + ", pubMod=" + pubMod + ", hash=" + hash + ", rail=" + rail + "]";
	}

}
