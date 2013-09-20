package com.gp.gpscript.profile;
import java.lang.*;
import java.io.*;
import javax.xml.parsers.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.awt.*;
import javax.swing.tree.*;

import com.gp.gpscript.profile.*;
//import com.watchdata.wdcams.loader.Loader;

import java.util.Vector;
/**
	Profile is xml file,class profile parse the xml file and create a DOM tree
	the DOM tree express all data of profile
*/


public class Profile  extends ProfileNode
{
	private Logger log = Logger.getLogger(Profile .class);
static final String JAXP_SCHEMA_LANGUAGE ="http://java.sun.com/xml/jaxp/properties/schemaLanguage";
static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
static final String JAXP_SCHEMA_SOURCE ="http://java.sun.com/xml/jaxp/properties/schemaSource";
/**
	DOM tree ,include all data and  data relation
*/
public Document document=null;

protected DocumentBuilder    domParser;
protected DocumentBuilderFactory factory;

public static final int SUCCESS=0;
public static final int WARNING=1;
public static final int ERROR=2;
public static final int FATALERROR=3;
/**
status of vaild parse profile process
	0,success
	1,warning
	2,error
	3,fatalError
*/
public int ErrorCode;
/**
	Constructor of Class Profile,
	@param xmlFile	 file path of Profile
*/
public Profile(String xmlFile)
{
	try{
			factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(true);
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

 			domParser = factory.newDocumentBuilder();
			ErrorChecker errors = new ErrorChecker();
			domParser.setErrorHandler(errors);
			document= domParser.parse( new File(xmlFile) );
			document.normalize();
			ErrorCode=errors.ErrorCode;
			Node node=document.getDocumentElement();

	}
	catch(IOException  e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}
	catch(SAXException s)
	{
		
//        s.printStackTrace();
		log.error(s.getMessage());
	}
	catch (IllegalArgumentException x)
				{
//                    x.printStackTrace();
				// Happens if the parser does not support JAXP 1.2
				//  ...
                    log.error("the parser does not support JAXP 1.2");
				}
	catch(ParserConfigurationException pe)
	{
//        pe.printStackTrace();
        log.error("parser:ParserConfigurationException"+pe);
	}
}

public Profile(String xmlFile,int flag)
{
	try{
			factory=DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(true);
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

 			domParser = factory.newDocumentBuilder();
			ErrorChecker errors = new ErrorChecker();
			domParser.setErrorHandler(errors);
			
//			document= domParser.parse( new File(xmlFile) );
			StringReader reader = new StringReader(xmlFile);
			InputSource inputSource = new InputSource(reader);
			document= domParser.parse(inputSource);
			reader.close();
			
			document.normalize();
			ErrorCode=errors.ErrorCode;
			Node node=document.getDocumentElement();

	}
	catch(IOException  e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}
	catch(SAXException s)
	{
//        s.printStackTrace();
		log.error(s.getMessage());
	}
	catch (IllegalArgumentException x)
				{
//                    x.printStackTrace();
				// Happens if the parser does not support JAXP 1.2
				//  ...
		log.error("the parser does not support JAXP 1.2");
				}
	catch(ParserConfigurationException pe)
	{
//        pe.printStackTrace();
		log.error("parser:ParserConfigurationException"+pe);
	}
}
/**
	save profile to xml file specifed by user
	@param file  xml file name
*/
public void saveProfile(String file)
{

    try
    {
        DOMSource doms = new DOMSource (document);

        File f = new File (file);

        StreamResult sr = new StreamResult (f);

        TransformerFactory tf=TransformerFactory.newInstance();
        Transformer t=tf.newTransformer ();

        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.METHOD,"xml");

        t.transform(doms,sr);
}
catch (TransformerConfigurationException tce)
{
//        tce.printStackTrace();
	log.error("Transformer Configuration Exception\n-----");
//    tce.printStackTrace();
}
catch (TransformerException te)
{
//    te.printStackTrace();
	log.error ("Transformer Exception\n---------");
//    te.printStackTrace ();
}

}

