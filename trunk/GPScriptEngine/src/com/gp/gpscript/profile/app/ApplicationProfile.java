package com.gp.gpscript.profile.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gp.gpscript.profile.Profile;
import com.gp.gpscript.profile.xPathNode;

//import com.watchdata.wdcams.loader.Loader;

/**
 * ApplicationProfile elements are the parent or root XML element containing an Application Profile for a smart card application. As the Application Profile represents a smart card application independent of a smart card, no card specific application instance information, such as whether the application is installed on a smart card or the current state of the application, is contained within ApplicationProfile XML. The specific application instance information is described in the Card Profile for the smart card or maintained by a SCMS in conjunction with the Card Profile.
 */
public class ApplicationProfile extends Profile {
	private Logger log = Logger.getLogger(ApplicationProfile.class);
	/**
	 * Application Provider OID + 5 byte value assigned by Application Provider. This unique ID field must be unique amongst all UniqueID attributes for the ApplicationProfile element for all Application Profiles supplied by the Application Provider who has been assigned the OID. UniqueID is variable length (up to 119 bytes). Once the ApplicationProfile has been changed by another actor, the UniqueID attribute must be updated and a new Revision element must be created. Example: 0x00010203040506070809 where 0x0001020304 represents the OID value of the Application Provider.
	 */
	public String UniqueID;
	/**
	 * Version of profiles specification conforming to GP versioning specification. Version number is x.x.x where each x is a decimal number. If the version of the specification has no last x value, then version number is x.x.0.Example: 1.0.0 1.0.1 1.1.0 1.1.2
	 */
	public String ProfileVersion;
	public apDescription Description;
	public apRevisions Revisions;
	public apConflictRules ConflictRules;
	public apApplicationInfo ApplicationInfo;
	public apCryptoEngine CryptoEngine;
	public apKey Key[];
	public apDataElement DataElement[];
	public apFunction Function[];
	public apSecureChannel SecureChannel;
	public apScriptFragment ScriptFragment[];
	private Element currElement;

	public ApplicationProfile(String xmlFile) {
		super(xmlFile);
		String xpString;
		NodeList nl;
		Node node;
		int i;
		node = document.getDocumentElement();
		currElement = (Element) node;

		try {
			NamedNodeMap map;
			map = node.getAttributes();
			UniqueID = map.getNamedItem("UniqueID").getNodeValue();
			ProfileVersion = map.getNamedItem("ProfileVersion").getNodeValue();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "Description";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0)
				Description = new apDescription(node);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Description " + e.getMessage());
		}

