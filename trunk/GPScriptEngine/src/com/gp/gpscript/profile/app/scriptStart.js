// Constants used in this fragment
		MACONLY= 0x01;
		MACENC = 0x03;

		CLA    = 0x80;
		CLASEC = 0x84;

		INS_SelectApplication    = 0xA4;
		INS_Install    = 0xE6;

		INS_InitializeUpdate     = 0x50;
		INS_ExternalAuthenticate = 0x82;
		INS_AppendRecord         = 0xE2;
		INS_PutData    = 0xDA;
		INS_PutKey     = 0xD8;
		INS_ChangePin  = 0x24;

		INS_EndPersonalization    =  0xF0;

		P1 = 0x00;
		P2 = 0x00;

		myObject=this.data;
		myKey=this.key;

    //1.Place the PSK key into applet

         	    //GP Selecting VSDC Application
				this.securityDomain.select();

 	       		 //GP Initialize Update
//      			this.securityDomain.secureChannel.initializeUpdate(0,0);
      			//GP External Authenticate
//     			 this.securityDomain.secureChannel.externalAuthenticate(0);
				this.securityDomain.openSecureChannel(0x00);

      			//GP Put Key,to place the PSK key into applet
      			//version =00 (P1 bit6-0)
      			//newVersion= 0x00
      			//index = 0x00 (P2 bit6-0)
      			//type 0x81
     			//key
      			//kcv
      			checkValue=myKey.PSK.getKcv();
      			//this.securityDomain.putKey(0,1,1,0x81,myKey.PSK,checkValue);
      			this.putKey(0,1,1,0x81,myKey.PSK,checkValue);


    //2. VSDC Mutual Authenticate
      			// Selecting VSDC Application
	 			this.securityDomain.select();

 	        	//VSDC Initialize Update
 	        	hostChallenge = this.crypto.generateRandom(8);
      			cardChallenge = this.securityDomain.sendApdu(CLA, INS_InitializeUpdate, 0x00, 0x00, hostChallenge);
				challenge=cardChallenge.bytes(12,8).concat(hostChallenge);
	        	//VSDC External Authenticate
	        	comData=this.crypto.sign(myKey.PSK,this.crypto.DES_MAC,challenge);
				this.securityDomain.sendApdu(CLA, INS_ExternalAuthenticate, 0x00, 0x00, comData);