/**
	@param absPath ,select nodes by absolute path in DOM tree
	@return NodeList
*/
public NodeList getNodeList(String absPath)
{	NodeList list=null;
	String 	path=absPath.replace('.','/');
	try{
		list=xPathNode.getNodeList(path,document.getDocumentElement());
	}catch(Exception e)
	{
//        e.printStackTrace();
		log.error(e.getMessage());
	}
	return(list);
}
/**	get data in Dom tree by absolute path
	@param absPath ,absolute path in DOM tree,may be is attribute or element
					eg. absPath="ApplicationProfile.DataElement.Name"
	@return String[],all values specified by absPath
*/
public String[] getxPathValue(String absPath)
{
	Node node;
	NodeList list;
	String result[]=null;
	int i;
	try{
		node=document.getDocumentElement();

		String path=absPath.replace('.','/');
		String pathNodeName[]=new String[10];//path.
			   path="";
		for(i=0;i<pathNodeName.length-1;i++)
		{
			path=path+"/"+pathNodeName[i];
		}

		String	lastNodeName=pathNodeName[i];
		String attrPath=path+"[@"+lastNodeName+"]";	//assume that last node  is attribute

		list=xPathNode.getNodeList(attrPath,node);
		if(list.getLength()>0)
		{	 result=new String[list.getLength()];
			for( i=0;i<list.getLength();i++)
			{
				node=list.item(i);
				NamedNodeMap map=node.getAttributes();
				result[i]=map.getNamedItem(lastNodeName).getNodeValue();	//get value of attribute node

			}
		}
		else
		{
			String	elemPath=path+"/"+lastNodeName;	//assume that last node is element

			list=xPathNode.getNodeList(elemPath,node);
			if(list.getLength()>0)
			{
				result=new String[list.getLength()];
				for(i=0;i<list.getLength();i++)
				if(list.item(i).hasChildNodes())
				{
					result[i]=list.item(i).getFirstChild().getNodeValue(); //get value of element node
				}
			}
		}

		}catch(Exception e)
	{
//        e.printStackTrace();
			log.error(e.getMessage());
	}

return(result);

}

/**	Document  transform to TreeNode,
	@param parent  a DefaultMutableTreeNode in tree,but it is not root of Tree

	Example:

		  Profile profile=new Profile("app.xml");
		  TreePath path=jTree1.getSelectionPath();
          parent=(DefaultMutableTreeNode)path.getLastPathComponent();
          model=(DefaultTreeModel)jTree1.getSelectionModel();
          child=new DefaultMutableTreeNode("App");
          profile.toTreeNode(child);
          model.insertNodeInto(child,parent,parent.getChildCount());
*/
public void toTreeNode(DefaultMutableTreeNode parent) throws Exception
{
 		if(document!=null)
 		{
 			NodeList list=document.getChildNodes();
        	NodeListToTreeNode(list,parent);
    	}
}

/**	DOM NodeList transform to TreeNode
	@param list NodeList of DOM
	@param parent DefaultMutableTreeNode of Tree
*/