		try {
			xpString = "Revisions";
			nl = xPathNode.getNodeList(xpString, document);

			if (nl.getLength() > 0)
				Revisions = new apRevisions(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "ConflictRules";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0)
				ConflictRules = new apConflictRules(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Revisions " + e.getMessage());
		}

		try {
			xpString = "ApplicationInfo";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				ApplicationInfo = new apApplicationInfo(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ApplicationInfo " + e.getMessage());
		}

		try {
			xpString = "Key";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Key = new apKey[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					if (nl.getLength() > 0)
						Key[i] = new apKey(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Key " + e.getMessage());
		}

		try {
			xpString = "DataElement";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				DataElement = new apDataElement[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					DataElement[i] = new apDataElement(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("DataElement " + e.getMessage());
		}

		try {
			xpString = "Function";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Function = new apFunction[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Function[i] = new apFunction(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Function " + e.getMessage());
		}

		try {
			xpString = "SecureChannel";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				SecureChannel = new apSecureChannel(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("SecureChannel " + e.getMessage());
		}

		try {
			xpString = "ScriptFragment";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				ScriptFragment = new apScriptFragment[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					ScriptFragment[i] = new apScriptFragment(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ScriptFragment " + e.getMessage());
		}

	}

	public ApplicationProfile(String xmlFile, int flage) {
		// super(xmlFile);
		super(xmlFile, 0);
		String xpString;
		NodeList nl;
		Node node;
		int i;
		node = document.getDocumentElement();
		currElement = (Element) node;

		try {
			NamedNodeMap map;
			map = node.getAttributes();
			UniqueID = map.getNamedItem("UniqueID").getNodeValue();
			ProfileVersion = map.getNamedItem("ProfileVersion").getNodeValue();
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "Description";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0)
				Description = new apDescription(node);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Description " + e.getMessage());
		}

		try {
			xpString = "Revisions";
			nl = xPathNode.getNodeList(xpString, document);

			if (nl.getLength() > 0)
				Revisions = new apRevisions(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			xpString = "ConflictRules";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0)
				ConflictRules = new apConflictRules(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Revisions " + e.getMessage());
		}

		try {
			xpString = "ApplicationInfo";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0)
				ApplicationInfo = new apApplicationInfo(nl.item(0));
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ApplicationInfo " + e.getMessage());
		}

		try {
			xpString = "Key";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Key = new apKey[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					if (nl.getLength() > 0)
						Key[i] = new apKey(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Key " + e.getMessage());
		}

		try {
			xpString = "DataElement";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				DataElement = new apDataElement[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					DataElement[i] = new apDataElement(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("DataElement " + e.getMessage());
		}

		try {
			xpString = "Function";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				Function = new apFunction[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					Function[i] = new apFunction(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Function " + e.getMessage());
		}

		try {
			xpString = "SecureChannel";
			nl = xPathNode.getNodeList(xpString, node);
			if (nl.getLength() > 0) {
				SecureChannel = new apSecureChannel(nl.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("SecureChannel " + e.getMessage());
		}

		try {
			xpString = "ScriptFragment";
			nl = xPathNode.getNodeList(xpString, node);

			if (nl.getLength() > 0) {
				ScriptFragment = new apScriptFragment[nl.getLength()];
				for (i = 0; i < nl.getLength(); i++) {
					ScriptFragment[i] = new apScriptFragment(nl.item(i));
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("ScriptFragment " + e.getMessage());
		}

	}

	/**
	 * add DataElement into ApplicationProifle,attribute of DataElement is same with existent DataElement(0) Name and Length of DataElement is setted by user
	 * 
	 * @param name
	 *            Name of DataElement
	 * @param length
	 *            Length of DataElement
	 * @see apDataElement
	 */
	public void addDataElement(String name) {
		String xpath = "DataElement[@Name='dgi0000' ]";
		Node node = document.getDocumentElement();
		try {
			NodeList list = xPathNode.getNodeList(xpath, node);
			if (list.getLength() > 0) {
				Element dataElement = (Element) list.item(0).cloneNode(true);
				dataElement.setAttribute("Name", name);
				node.insertBefore(dataElement, list.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * Delete DataElement specified by name
	 * 
	 * @param name
	 *            attribute of DataElement
	 * @see apDataElement
	 */
	public void deleteDataElement(String name) {
		String xpath = "DataElement[@Name='" + name + "']";
		Node node = document.getDocumentElement();
		try {
			NodeList list = xPathNode.getNodeList(xpath, node);
			if (list.getLength() > 0) {
				node.removeChild(list.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	/**
	 * Add Key into root of ApplicationProfile
	 * 
	 * @param name
	 *            ,Name of Key
	 * @profileID ,ProfileID of Key
	 * @see apKey
	 */
	public void addKey(String name, String profileID) {
		String xpath = "Key";
		Node node = document.getDocumentElement();
		try {
			NodeList list = xPathNode.getNodeList(xpath, node);
			if (list.getLength() > 0) {
				Element key = (Element) list.item(0).cloneNode(true);
				key.setAttribute("Name", name);
				key.setAttribute("ProfileID", profileID);
				Node nextNode = list.item(list.getLength() - 1).getNextSibling();
				if (nextNode != null) {
					node.insertBefore(key, nextNode);
				} else {
					node.appendChild(key);
				}

			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * Delete a Key from root of ApplicationProfile the Key specifed by Name
	 * 
	 * @param name
	 *            Name of Key
	 * @see apKey
	 */
	public void deleteKey(String name) {
		String xpath = "Key[@Name='" + name + "']";
		Node node = document.getDocumentElement();
		try {
			NodeList list = xPathNode.getNodeList(xpath, node);
			if (list.getLength() > 0) {
				node.removeChild(list.item(0));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * accord with DGI[],automatically replace DataElement ScriptFragment/Declaration ScriptFragment/Script
	 * 
	 * @param dig
	 *            [] DGI of VSDC
	 */
	// ���dgi���飬�Զ��滻DataElemen,ScriptFragment/Declaration ScriptFragment/Script

	public void autoUpdate(String[] dgi) {
		// if(GP_Global.GP_PersonalizationMethod.equals("OLD"))
		if (false)
			autoUpdate_Old_PM(dgi);
		else
			autoUpdate_CP_PM(dgi);
	}

	public void autoUpdate_Old_PM(String[] dgi) {
		String appPath = System.getProperty("user.dir") + "\\Profiles\\";
		String scriptStart;
		String scriptEnd;
		int dgiTab;
		String script;
		String s;
		String appendRecord = "                       appendRecord(myObject['";
		String putData = "                       putData(myObject['";
		String putDESKey = "                       putDESKey(myKey,myObject['";
		String putRSAKey = "                       putRSAKey(myKey,myObject['";
		String changePin = "                       changePin(myKey,myObject['";

		String bracket = "']);\n";
		String endPersonalization = "endPersonalization(new ByteString(\"00\",HEX));\n";
		int personalizeScript = 0;
		for (int i = 0; i < ScriptFragment.length; i++) {
			if (ScriptFragment[i].Name.equals("PERSONALIZE")) {
				personalizeScript = i; // search personalize scriptFragment
				break;
			}
		}
		script = ScriptFragment[personalizeScript].Script.Script;

		scriptStart = script.substring(0, script.indexOf("//DGI-END"));
		scriptEnd = script.substring(scriptStart.length());

		script = scriptStart + "\n";
		boolean delInstallPSE = true;
		for (int i = 0; i < dgi.length; i++) {
			addDataElement(dgi[i]);
			ScriptFragment[personalizeScript].addDeclaration(dgi[i]);

			s = dgi[i].substring("dgi".length());
			dgiTab = Integer.parseInt(s, 16);

			/*
			 * DGI �ͺ������ϸ�Ķ�Ӧ��ϵ���� appendRecord��append record command�� 0101 0201 0202 0203 0204 0205 0206 0207 0208 0301 0302 0303 0401 0B01 (SFI11)
			 * 
			 * putData��put data command�� 0701 0702 0703 0D01 0E01 0E02 9102
			 * 
			 * putRSAKey��put key command�� 8101 8103 8102 8104
			 * 
			 * putDESKey��put key command�� 9103
			 * 
			 * changePin ��change/unblock pin command�� 1101
			 */
			switch (dgiTab) {

			case 0x0001:
			case 0x0002:
				delInstallPSE = false;
				break;
			case 0x0101:
			case 0x0201:
			case 0x0202:
			case 0x0203:
			case 0x0204:
			case 0x0205:
			case 0x0206:
			case 0x0207:
			case 0x0208:
			case 0x0301:
			case 0x0302:
			case 0x0303:
			case 0x0401:
			case 0x0B01:
				script = script + appendRecord + dgi[i] + bracket;
				break;
			case 0x0701:
			case 0x0702:
			case 0x0703:
			case 0x0D01:
			case 0x0E01:
			case 0x0E02:
			case 0x9102:
				script = script + putData + dgi[i] + bracket;
				break;
			case 0x8101:
			case 0x8103:
			case 0x8102:
			case 0x8104:
				script = script + putRSAKey + dgi[i] + bracket;
				break;
			case 0x8000:
				script = script + putDESKey + dgi[i] + bracket;
				break;
			case 0x1101:
				script = script + changePin + dgi[i] + bracket;
				break;
			default:
				// do nothing
			}

		}
		deleteDataElement("dgi0000"); // delete DataElement[0]
		ScriptFragment[personalizeScript].deleteDeclaration("dgi0000");// delete Declaration,and reserve Declaration[0]

		script = script + "                      " + scriptEnd;
		ScriptFragment[personalizeScript].Script.setNodeValue(script); // repleace Script
		if (delInstallPSE) {
			String xpath = "ScriptFragment[@Name= 'InstallPSE']";
			Node node = document.getDocumentElement();
			try {
				NodeList list = xPathNode.getNodeList(xpath, node);
				if (list.getLength() > 0) {
					node.removeChild(list.item(0));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error(e.getMessage());
			}

		}

	}

	public void autoUpdate_CP_PM(String[] dgi) {
		String appPath = System.getProperty("user.dir") + "\\Profiles\\";
		String scriptStart;
		String scriptEnd;
		int dgiTab;
		String script;
		String s;
		String storeData_Record = "              storeData_AppendRecord(myObject['";
		String storeData_Data = "              storeData_PutData(myObject['";
		String storeData_Encrypt = "              storeData_PutKeyPinChange(myObject['";

		String more_true = "'],true";
		String more_false = "'],false";
		String sw = "0x9000";
		String end = ");\n";
		String myKey = ",myKey";

		String endPersonalization = "endPersonalization(new ByteString(\"00\",HEX));\n";
		int personalizeScript = 0;
		for (int i = 0; i < ScriptFragment.length; i++) {
			if (ScriptFragment[i].Name.equals("PERSONALIZE")) {
				personalizeScript = i; // search personalize scriptFragment
				break;
			}
		}
		script = ScriptFragment[personalizeScript].Script.Script;

		scriptStart = script.substring(0, script.indexOf("//DGI-END"));
		scriptEnd = script.substring(scriptStart.length());

		script = scriptStart + "\n";
		boolean delInstallPSE = true;

		for (int i = 0; i < dgi.length; i++) {
			s = dgi[i].substring("dgi".length());
			dgiTab = Integer.parseInt(s, 16);
			if (dgiTab == 0x0702) {
				script = script + storeData_Data + dgi[i] + more_false + end;
				break;
			}
		}

		for (int i = 0; i < dgi.length; i++) {
			addDataElement(dgi[i]);
			ScriptFragment[personalizeScript].addDeclaration(dgi[i]);

			s = dgi[i].substring("dgi".length());
			dgiTab = Integer.parseInt(s, 16);

			if (i == dgi.length - 1) {
				s = more_true;
				end = ");\n\n";
			} else {
				s = more_false;
				end = ");\n";
			}

			switch (dgiTab) {
			case 0x0001:
			case 0x0002:
				delInstallPSE = false;
				break;
			case 0x0101:
			case 0x0201:
			case 0x0202:
			case 0x0203:
			case 0x0204:
			case 0x0205:
			case 0x0206:
			case 0x0207:
			case 0x0208:
			case 0x0301:
			case 0x0302:
			case 0x0303:
			case 0x0401:
			case 0x0B01:
				script = script + storeData_Record + dgi[i] + s + end;
				break;
			case 0x0702:
				break;
			case 0x0701:
			case 0x0703:
			case 0x0D01:
			case 0x0E01:
			case 0x0E02:
			case 0x9102:
				script = script + storeData_Data + dgi[i] + s + end;
				break;
			case 0x8101:
			case 0x8103:
			case 0x8102:
			case 0x8104:
			case 0x8201:
			case 0x8202:
			case 0x8203:
			case 0x8204:
			case 0x8205:
			case 0x8301:
			case 0x8302:
			case 0x8303:
			case 0x8304:
			case 0x8305:
			case 0x9103:
			case 0x1101:
				script = script + storeData_Encrypt + dgi[i] + s + myKey + end;
				break;
			default:
				break;
			}
		}

		deleteDataElement("dgi0000"); // delete DataElement[0]
		ScriptFragment[personalizeScript].deleteDeclaration("dgi0000");// delete Declaration,and reserve Declaration[0]

		script = script + "             " + scriptEnd;
		ScriptFragment[personalizeScript].Script.setNodeValue(script); // repleace Script

		if (delInstallPSE) {
			String xpath = "ScriptFragment[@Name= 'InstallPSE']";
			Node node = document.getDocumentElement();
			try {
				NodeList list = xPathNode.getNodeList(xpath, node);
				if (list.getLength() > 0) {
					node.removeChild(list.item(0));
				}
			} catch (Exception e) {
				// e.printStackTrace();
				log.error(e.getMessage());
			}
		}
	}

	public void changeUniqueID(String UID) {
		document.getDocumentElement().setAttribute("UniqueID", UID);
		UniqueID = UID;
	}

	/**
	 * update OpenSecureChannel script
	 * 
	 * @parent kmc =true select ../profiles/hasKMC.js =false select ../Profile/noKMC.js
	 */

	public void updateOSCScript(boolean kmc) {
		String appPath = System.getProperty("user.dir") + "\\Profiles\\";
		String hasKMC = "hasKMC.js";
		String noKMC = "noKMC.js";
		String fileName;
		String script;
		if (kmc) {
			fileName = appPath + hasKMC;
		} else {
			fileName = appPath + noKMC;
		}
		try {
			File fid = new File(fileName);
			FileInputStream dStream = new FileInputStream(fid);
			int byteLength = dStream.available(); // Get file byte number
			byte inBuffer[] = new byte[byteLength]; // carete input data buffer
			dStream.read(inBuffer);
			script = new String(inBuffer);

		} catch (IOException e) {
			// e.printStackTrace();
			log.error("Error Reading java script file" + e);
			return;
		}
		SecureChannel.OpenSecureChannel.Script.setNodeValue(script);
	}

	public static void main(String args[]) {
		ApplicationProfile ap = new ApplicationProfile(args[0]);
		// String script=ap.ScriptFragment[0].Script.Script;//
		// System.out.println(script);

		// ap.replaceScript("PERSOPREP","![CDATA[ //no script]]");
		// ap.addDataElement("dgi0801","20");

		// ap.deleteDataElement("dgi0801");
		// ap.addKey("KMC2","00112233440000000001");
		// ap.deleteKey("KMC2");

		/*
		 * System.out.println(ap.ApplicationInfo.Version); ap.ApplicationInfo.setAttribute("Version","1.2.1"); System.out.println(ap.ApplicationInfo.getAtrribute("Version")); System.out.println(ap.ApplicationInfo.getNodeValue()); System.out.println(ap.ApplicationInfo.getNodeName());
		 * 
		 * String data[]=new String[ap.DataElement.length]; for(i=0;i<ap.DataElement.length;i++) { data[i]="Name="+ap.DataElement[i].Name+"; Ext="+ap.DataElement[i].External+"; Encoding="+ap.DataElement[i].Encoding; System.out.println(data[i]); } //
		 */
		String[] data = new String[ap.ScriptFragment.length];

		for (int i = 0; i < ap.Function.length; i++) {
			// System.out.println(ap.Function[i].Script.Script);
		}
		for (int i = 0; i < ap.ScriptFragment.length; i++) {
			data[i] = "Name=" + ap.ScriptFragment[i].getAtrribute("Name") + " Script=" + ap.ScriptFragment[i].Script.getNodeValue();
			// String newScript="![CDATA[// script \n adpu.send(dgi0101)]]";
			// ap.ScriptFragment[i].Script.replaceScript(newScript);
			// ap.ScriptFragment[i].deleteDeclaration(ap.ScriptFragment[i].Declaration[0].Name);
			// ap.ScriptFragment[i].addDeclaration("dgi0101");
			// System.out.println(ap.ScriptFragment[i].Script.Script);
			// System.out.println(data[i]);
		}

		String outfile = "D:\\j2sdk14\\Tcps\\Profile\\profiles\\outputProfile.xml";
		String[] dgi = new String[16];
		for (int i = 0; i < 16; i++) {
			dgi[i] = "dgi0" + Integer.toHexString(i) + "01";

		}
		if (ap != null) {
			ap.autoUpdate(dgi);
			ap.changeUniqueID("1234567890abcdef");
			// ap.updateOSCScript(false);

			/*
			 * String des; if(ap.Description!=null) des=ap.Description.Description; // ap.setElement((Element)ap.document.getDocumentElement()); // System.out.println(ap.getAtrribute("ProfileVersion"));
			 * 
			 * // if(des!=null)System.out.println(ap.Description.Description);
			 */
			ap.saveProfile(outfile);
		}

	}

}