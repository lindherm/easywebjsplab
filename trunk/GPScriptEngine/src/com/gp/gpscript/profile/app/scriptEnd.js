
    //7.  Send END PERSONALIZATION command to the card
    			endPersonalization(new ByteString("00",HEX));

	//8. replace the KDC
				//Update KDC with CDK
				//create AID,CSN in database
				//derive the CDK from CMK
				//write the CDK to card
		//		strATR=this.card.reset(1);
		//		this.securityDomain.openSecureChannel(0x00);
		//		replaceKDC(myKey,myObject);