public void NodeListToTreeNode(NodeList nodelist,DefaultMutableTreeNode parent) throws Exception
{
/*	�õݹ鷽��ʵ��DOM�ĵ��ı���,����DOMת����DefaultMutableTreeNode
	NodeList ��DOM��ȫ����ڵ���б�,
	ʵ�ʴ������� ֻȡ��DOCUMENT_NODE �� ELEMENT_NOD������,TEXT_NODE��ֵ
	����GetElement����ʵ�����£�
*/
        Node		cnode;
        int		i,len;
        String	value;
        String attr;
        String attrName;
        String nodeName;
        Vector nodes=new Vector();
        Element element;
        int nodesElementIndex=1;
DefaultMutableTreeNode child,current,currentParent=parent;

        if(nodelist.getLength() == 0){
                // �ýڵ�û���ӽڵ�
                return;
        }
        for(i=0;i<nodelist.getLength();i++)
        {
                //���ȼ���Ƿ�����ͬ���ӽڵ�

                cnode = nodelist.item(i);
         	    if((cnode.getNodeType()==cnode.ELEMENT_NODE)||(cnode.getNodeType()==cnode.DOCUMENT_NODE))
                {
                	 nodeName=cnode.getNodeName();

                		NodeList nl=xPathNode.getNodeList(nodeName,cnode.getParentNode());
                	if(nl.getLength()<=1)
                	{	//ֻ��һ��

                	  	child=new DefaultMutableTreeNode(cnode.getNodeName());
               		 	parent.add(child);
                		current=child;
                		nString ns=new nString(cnode.getNodeName());
                		if(cnode.hasAttributes())
                  		{
                      		NamedNodeMap map=cnode.getAttributes();
                        	for(int j=0;j<map.getLength();j++)
                      		{
                       			ns.put((Object)map.item(j).getNodeName(),(Object)map.item(j).getNodeValue());
                      		}
                   		}
                   if(cnode.hasChildNodes())
                   {
                     if(cnode.getFirstChild().getNodeType()==cnode.CDATA_SECTION_NODE)
                     {
                            //child=new DefaultMutableTreeNode("CDATA="+cnode.getFirstChild().getNodeValue());
                           ns.put((Object)"CDATA",(Object)cnode.getFirstChild().getNodeValue());
                           // current.add(child);
                     }
                   }
                   child.setUserObject((Object)ns);

                	}
                	else
                	{	//�ж��
                		int index=nodes.indexOf((Object)nodeName);
                		if(index==-1)
                		{
                			//û����ͬ��Ԫ�أ�����Ӽ��ڵ�
                		nodes.addElement((Object)nodeName);

                		child=new DefaultMutableTreeNode(nodeName+"(s)");
               		 	parent.add(child);
                		currentParent=child;

                		element=(Element)cnode;
                		attr=nodeName=element.getAttribute("Name");
                		if(attr=="")
                		{
                			nodeName=attr.valueOf(nodesElementIndex);
                			nodesElementIndex++;
                		}
                		else
                		{
                			nodeName=attr;

                		}
                		child=new DefaultMutableTreeNode(nodeName);
               		 	currentParent.add(child);
               		 	current=child;
                		nString ns=new nString(nodeName);

                		if(cnode.hasAttributes())
                  		{
                      		NamedNodeMap map=cnode.getAttributes();
                        	for(int j=0;j<map.getLength();j++)
                      		{
                       			ns.put((Object)map.item(j).getNodeName(),(Object)map.item(j).getNodeValue());
                      		}
                   		}
                   if(cnode.hasChildNodes())
                   {
                     if(cnode.getFirstChild().getNodeType()==cnode.CDATA_SECTION_NODE)
                     {
                            //child=new DefaultMutableTreeNode("CDATA="+cnode.getFirstChild().getNodeValue());
                           ns.put((Object)"CDATA",(Object)cnode.getFirstChild().getNodeValue());
                           // current.add(child);
                     }
                   }
                   child.setUserObject((Object)ns);
					}
                	else
                	{
                		//����ͬ��,�ڼ��Ͻڵ������

                		element=(Element)cnode;
                		attr=nodeName=element.getAttribute("Name");
                		if(attr=="")
                		{
                			nodeName=attr.valueOf(nodesElementIndex);
                			nodesElementIndex++;
                		}
                		else
                		{
                			nodeName=attr;

                		}
                		child=new DefaultMutableTreeNode(nodeName);
               		 	currentParent.add(child);
               		 	current=child;
                		nString ns=new nString(nodeName);

                		if(cnode.hasAttributes())
                  		{
                      		NamedNodeMap map=cnode.getAttributes();
                        	for(int j=0;j<map.getLength();j++)
                      		{
                       			ns.put((Object)map.item(j).getNodeName(),(Object)map.item(j).getNodeValue());
                      		}
                   		}
                   if(cnode.hasChildNodes())
                   {
                     if(cnode.getFirstChild().getNodeType()==cnode.CDATA_SECTION_NODE)
                     {
                            //child=new DefaultMutableTreeNode("CDATA="+cnode.getFirstChild().getNodeValue());
                           ns.put((Object)"CDATA",(Object)cnode.getFirstChild().getNodeValue());
                           // current.add(child);
                     }
                   }
                   child.setUserObject((Object)ns);
                	}


                	}




                   NodeListToTreeNode(cnode.getChildNodes(),current);



           }
        }

}


}

