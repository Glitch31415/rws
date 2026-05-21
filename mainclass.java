package RWS;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.jsoup.*;

import gnu.io.NRSerialPort;

class stuff {
	public static String backendips = "";
	public static int modem = 1;
	public static String rwskey1 = "[rwskey1]";
	public static String rwskey2 = "[rwskey2]";
	public static String weatherkey;
	public static String weatherkey2;
	public static boolean intaccess = true;
	public static String welcomemessage = "";
	public static int totalconnections = 0;
	public static long interactiontimeout = 0;
	public static int cmdport = 8300;
	public static int dataport = 8301;
	public static int kissport = 8100;
	public static boolean ova = false;
	public static boolean inchat = false;
	public static int charlimit = 0;
	public static String homestring = "";
	public static String commandslist = "-----\nCommands:\n|home : Exits commands and reprints this page\n|website : Fetch text or raw html from a website\n|search : Quick text-only search\n|weather : Get weather forecast for given city+state\n|download : Download a given url through base64\n|forum : View or create threads in the forum folder on the github\n|chat : Send and receive direct messages with a callsign\n|info : Print server info\n|charlimit : Set maximum length of responses from the server (useful for slow connections)\r";
	public static String commandslistshort = "\nCommands: |home |website |search |weather |download |forum |chat |info |charlimit\r";
	static String backend(String option, String body) throws SocketException {
		String output = "";
		try {
		
		boolean success = false;
		int attempt = 0;
		while (success == false) {
			try {
				Socket socket = new Socket();
				socket.setTcpNoDelay(true);
				socket.setSoTimeout(5000);
			int port = new Random().nextInt(10)+1;
			
			InetAddress inetAddress=InetAddress.getByName(backendips);  
		      SocketAddress socketAddress=new InetSocketAddress(inetAddress, 5000+port);  
		      if(getstream4.debug==true){System.out.println(backendips + ":" + (5000+port));}
		      if(getstream4.debug==true){System.out.println(option);}
		      if(getstream4.debug==true){System.out.println(body.replaceAll(rwskey1, "[rwskey1]").replaceAll(rwskey2, "[rwskey2]"));}
		      socket.connect(socketAddress,(int) (1000+(Math.random()*4000))); // just in case of extremely high ping or extremely intense backend usage (load balancing)
		      
			
	        // get the input stream from the connected socket
	        InputStream inputStream = socket.getInputStream();
	        // create a DataInputStream so we can read data from it.
	        DataInputStream dataInputStream = new DataInputStream(inputStream);
	        OutputStream outputStream = socket.getOutputStream();
	        // create a data output stream from the output stream so we can send data through it
	        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
	        // read the message from the socket
	        dataOutputStream.writeUTF(stuff.rwskey1 + option + stuff.rwskey2 + body);
            dataOutputStream.flush(); // send the message
	        
	        output = dataInputStream.readUTF();
	        dataOutputStream.close();
	        dataInputStream.close();
	        success = true;
	        socket.close();
	        if(getstream4.debug==true){System.out.println("success");}
			} catch (Exception e) {
				//if(getstream4.debug==true){System.out.println(e.toString() + "\nbackend down or busy, refetching backendips, waiting, and retrying...");}
				attempt = attempt + 1;
				if(getstream4.debug==true){e.printStackTrace();System.out.println("\nbackend down or busy, refetching backendips, waiting, and retrying with randomized port, attempt " + attempt + "...");}
				
				
				
				try {
					stuff.backendips = Jsoup.parse(new URI("https://raw.githubusercontent.com/Glitch31415/rws/refs/heads/main/backendips").toURL(), 10000).wholeText().replaceAll("\n", "").replaceAll("\r", "");
					//Thread.sleep((long) (Math.random()*500));
				} catch (Exception e1) {
					if(getstream4.debug==true){e1.printStackTrace();}
				}
			}
			
		}
		
		output = output.replaceAll(stuff.rwskey1, "");
        
		
		} catch (Exception e) {
			if(getstream4.debug == true) {e.printStackTrace();}
		}
		return output;
	}
	static void updusabled() throws IOException, InterruptedException, XmlRpcException {

		if (getstream3.termin != "" && getstream3.termin != null) {
			if (getstream4.conn == true && getstream4.termconnect == false) {
				System.out.println("You can't interact with the server locally right now, there is someone who is connected to it externally.");
				getstream3.termin = "";
			}
			else {


					if (getstream3.termin.contains("|disc")) {
						//if (getstream4.termconnect == true) { // commented, sending |disc remotely is now allowed as a last resort in case something breaks spectacularly and it doesn't instantly disconnect you
						stuff.disconnect();	
						getstream4.termconnect = false;
							getstream4.conn = false;
							
							System.out.println("Logs:\n-----\n" + getstream4.logs + "\n-----");
							getstream4.option = 0;
							stuff.charlimit = 0;
							getstream4.dataoutp = "";
							getstream4.cmdsoutp = "CLEANTXBUFFER\r";
							getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
							if (stuff.modem == 1) {
								getstream4.cmdsout.write(getstream4.cmdsdata);
							}
							getstream4.conn = false;
							getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
							getstream4.option = 0;
							getstream4.dataoutp = "";
							getstream4.cmdsoutp = "CLEANTXBUFFER\r";
							getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
							if (stuff.modem == 1) {
								getstream4.cmdsout.write(getstream4.cmdsdata);
							}
							getstream4.bwcheck = true; getstream1.dkill = false;
						//}

						getstream3.termin = "";
						

					}
					else {
						if (getstream4.conn == false) {
							getstream4.conn = true;
							getstream4.rcall = getstream4.callsign.replaceAll("[^a-zA-Z0-9 -]", "").toUpperCase();
							getstream4.connmsg = true;
							stuff.connectinit(true);
						}
						getstream4.termconnect = true;
						getstream4.curbuf = 0;
						if (getstream4.usabled != "") {
							getstream4.usabled = getstream4.usabled + "\n" + getstream3.termin;
						}
						else {
							getstream4.usabled = getstream3.termin;
						}
						getstream3.termin = "";
					}
					
			}
			
		}
		if (getstream4.termconnect == false && stuff.modem == 1) {
			if (getstream4.prevdatainthing == getstream2.gdatain) {
				//getstream4.usabled = "";
			}
			else {
				if (getstream2.gdatain.contains("&IT&") || getstream2.gdatain.contains(getstream4.rcall + " <R") ) {
					//getstream4.usabled = "";
				}
				else {
					if (getstream4.usabled != "") {
						getstream4.usabled = getstream4.usabled + "\n" + getstream2.gdatain;
					}
					else {
						getstream4.usabled = getstream2.gdatain;
					}
					getstream4.prevdatainthing = getstream2.gdatain;

				}

			}
		}
		if (getstream4.usabled != "" && getstream4.debug == true) {
			System.out.println("usabled: '" + getstream4.usabled + "'");
		}

	}
	static void modemconfig() throws InterruptedException, XmlRpcException, URISyntaxException, UnknownHostException, IOException {

		if (stuff.modem == 1) {
			if (ova == true) {
				try {
					File varaloc = null;
					if (File.separator.contains("/")) {
						varaloc = new File(System.getProperty("user.home") + "/.wine/drive_c/VARA/VARA.exe");
					}
					else {
						varaloc = new File("C:" + File.separator + "VARA" + File.separator + "VARA.exe");
					}
					Desktop.getDesktop().open(varaloc);
					Thread.sleep(7500);
					} catch (Exception e) { System.out.println("Failed to automatically open VARA. If it's not already open, the server will error now.");if(getstream4.debug==true){e.printStackTrace();} }
			}
    		try {
			getstream1.cmds = new Socket("127.0.0.1", cmdport);
			getstream2.data = new Socket("127.0.0.1", dataport);
			getstream4.cmdsout = getstream1.cmds.getOutputStream();
			getstream4.dataout = getstream2.data.getOutputStream();
    		} catch (Exception e) { System.out.println("Failed to connect to VARA. Make sure to start VARA before starting the server, and make sure your port settings are correct.");if(getstream4.debug==true){e.printStackTrace();}System.exit(0); }
			Thread object = new Thread(new getstream1());
			object.start();
			Thread object2 = new Thread(new getstream2());
			object2.start();
		}
		if (stuff.modem == 2) {
			// freedata stuff
			//try {
			//freedatags.fcmds = new Socket("127.0.0.1", 9000);
			//freedatags.fdata = new Socket("127.0.0.1", 9001);
			//freedatags.fcmdsout = freedatags.fcmds.getOutputStream();
			//freedatags.fcmdsin = freedatags.fcmds.getInputStream();
			//freedatags.fdataout = freedatags.fdata.getOutputStream();
			//freedatags.fdatain = freedatags.fdata.getInputStream();
			//} catch (Exception e) { System.out.println("Failed to connect to FreeDATA. Make sure the FreeDATA server is started *and the web client is closed* before starting RWS.");if(getstream4.debug==true){e.printStackTrace();}System.exit(0); }
			//Thread object = new Thread(new freedatags());
			//object.start();
			//Thread.sleep(1000);
			//freedatags.out.
		}
		if (stuff.modem == 3) {
			// fldigi stuff
			System.out.println("Attempting to connect to fldigi's xmlrpc server at 127.0.0.1:7362");
			fldigihandler.config = new XmlRpcClientConfigImpl();
			fldigihandler.config.setServerURL(new URI("http://127.0.0.1:7362/RPC2").toURL());
			fldigihandler.client = new XmlRpcClient();
			fldigihandler.client.setConfig((XmlRpcClientConfig)fldigihandler.config);
		    fldigihandler.client.execute("text.clear_rx", new Object[0]);
			Thread object = new Thread(new fldigihandler());
			object.start();
		}
		if (stuff.modem == 4) {
			// serial stuff
			System.out.println("Attempting to initialize serial at port " + serialhandler.serialport + " at " + serialhandler.serialbaud + " baud");
			serialhandler.serial = new NRSerialPort(serialhandler.serialport, serialhandler.serialbaud);
			Thread object = new Thread(new serialhandler());
			object.start();
		}
		if (stuff.modem == 5) {
			// tcp stuff
			System.out.println("Attempting to initialize TCP at port 127.0.0.1:" + tcphandler.tcpport);
			tcphandler.tcpsocket = new ServerSocket();
			InetSocketAddress socketAddress = new InetSocketAddress(tcphandler.tcpport);  
		    tcphandler.tcpsocket.bind(socketAddress); 
		    tcphandler.tcpsocket.setSoTimeout(0);
			Thread object = new Thread(new tcphandler());
			object.start();
		}

	}
	static void connectinit(boolean isconnect) throws InterruptedException, IOException, XmlRpcException {
		getstream4.rcall = getstream4.rcall.replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").toUpperCase();
		if (getstream4.rcall.contains("-")) {
			getstream4.rcall = getstream4.rcall.substring(0, getstream4.rcall.indexOf("-"));
		}
		if (getstream4.rcall != "") {
			
		getstream4.option = 0;
		String dummyload = "";
		getstream4.encodedString = "";
        try {

				dummyload = Jsoup.parse(new URI("https://www.google.com").toURL(), 10000).wholeText();

        }
        catch (Exception e) {
            e.printStackTrace();
           dummyload = e.toString();
           intaccess = false;
        }
        if (dummyload.contains("Privacy")) {
        	intaccess = true;
        }
		getstream4.conn = true;
		getstream4.connmsg = true;
		
		
		int ind = 0;
		boolean fc = false;
        while (ind < getstream4.bannedcalls.size()) {
        	if(getstream4.debug==true){System.out.println("loopingbc2");}
        	if (getstream4.bannedcalls.get(ind).contains(getstream4.rcall)) {
        		fc = true;
        		break;
        	}
        	ind = ind + 1;
        }
		if (fc == false) { // banned calls
			String notifs = stuff.backend("cn", getstream4.rcall);
			if (welcomemessage != "") {
				getstream4.encodedString = "\n-----\nWelcome, " + getstream4.rcall + "\n" + welcomemessage + notifs;
			}
			else {
				getstream4.encodedString = "\n-----\nWelcome, " + getstream4.rcall + notifs;
			}
		
			if (intaccess == true) {
				Thread.sleep(1000);
				if (stuff.modem == 1) {
					if (getstream1.gcmdsin != null) {
						if (getstream4.readyforreadingc == true) {
							if (getstream1.gcmdsin.length() > 0) {
								getstream4.usablec = getstream1.gcmdsin;
								//getstream4.cind = tgcsl;
							} else {
								//getstream4.usablec = "";
							}
						}
						else {
							//getstream4.usablec = "";
						}

					}
					if (getstream1.gcmdsin.contains("LINK UNREGISTERED")) {
						if (getstream4.varalicensed == true) {
							getstream4.encodedString = getstream4.encodedString + "(This server uses a registered copy of VARA, but you don't, and you manually connected to this server. This means transfer speeds will be limited to speed level 4.)\n";
							
						} else {
							getstream4.encodedString = getstream4.encodedString + "(This server uses an unregistered copy of VARA. You are also using an unregistered copy, and manually connected to this server. This means transfer speeds will be limited to speed level 4.)\n";
							
						}
					}

				}
				getstream4.encodedString = getstream4.encodedString + stuff.commandslist;
				if (stuff.modem == 0 || stuff.modem == 3 || stuff.modem == 4) {
					// manual connection, not modem controlled, so inform about |disc
					getstream4.encodedString = getstream4.encodedString.replaceAll("\r", "") + "\n|disc : Disconnects from the server\r";
					
				}
				stuff.homestring = getstream4.encodedString;
				
				if (getstream4.flrig == true && getstream4.flrigfc == true && getstream4.flrigmfreq != getstream4.flrigsfreq) {
					getstream4.encodedString = getstream4.encodedString.replaceAll("\r", "");
					String tempqsys = "" + (long)getstream4.flrigmfreq;
					int nzqsy = 12-tempqsys.length();
					for (int zzqsy = 0; zzqsy < nzqsy; zzqsy++) {
						if(getstream4.debug==true){System.out.println("loopingfor2");}
                		tempqsys = "0" + tempqsys;
                	}
					getstream4.encodedString = getstream4.encodedString + "\nPlease QSY to " + getstream4.flrigmfreq/1000000 + " to use the server.\n<QSYF>"+ tempqsys + "</QSYF>\r";
				}
				if (isconnect) {
					getstream4.logs = getstream4.logs + getstream4.rcall + " connected\n";
					totalconnections = totalconnections + 1;
				}

				stuff.transmit();
				stuff.interactiontimeout = System.currentTimeMillis() + 300000;
				getstream4.connmsg = false;
			}
			else {
				getstream4.encodedString = getstream4.encodedString + "\n-----\nSorry, but this server doesn't have access to the internet. Please let the server operator know this and/or try again later.\r";
				
				getstream4.logs = getstream4.logs + getstream4.rcall + " connected, but the server did not have internet access\n";
				totalconnections = totalconnections + 1;
				stuff.transmit();
				getstream4.connmsg = false;
			}
	}
	else {
		getstream4.encodedString = getstream4.encodedString + "\n-----\nSorry, but you are banned from using RWS. Talk to KJ7QQG if you want to be unbanned, email jpradiophone@gmail.com\r";
		
		getstream4.logs = getstream4.logs + getstream4.rcall + " connected, but they are banned, so we kicked them off\n";
		stuff.transmit();
		stuff.charlimit = 0;
		getstream4.connmsg = false;
		getstream4.conn = false;
		if (modem == 1) {
			Thread.sleep(5000);
			getstream4.cmdsoutp = "DISCONNECT\r";
			getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
			//if (stuff.modem == 1) {
				getstream4.cmdsout.write(getstream4.cmdsdata);
			//}
			Thread.sleep(5000);
			stuff.disconnect();
			getstream4.conn = false;
			getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
			getstream4.option = 0;
			getstream4.dataoutp = "";
			getstream4.cmdsoutp = "CLEANTXBUFFER\r";
			getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
			getstream4.cmdsout.write(getstream4.cmdsdata);
			getstream4.bwcheck = true; getstream1.dkill = false;
		}
		
		//getstream4.usablec = "";
		//getstream4.cind = 0;
	}
		//getstream4.usablec = ""; // fixes something maybe?
		//getstream4.cind = 0;

		}
		else {
			getstream4.termconnect = false;
			getstream4.conn = false;
			stuff.charlimit = 0;
			getstream4.option = 0;
			getstream4.usabled = "";
			getstream4.rcall = "";
		}
	}
		
	static void disconnect() {

		getstream4.termconnect = false;
		getstream4.conn = false;
		stuff.charlimit = 0;
		stuff.inchat = false;
		getstream4.logs = getstream4.logs + getstream4.rcall + " disconnected\n";
		System.out.println("Logs:\n-----\n" + getstream4.logs + "\n-----");
		getstream4.option = 0;
		getstream4.usabled = "";
		getstream4.rcall = "";

		
	}
	static void transmit() throws IOException, InterruptedException, XmlRpcException {
		try {
		if (stuff.charlimit > 0 && getstream4.encodedString.length() > stuff.charlimit) {
			getstream4.encodedString = getstream4.encodedString.substring(0, stuff.charlimit);
		}
		getstream4.usabled = "";
		if (getstream4.termconnect == false) {
			stuff.interactiontimeout = System.currentTimeMillis() + 300000;
			if (stuff.modem == 1) {
				// vara
				getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", ""); // please stop triggering dkill somehow
	        	getstream4.filebytesleft = getstream4.encodedString.length();
	        	//System.out.println("total length of tranfer: " + (int)(getstream4.encodedString.length()+2) + " bytes");
				getstream4.dataoutp = (getstream4.encodedString.length()+2) + " \n";
	            byte[] datadata = getstream4.dataoutp.getBytes();
	            
				getstream4.dataout.write(datadata);
				getstream4.curbuf = 1;
				getstream4.dataoutp = "";
					if (getstream4.filebytesleft < 31743) {
						//getstream4.encodedStringpart = getstream4.encodedString.substring(getstream4.encodedString.length()-getstream4.filebytesleft, getstream4.encodedString.length());
						getstream4.dataoutp = getstream4.dataoutp + getstream4.encodedString + "\r";
		                datadata = getstream4.dataoutp.getBytes();
		                getstream4.dataout.write(datadata);
		                getstream4.dataoutp = "";
		                getstream4.filebytesleft = 0;
					}
					else {
						while (getstream4.filebytesleft > 1024 && getstream1.dkill == false) {
							if(getstream4.debug==true){System.out.println("looping17");}
							if (getstream1.readingcmds == getstream4.prevrcind) {
								Thread.sleep(101);
								if (getstream1.readingcmds == getstream4.prevrcind) {
									getstream4.readyforreadingc = true;

								}

							}
							else {
								getstream4.readyforreadingc = false;
								getstream1.readingcmds = 0;
								getstream4.prevrcind = getstream1.readingcmds;
							}
							if (getstream1.gcmdsin != null) {
								if (getstream4.readyforreadingc == true) {
									if (getstream1.gcmdsin.length() > 0) {

										getstream4.usablec = getstream1.gcmdsin;

										//getstream4.cind = tgcsl;
									} else {
										//getstream4.usablec = "";
									}
								}
								else {
									//getstream4.usablec = "";
								}

							}
		    					if (getstream4.usablec.length() > getstream4.usablec.lastIndexOf("BUFFER")+6) {
		    						if (getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+6) == ' ') {
		    							int thing = 7;
		    							String thingcounter = "";
		    							while (Character.isDigit(getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+thing))) {
		    								if(getstream4.debug==true){System.out.println("looping18");}
		    								thingcounter = thingcounter + getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+thing);
		    								thing = thing + 1;
		    							}
		    							if (thingcounter != "") {
		    								getstream4.curbuf = Integer.parseInt(thingcounter);
		    								//System.out.println("\n\n\n\n\n" + getstream4.curbuf + "\n\n\n\n\n");
		    							}
		    						}
		    					}
		    					getstream4.usablec = "";
							//if (getstream4.usablec.contains("BUFFER")) {
							//	int i = 7;
								//String numbuild = "";
								
								//if (getstream4.usablec.lastIndexOf("BUFFER")+i < (getstream4.usablec.length()-getstream4.usablec.lastIndexOf("BUFFER"))) {
									//if (Character.isDigit(getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+i))) {
										//boolean kloop = true;
										//while (Character.isDigit(getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
											//if(getstream4.debug==true){System.out.println("looping19");}
											//numbuild = numbuild + getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+i);
											//i = i + 1;
											//if (i >= (getstream4.usablec.length()-getstream4.usablec.lastIndexOf("BUFFER"))) {
												//kloop = false;
											//}
										//}
										//getstream4.numbuf = Integer.parseInt(numbuild);
										//System.out.println(getstream4.numbuf + "\n\n\n\n\n");
									//}
								//}

							//}
							if (getstream4.usablec.contains("DISCONNECTED")) {
								stuff.disconnect();
								getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
								getstream4.filebytesleft = 0;
								getstream4.dataoutp = "";
								getstream4.option = 0;
								getstream4.bwcheck = true; getstream1.dkill = false;
								getstream4.conn = false;
								getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
								getstream4.option = 0;
								getstream4.dataoutp = "";
								getstream4.cmdsoutp = "CLEANTXBUFFER\r";
								getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
								//if (stuff.modem == 1) {
									getstream4.cmdsout.write(getstream4.cmdsdata);
								//}
								getstream4.bwcheck = true; getstream1.dkill = false;
								
							}
							if (getstream4.curbuf < 31743) {
								getstream4.encodedStringpart = getstream4.encodedString.substring(getstream4.encodedString.length()-getstream4.filebytesleft, getstream4.encodedString.length()-getstream4.filebytesleft+1024);
								getstream4.dataoutp = getstream4.dataoutp + getstream4.encodedStringpart;
		                        datadata = getstream4.dataoutp.getBytes();
		                        getstream4.dataout.write(datadata);
		                        getstream4.dataoutp = "";
		                        getstream4.filebytesleft = getstream4.filebytesleft - 1024;
		                        getstream4.curbuf = getstream4.curbuf + 1024;
							}
							getstream4.lastresponse = System.currentTimeMillis();
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;
						}
						if (getstream1.dkill == true) {
							getstream4.filebytesleft = 0;
							getstream4.dataoutp = "";
							getstream4.option = 0;
							getstream1.dkill = false;
						}
						else {
							getstream4.encodedStringpart = getstream4.encodedString.substring(getstream4.encodedString.length()-getstream4.filebytesleft);
							getstream4.dataoutp = getstream4.dataoutp + getstream4.encodedStringpart + "\r";
			                datadata = getstream4.dataoutp.getBytes();
			                getstream4.dataout.write(datadata);
			                getstream4.dataoutp = "";
			                getstream4.filebytesleft = 0;
						}
					}
				stuff.interactiontimeout = System.currentTimeMillis() + 300000;
			}
			if (stuff.modem == 2) {
				// freedata
			}
			if (stuff.modem == 3) {
				// fldigi
				getstream4.usabled = "";
				getstream4.curbuf = 1;
				Thread.sleep(15000);
				while (fldigihandler.client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					getstream4.usabled = "";
                }
				fldigihandler.client.execute("text.add_tx", new Object[]{"\n\n" + getstream4.encodedString + "\n\n^r"});
				fldigihandler.client.execute("main.tx", new Object[]{""});
                while (fldigihandler.client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    fldigihandler.client.execute("text.clear_rx", new Object[0]);
					stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					getstream4.usabled = "";
                }
                Thread.sleep(1000L);
                getstream4.curbuf = 0;
                fldigihandler.client.execute("text.clear_rx", new Object[0]);

				stuff.interactiontimeout = System.currentTimeMillis() + 300000;
				getstream4.usabled = "";
			}
			if (stuff.modem == 4) {
				// serial
				getstream4.usabled = "";
				getstream4.curbuf = 1;
				try {
				serialhandler.outs.writeBytes(getstream4.encodedString);
				} catch (Exception e) {
					if(getstream4.debug == true) {e.printStackTrace();}
				}
				getstream4.curbuf = 0;
				stuff.interactiontimeout = System.currentTimeMillis() + 300000;
				getstream4.usabled = "";
			}
			if (stuff.modem == 5) {
				// tcp
				getstream4.usabled = "";
				getstream4.curbuf = 1;
				getstream4.encodedString = getstream4.encodedString + "\n";
				try {
				tcphandler.tcpout.write(getstream4.encodedString.getBytes());
				} catch (Exception e) {
					if(getstream4.debug == true) {e.printStackTrace();}
				}
				getstream4.curbuf = 0;
				stuff.interactiontimeout = System.currentTimeMillis() + 300000;
				getstream4.usabled = "";
			}
			

        }
        else {
        	System.out.println(getstream4.encodedString);
        }
		getstream4.usabled = "";
		} catch (Exception e) {
			if(getstream4.debug == true) {e.printStackTrace();}
			getstream4.curbuf = 0;
			stuff.interactiontimeout = System.currentTimeMillis() + 300000;
			getstream4.usabled = "";
		}
	}
}

class getstream1 implements Runnable {  // reads commands from modem
	public static String gcmdsin = "";
	public static Socket cmds = null;
	public static int readingcmds = 0;
	public static boolean dkill = false;
	public static long lvupdate = System.currentTimeMillis();
	//public static boolean cfgci = true;

	public void run() {
		InputStream cmdsin = null;
		try {
			cmdsin = cmds.getInputStream();
		} catch (Exception e) { e.printStackTrace(); }


		int gcmds;
		while (0==0) {
			

            try {
				if ((gcmds = cmdsin.read()) != -1) {
					//cfgci = false;
					readingcmds = readingcmds + 1;

					    gcmdsin = gcmdsin + (char)gcmds;
							if (gcmdsin.contains("PTT OFF") && getstream4.flrig == true) {
								gcmdsin = gcmdsin.replaceAll("PTT OFF", "");
								getstream4.client.execute("rig.set_ptt", new Object[]{0});		
								getstream4.ptt = false;
							}
							if (gcmdsin.contains("PTT ON") && getstream4.flrig == true) {
								gcmdsin = gcmdsin.replaceAll("PTT ON", "");
								getstream4.client.execute("rig.set_ptt", new Object[]{1});
								getstream4.ptt = true;
							}
							if (gcmdsin.contains("DISCONNECTED")) {
								stuff.disconnect();
								
								getstream4.filebytesleft = 0;
								getstream4.dataoutp = "";
								getstream4.option = 0;
								dkill = true;
								getstream4.bwcheck = true; getstream1.dkill = false;
								getstream4.conn = false;
								getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
								getstream4.option = 0;
								getstream4.dataoutp = "";
								getstream4.cmdsoutp = "CLEANTXBUFFER\r";
								getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
								//if (stuff.modem == 1) {
									getstream4.cmdsout.write(getstream4.cmdsdata);
								//}
								getstream4.bwcheck = true; getstream1.dkill = false;
							}
							if (gcmdsin.contains("REGISTERED " + getstream4.callsign)) {
								gcmdsin = gcmdsin.replaceAll("REGISTERED " + getstream4.callsign, "");
								getstream4.varalicensed = true;
							}
							if (gcmdsin.contains("IAMALIVE")) {
								gcmdsin = gcmdsin.replaceAll("IAMALIVE", "");
								lvupdate = System.currentTimeMillis();
								
							}
					    if (getstream4.bwcheck == true) {
							if (gcmdsin.contains("OK")) { // bandwidth change command accepted
								gcmdsin.replaceAll("OK", "");
								if (getstream4.conn == true && getstream4.termconnect == false) { // if the command was accepted, that means we're disconnected, but we still think we're connected, so we fix that
									stuff.disconnect();
									getstream4.conn = false;
									getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
									//getstream4.usablec = "";
									//getstream4.cind = 0;
									
									//System.out.println("disconnected?");
									getstream4.option = 0;
									getstream4.dataoutp = "";
									getstream4.cmdsoutp = "CLEANTXBUFFER\r";
									getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
									if (stuff.modem == 1) {
										getstream4.cmdsout.write(getstream4.cmdsdata);
									}
									getstream4.conn = false;
									getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
									getstream4.option = 0;
									getstream4.dataoutp = "";
									getstream4.cmdsoutp = "CLEANTXBUFFER\r";
									getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
									if (stuff.modem == 1) {
										getstream4.cmdsout.write(getstream4.cmdsdata);
									}
									getstream4.bwcheck = true; getstream1.dkill = false;
									
								}
								getstream4.bwcheck = false;
							}
							if (gcmdsin.contains("WRONG")) { // trying to change bandwidth while connected (don't need to check for correct bandwidth until disconnected)
								gcmdsin.replaceAll("WRONG", "");
								getstream4.bwcheck = false;
							}
					    }


						//cfgci = true;

				}
			} catch (Exception e) { e.printStackTrace(); }
		}


	}
}

class getstream2 implements Runnable { // reads data from modem
	public static String gdatain = "";
	public static String gdataout;
	public static Socket data = null;
	public static int readingdata = 0;
	
	String pgdi = "";
	char datachar = 0;

	public void run() {
		InputStream datain = null;
		try {
			datain = data.getInputStream();
		} catch (Exception e) { e.printStackTrace(); }


        InputStreamReader isr2 = new InputStreamReader(datain,StandardCharsets.UTF_8);
        BufferedReader br2 = new BufferedReader(isr2);
        boolean erasenext = false;
        String totalnumbuild = "";
        int totalnum = 0;
        int charnum = 0;
        boolean lookingforletters = false;
        boolean buildingtotalnum = true;
        boolean varacfuckedup = false;
		while (0==0) {

	        try {
				datachar = (char)br2.read();

				if (Character.isDigit(datachar)) {
					if (buildingtotalnum == true) {
						if (pgdi == "") {
							buildingtotalnum = true;
							if (totalnumbuild == "") {
								if (datachar == '0') {
									varacfuckedup = true;
									erasenext = true;
								}
							}
							if (varacfuckedup == false) {
								totalnumbuild = totalnumbuild + datachar;
								erasenext = true;
							}

						}
						else {
							
						}
					}
					else {
						pgdi = pgdi + datachar;
						charnum = charnum + 1;
						if (charnum == totalnum) {
							gdatain = pgdi;
							pgdi = "";
							totalnum = 0;
							erasenext = false;
							totalnumbuild = "";
							charnum = 0;
							lookingforletters = false;
							buildingtotalnum = true;
							
						}
					}

				}
				else {
					if (erasenext == true) {
						if (varacfuckedup == true) {
							erasenext = false;
							varacfuckedup = false;
						}
						else {
							erasenext = false;
							buildingtotalnum = false;
							lookingforletters = true;
							totalnum = Integer.parseInt(totalnumbuild);
						}

					}
					else {
							if (lookingforletters == true) {
								pgdi = pgdi + datachar;
								charnum = charnum + 1;
								if (charnum == totalnum) {
									gdatain = pgdi;
									pgdi = "";
									totalnum = 0;
									erasenext = false;
									totalnumbuild = "";
									charnum = 0;
									lookingforletters = false;
									buildingtotalnum = true;
									
								}
							}

						
					}
				}

				
	        } catch (Exception e) { e.printStackTrace(); }
		}


	}
}

class getstream3 implements Runnable { // reads commands from terminal
	public static String termin = "";

	public void run() {

        BufferedReader readerterm = new BufferedReader(
            new InputStreamReader(System.in));
 

        String nameterm = "";
        while (0==0) {
        	try {
				Thread.sleep(53);
        	} catch (Exception e) { e.printStackTrace(); }
    		try {
    			
    			nameterm = readerterm.readLine();
    		} catch (Exception e) { e.printStackTrace(); }
     

            termin = nameterm;
        }

	}
}
class getstream4 implements Runnable { // handles timing things
	public static int initwait = 0;
	public static byte[] cmdsdata = null;
	public static String cmdsoutp = "";
	public static OutputStream cmdsout = null;
	public static boolean termconnect = false;
	public static long cqresp = 0;
	public static String cqbw = "";
	public static boolean conn = false;
	public static float cqwaittime = 130;
	public static String callsign = "";
	public static String cqrespcall = "";
	public static long processingcycle = 0;
	public static long updatecycle = 0;
	public static boolean debug = false;
	public static boolean varalicensed = false;
	public static String serverid = "";
	public static String servfreq = "";
	public static String servlocator = "";
	public static String softver = "";
	public static String stp = "";
	public static Scanner webscan;
	public static int option = 0;
	public static long lastresponse = System.currentTimeMillis();
	public static int loglength = 0;
	public static String logs = "";
	public static int filebytesleft = 0;
	public static String encodedString = "";
	public static int curbuf = 0;
	public static int prevrcind = 0;
	public static boolean readyforreadingc = true;
	//public static int cind = 8;
	public static String usablec = "";
	public static int numbuf = 0;
	public static String encodedStringpart = "";
	public static String dataoutp = "";
	public static OutputStream dataout = null;
	public static String usabled = "";
	public static String prevdatainthing = "";
	public static String rcall = "";
	public static boolean connmsg = false;
    public static boolean flrig = false;
    public static boolean ptt = false;
    public static XmlRpcClient client = new XmlRpcClient();
    public static XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
    public static boolean flrigfc = false;
    public static long flrigsfreq = 14109000;
    public static long flrigmfreq = 14109000;
    public static long flrigfreq = 14109000;
    public static boolean bwcheck = true;
    public static boolean lbm = false;
	public static String pathToClone = null;
	public static List<String> bannedcalls = new ArrayList<>();
	
	
	public void run() {
		while (0==0) {

				
				if(debug==true){System.out.println("checking into activeservers");}
				try {
					


					           String pas = "";
					           String mtype = "";
					           if (stuff.modem == 0) {
					        	   mtype = "none";
					           }
					           if (stuff.modem == 1) {
					        	   mtype = "VARA";
					           }
					           if (stuff.modem == 2) {
					        	   mtype = "FreeDATA";
					           }
					           if (stuff.modem == 3) {
					        	   mtype = "FLDigi";
					           }
					           if (stuff.modem == 4) {
					        	   mtype = "Serial";
					           }
					           if (stuff.modem == 5) {
					        	   mtype = "TCP";
					           }
						            if (varalicensed == false && stuff.modem == 1) {
						            	pas = serverid + " " + callsign + " " + servfreq + " " + servlocator + " " + softver + " VARA unlicensed\n";
						            }
						            else {
						            	pas = serverid + " " + callsign + " " + servfreq + " " + servlocator + " " + softver + " " + mtype + "\n";
						            }

						   stuff.backend("wa", pas);
					       
					       if (logs.length() > loglength) {
						    	   String plogs = logs.substring(loglength);
						    stuff.backend("wl", plogs);
					    	   loglength = logs.length();
					       }
				            	

					       if ((System.currentTimeMillis() - lastresponse) > 900000) {
								System.out.println("main thread stuck somewhere, hasn't responded in " + (System.currentTimeMillis()-lastresponse)/1000 + " seconds, terminating program");
								//Runtime r = Runtime.getRuntime();
								//String path = mainclass.class.getProtectionDomain().getCodeSource().getLocation().getPath();
								//String decodedPath = URLDecoder.decode(path, "UTF-8");
								//if(debug==true){System.out.println(decodedPath + "vrws.jar");}

								//Process p = r.exec("java -jar " + decodedPath + "vrws.jar");
								//p.waitFor();
								System.exit(1);
							}
					       if ((System.currentTimeMillis() - getstream1.lvupdate) > 150000 && stuff.modem == 1) {
					    	   System.out.println("VARA instance lost, hasn't responded in " + (System.currentTimeMillis()-getstream1.lvupdate)/1000 + " seconds, terminating program");
					    	   System.exit(1);
					       }
					       if (getstream4.flrig == true && getstream4.flrigfc == true) {
					    	   long testfreq = 0;
					    	   try {
					    	   testfreq = Long.decode((String)getstream4.client.execute("rig.get_vfo", new Object[]{}));
					    	   } catch (Exception e) {
					    		   System.out.println("flrig frequency query returned an error, terminating program");
					    		   System.exit(1);
					    	   }
					    	   //System.out.println(testfreq);
					    	   if (testfreq != getstream4.flrigfreq) {
					    		   System.out.println("flrig frequency query returned an incorrect value, resetting frequency...");
					    		   getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigfreq});
					    		   getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigfreq});
					    		   Thread.sleep(1000);
					    		   testfreq = Long.decode((String)getstream4.client.execute("rig.get_vfo", new Object[]{}));
					    		   if (testfreq != getstream4.flrigfreq) {
					    			   System.out.println("flrig frequency is still incorrect after attempting reset, terminating program");
					    			   System.exit(1);
					    		   }
					    		   
					    	   }
					       }
					       List<String> bannedcalls = new ArrayList<>();
							while (bannedcalls.isEmpty()) {
								
					            try {
					                    int ind = 0;
					                    String[] temparray = stuff.backend("gc", "bannedcalls").split("\n");
					                    while (ind < temparray.length-1) {
					                    	ind = ind + 1;
					                        bannedcalls.add(temparray[ind]);
					                    }


					            } catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
						}
							getstream4.bannedcalls = bannedcalls;
					       System.gc();
					       Thread.sleep(450000);
					       

						} catch (Exception e) { System.out.print("");if(debug==true){e.printStackTrace();} }


		}
	}
}


class freedatags implements Runnable {
	
	public void run() {
		
		// freedata handler
		
	}
}

class fldigihandler implements Runnable {
	public static byte[] rx = new byte[0];
	public static String rxstr = "";
	public static XmlRpcClientConfigImpl config;
    public static XmlRpcClient client;
	public void run() {
		while (0==0) {
			try {
				if (fldigihandler.client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
					rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
					rxstr = new String(rx, StandardCharsets.UTF_8);
					rxstr = rxstr.replaceAll("\\r|\\n", "");
					if (rxstr.length() > 0) {
						String[] callsignsplit = (rxstr + " ").split("==");
						if (callsignsplit.length > 2) {
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							getstream4.usabled = callsignsplit[callsignsplit.length-2];
							fldigihandler.client.execute("text.clear_rx", new Object[0]);
						}
						if (callsignsplit.length == 2) {
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;
						}
						if (callsignsplit.length == 1 && rxstr.charAt(rxstr.length()-1) != '=') {
							fldigihandler.client.execute("text.clear_rx", new Object[0]);
						}
						
					}
				}
				else {
					stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					fldigihandler.client.execute("text.clear_rx", new Object[0]);
				}


			Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class serialhandler implements Runnable {
	public static String serialport = "/dev/tty0";
	public static int serialbaud = 9600;
	public static NRSerialPort serial;
	public static String rxstr = "";
	public static DataInputStream ins;
	public static DataOutputStream outs;
	public void run() {
		serial.connect();
		ins = new DataInputStream(serial.getInputStream());
		outs = new DataOutputStream(serial.getOutputStream());
		while (0==0) {
		try {
			while(ins.available()==0) {
				Thread.sleep(100);
			};
				rxstr = rxstr + (char)ins.read();
				rxstr = rxstr.replaceAll("\\r|\\n", "");
				if (rxstr.length() > 0) {
					String[] callsignsplit = (rxstr + " ").split("==");
					if (rxstr.contains("===")) {
						getstream4.usabled = " "; // something other than nothing
					}
					if (callsignsplit.length > 2) {
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
						getstream4.usabled = callsignsplit[callsignsplit.length-2];
						rxstr = "";
					}
					if (callsignsplit.length == 2) {
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					
				}
				//outs.write((byte)b);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	
}

class tcphandler implements Runnable {
	public static int tcpport = 3141;
	public static String rxstr = "";
	public static ServerSocket tcpsocket;
	public static InputStream tcpin;
	public static OutputStream tcpout;
	public void run() {
		
		
		while (0==0) {
			try {
				
				Socket socket = tcpsocket.accept();
				boolean connected = true;
				tcpin = socket.getInputStream();
				tcpout = socket.getOutputStream();
				getstream4.usabled = " "; // trigger callsign request
				while (connected) {
					while(tcpin.available()==0 && connected) {
						rxstr = "";
						int tchar = -1;
						try {
						tchar = tcpin.read();
						} catch (Exception e) {
							if(getstream4.debug==true) {e.printStackTrace();}
						}
						
						if (tchar == -1 || socket.isClosed() || !socket.isConnected()) {
							connected = false;
						} else {
							rxstr = rxstr + (char)tchar;
						}
						Thread.sleep(100);
						
					};
					if (connected) {
						while(tcpin.available() > 0) {
							rxstr = rxstr + (char)tcpin.read();
						};
						while (rxstr.charAt(rxstr.length() - 1) == '\n' || rxstr.charAt(rxstr.length() - 1) == '\r') {
							rxstr = rxstr.substring(0, rxstr.length()-1);
						}
							getstream4.usabled = rxstr;
							rxstr = "";
					}
					
						

				}
				// disconnect
				if (getstream4.conn == true) {
					stuff.disconnect();
					
				}
				getstream4.rcall = "";
				stuff.inchat = false;
				getstream4.option = 0;
				
				
					//outs.write((byte)b);
				
			} catch (Exception e) {
				e.printStackTrace();
				if (getstream4.conn == true) {
					stuff.disconnect();
					
				}
				getstream4.rcall = "";
				stuff.inchat = false;
				getstream4.option = 0;
			}
		}
	}
	
}

public class mainclass {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, NoFilepatternException, GitAPIException, XmlRpcException, URISyntaxException {

		
		
		String wstext = "";
		getstream4.option = 0;

		getstream4.rcall = "";
		String searchthing = "";
		String weatherthing = "";
		getstream4.logs = "";





		String weatherend = "";
		String[] latLng = null;
		getstream4.conn = false;
		getstream4.connmsg = false;

		getstream4.termconnect = false;


		getstream4.softver = "v130";
		System.out.println("Starting RWS server (version " + getstream4.softver + ")");
		System.out.println("Fetching backend IPs...");
		stuff.backendips = Jsoup.parse(new URI("https://raw.githubusercontent.com/Glitch31415/rws/refs/heads/main/backendips").toURL(), 10000).wholeText().replaceAll("\n", "").replaceAll("\r", "");
		System.out.println("Found " + stuff.backendips);
		stuff.totalconnections = 0;
		stuff.intaccess = true;
		getstream4.bwcheck = true; getstream1.dkill = false;
		String lastchat = "";
		getstream4.debug = false;
        int chatlines = 0;
		int chattotlines = 0;
		boolean chatltype = false;
        getstream4.flrigsfreq = 14109000;
        getstream4.flrigmfreq = 14109000;
        getstream4.flrigfreq = 14109000;
        getstream4.flrig = false;
        getstream4.flrigfc = false;
		//List<String> badwords = new ArrayList<>();
		//while (badwords.isEmpty()) {
			
            //try {
					
                    //int ind = 0;
                   // String[] temparray = stuff.backend("gc", "profanity").split("\n");
                   // while (ind < temparray.length-1) {
                    	//ind = ind + 1;
                        //badwords.add(temparray[ind]);
                    //}


            //} catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
		//}
		List<String> bannedcalls = new ArrayList<>();
		while (bannedcalls.isEmpty()) {
			
            try {
                    int ind = 0;
                    String[] temparray = stuff.backend("gc", "bannedcalls").split("\n");
                    while (ind < temparray.length-1) {
                    	ind = ind + 1;
                        bannedcalls.add(temparray[ind]);
                    }


            } catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
		}
		getstream4.bannedcalls = bannedcalls;
		stuff.weatherkey = stuff.backend("k", "weatherkey");
		stuff.weatherkey2 = stuff.backend("k", "weatherkey2");
	        
	                @SuppressWarnings("resource")
		Scanner callinp = new Scanner(System.in);
	        		getstream1.cmds = null;
	        		getstream2.data = null;
	        		getstream4.cmdsout = null;
	        		getstream4.dataout = null;
	        		getstream4.callsign = null;
	        		stuff.welcomemessage = null;
	        		getstream4.servfreq = null;
	        		getstream4.servlocator = null;
        	stuff.cmdport = 8300;
        	stuff.dataport = 8301;
        	stuff.kissport = 8100;
        	boolean ssaved = false;
    	      boolean filethere = false;
    	      boolean serror = false;
  			stuff.ova = false;
    		File myObjps;
  	      myObjps = new File(System.getProperty("user.home")+File.separator+"rwsdata"+File.separator, "rws.conf");
  		try {
  	      Scanner myReaderps = new Scanner(myObjps);
  	      int templine = 0;

  	      while (myReaderps.hasNextLine()) {
  	    	 filethere = true;
  	    	  templine = templine + 1;
  	    	  String sline = myReaderps.nextLine();
  	    	  if (templine == 2) {
  	    		  stuff.cmdport = Integer.valueOf(sline);
  	    	  }
  	    	  if (templine == 3) {
  	    		stuff.dataport = Integer.valueOf(sline);
  	    	  }
  	    	  if (templine == 4) {
  	    		stuff.kissport = Integer.valueOf(sline);
  	    	  }
  	    	  if (templine == 5) {
  	    		  if (sline.contains("yes")) {
  	    			  getstream4.debug = true;
  	    		  }
  	    		  else {
  	    			  getstream4.debug = false;
  	    		  }
  	    	  }
  	    	  if (templine == 6) {
  	    		getstream4.callsign = sline.replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").toUpperCase();
  	    		if (getstream4.callsign.contains("-")) {
  	    			getstream4.callsign = getstream4.callsign.substring(0, getstream4.callsign.indexOf("-"));
  	    		}
  	    	  }
  	    	  if (templine == 7) {
  	    		  stuff.welcomemessage = sline;
  	    	  }
  	    	  if (templine == 8) {
  	    		  getstream4.servfreq = sline;
  	    	  }
  	    	  if (templine == 9) {
  	    		  getstream4.servlocator = sline;
  	    	  }
  	    	  if (templine == 10) {
  	    		  if (sline.contains("yes")) {
  	    			  stuff.ova = true;
  	    		  }
  	    	  }
  	    	  if (templine == 11) {
  	    		  if (sline.contains("yes")) {
  	    			  getstream4.flrig = true;
  	    			if(getstream4.debug==true){System.out.println("connecting to flrig... (config)");}
          	        getstream4.config.setServerURL(new URI("http://127.0.0.1:12345/RPC2").toURL());
          	        getstream4.client.setConfig((XmlRpcClientConfig)getstream4.config);
          	      if(getstream4.debug==true){System.out.println("successfully connected to flrig (config)");}
          	    if(getstream4.debug==true){System.out.println("flrig setting PTT... (config)");}
          	  System.out.println("If the server hangs here, restart VARA, flrig, and the server");
          	      	getstream4.client.execute("rig.set_ptt", new Object[]{0});		
          	      	getstream4.ptt = false;
          	      if(getstream4.debug==true){System.out.println("flrig successfully set PTT (config)");}
          	      	
  	    		  }
  	    	  }
  	    	  if (templine == 12) {
  	    		  if (sline.contains("yes")) {
  	    			getstream4.flrigfc = true;
  	    		  }
  	    	  }
  	    	  if (templine == 13) {
  	    		getstream4.flrigsfreq = Long.parseLong(sline);
				if (getstream4.flrig == true && getstream4.flrigfc == true) {
					if(getstream4.debug==true){System.out.println("flrig setting frequency... (config)");}
        	        getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigsfreq});
        	        getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigsfreq});
        	        getstream4.flrigfreq = getstream4.flrigsfreq;
        	        if(getstream4.debug==true){System.out.println("flrig successfully set frequency (config)");}
				}
  	    	  }
  	    	  if (templine == 14) {
      			getstream4.flrigmfreq = Long.parseLong(sline);
  	    		
  	    	  }
  	    	  if (getstream4.flrig == true && getstream4.flrigfc == true) {
    	        getstream4.servfreq = getstream4.flrigsfreq/1000000 + "->" + getstream4.flrigmfreq/1000000;
			}
  	    	if (templine == 15) {
  	    		if (sline.contains("yes")) {
  	    			getstream4.lbm = true;
  	    		  }
  	    		
  	    	  }
  	    	if (templine == 16) {
  	    		stuff.modem = Integer.valueOf(sline);
  	    		if (stuff.modem == 0) {
  	    			System.out.println("\nYou have chosen to link no modem to the server. This means the server is inaccessible outside of your terminal. You can change this in " + System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"rws.conf\n");
  	    		}
  	    	}
  	    	if (templine == 17) {
  	    		serialhandler.serialport = sline;
  	    	}
  	    	if (templine == 18) {
  	    		serialhandler.serialbaud = Integer.valueOf(sline);
  	    	}
  	    	if (templine == 19) {
  	    		tcphandler.tcpport = Integer.valueOf(sline);
  	    		ssaved = true;
  	    	}
  	    	  if (templine > 19) {
  	    		  ssaved = false;
  	    	  }

  	      }


  	      myReaderps.close();
  		} catch (Exception e) { serror=true; ssaved=false; if (filethere == true) {
    		System.out.println("Configuration file found, but was invalid (" + e + ")");
    		if (e.toString().contains("org.apache.xmlrpc.XmlRpcException")) {
    			System.out.println("\n\nIt looks like RWS was unable to connect to flrig. Make sure you start flrig before starting RWS.\n\n");
    			System.exit(0);
    		}
  	  }
  	  else {
  		  ssaved = false;
  		System.out.println("Configuration file doesn't exist");
  	  } }
	      if (ssaved == true) {
    		System.out.println("Using saved configuration located at " + System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"rws.conf");
	      }
	      else {
	    	  if (filethere == true && serror == false) {
	    		System.out.println("Configuration file found, but was invalid. (wrong number of lines)");
	    	  }
	    	  if (filethere == false && serror == false) {
	    		  System.out.println("Configuration file found, but was invalid. (empty file)");
	    	  }
	      }
        	if (ssaved == true) {
        		stuff.modemconfig();

        	}
        	else {
        		stuff.ova = false;
        		System.out.println("Enter VARA command port (default: 8300)");
    			String tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				stuff.cmdport = Integer.valueOf(tempenterstr);
    				tempenterstr = "";
    			}
    			System.out.println("Enter VARA data port (default: 8301)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				stuff.dataport = Integer.valueOf(tempenterstr);
    				tempenterstr = "";
    			}
    			System.out.println("Enter VARA KISS port (default: 8100)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				stuff.kissport = Integer.valueOf(tempenterstr);
    				tempenterstr = "";
    			}
    			System.out.println("Enable debug mode? (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.debug = true;
    			}
    			System.out.println("Enter callsign without suffixes or prefixes (example: KJ7QQG)");
    			getstream4.callsign = callinp.nextLine().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").toUpperCase();
  	    		if (getstream4.callsign.contains("-")) {
  	    			getstream4.callsign = getstream4.callsign.substring(0, getstream4.callsign.indexOf("-"));
  	    		}
    			System.out.println("Enter server welcome message (leave blank if none)");
    			stuff.welcomemessage = callinp.nextLine();
    			System.out.println("Enter server frequency or other info on how to connect (example: 14.109) (only used if flrig frequency control is disabled)");
    			getstream4.servfreq = callinp.nextLine();
    			System.out.println("Enter server location (example: CN84OR)");
    			getstream4.servlocator = callinp.nextLine();
    			System.out.println("Would you like to open VARA automatically on startup? May not work on Linux. (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				System.out.println("VARA will try to open automatically");
    				stuff.ova = true;
    			}

    			System.out.println("Attempt to connect to flrig at 127.0.0.1:12345? includes PTT control (BETA) (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.flrig = true;
    				System.out.println("Connecting to flrig...");
        	        getstream4.config.setServerURL(new URI("http://127.0.0.1:12345/RPC2").toURL());
        	        getstream4.client.setConfig((XmlRpcClientConfig)getstream4.config);
        	        System.out.println("Successfully connected to flrig");
        	        System.out.println("flrig setting PTT...");
        	        System.out.println("If the server hangs here, restart VARA, flrig, and the server");
          	      	getstream4.client.execute("rig.set_ptt", new Object[]{0});		
          	      	getstream4.ptt = false;
          	      	System.out.println("flrig successfully set PTT");
        	        
    			}
    			System.out.println("If flrig is enabled, also use automatic frequency control? (BETA) (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.flrigfc = true;
    				System.out.println("Using frequency control");
    			}
    			System.out.println("If using frequency control, what frequency do you want to use for standby (in hz)? default: 14109000 (BETA)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.flrigsfreq = Long.parseLong(tempenterstr);
    				System.out.println("Standby freq: " + getstream4.flrigsfreq);
    				if (getstream4.flrig == true && getstream4.flrigfc == true) {
            	        getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigsfreq});
            	        getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigsfreq});
            	        getstream4.flrigfreq = getstream4.flrigsfreq;
    				}
    			}
    			System.out.println("If using frequency control, what frequency do you want to use for traffic (in hz)? default: 14109000 (BETA)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.flrigmfreq = Long.parseLong(tempenterstr);
    				System.out.println("Traffic freq: " + getstream4.flrigmfreq);
    			}
    			if (getstream4.flrig == true && getstream4.flrigfc == true) {
        	        getstream4.servfreq = getstream4.flrigsfreq/1000000 + "->" + getstream4.flrigmfreq/1000000;
				}
    			System.out.println("Should the server be limited to 500 hz connections only? (in order to comply with FCC 97.221 in the US, for example) (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				getstream4.lbm = true;
    				System.out.println("Limiting bandwidth to 500 hz");
    			}
    			System.out.println("Which modem would you like to use for your server? Type a number:\n0: None (for testing purposes)\n1: VARA\n2: FreeDATA\n3: FLDigi (not recommended)\n4: Serial\n5: TCP\ndefault: VARA");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				stuff.modem = Integer.valueOf(tempenterstr);
    				System.out.println("Using option " + stuff.modem);
    			}
    			else {
    				stuff.modem = 1;
    				System.out.println("Defaulting to VARA");
    			}
    			System.out.println("What port would you like to use if you choose serial? default: /dev/tty0");
    			System.out.println("Currently available ports on this computer:");
    			for(String s : NRSerialPort.getAvailableSerialPorts()){
    				System.out.println(s);
    			}
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				serialhandler.serialport = tempenterstr;
    				System.out.println("Using port " + serialhandler.serialport);
    			}
    			else {
    				System.out.println("Defaulting to /dev/tty0");
    			}
    			System.out.println("What baudrate would you like to use if you choose serial? default: 9600");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				serialhandler.serialbaud = Integer.valueOf(tempenterstr);
    				System.out.println("Using " + serialhandler.serialbaud + " baud");
    			}
    			else {
    				System.out.println("Defaulting to 9600 baud");
    			}
    			System.out.println("What port would you like to use if you choose TCP? default: 3141");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				tcphandler.tcpport = Integer.valueOf(tempenterstr);
    				System.out.println("Using 127.0.0.1:" + tcphandler.tcpport);
    			}
    			else {
    				System.out.println("Defaulting to 127.0.0.1:3141");
    			}
    			System.out.println("Would you like to save these settings to use automatically in the future? (Leave blank for no, any input is yes)");
    			tempenterstr = callinp.nextLine();
    			if (tempenterstr != "") {
    				System.out.println("The settings file will be saved to " + System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"rws.conf");
    				new File(System.getProperty("user.home")+File.separator+"rwsdata").mkdirs();
		            FileWriter myWriterps;
			            myWriterps = new FileWriter(System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"rws.conf");
			            String tempdebugs = "no";
			            String ovas = "no";
			            String flrigs = "no";
			            String flrigfcs = "no";
			            String lbms = "no";
			            if (getstream4.debug == true) {
			            	tempdebugs = "yes";
			            }
			            if (stuff.ova == true) {
			            	ovas = "yes";
			            }
			            if (getstream4.flrig == true) {
			            	flrigs = "yes";
			            }
			            if (getstream4.flrigfc == true) {
			            	flrigfcs = "yes";
			            }
			            if (getstream4.lbm == true) {
			            	lbms = "yes";
			            }
				        myWriterps.write("This is the config file for your RWS server. In order, the values for each line are for: VARA command port, VARA data port, VARA KISS port, debug mode (yes/no), callsign, server welcome message, server frequency, server 6 character locator, open VARA automatically (yes/no), use flrig + ptt control (yes/no), use freq control when using flrig (yes/no), flrig freq control standby freq, flrig freq control traffic freq, 500 hz only mode when using VARA (yes/no), modem type (0 for none, 1 for VARA, 2 for FreeDATA, 3 for FLDigi, 4 for Serial, 5 for TCP), serial port, serial baudrate, TCP port\n" + stuff.cmdport + "\n" + stuff.dataport + "\n" + stuff.kissport + "\n" + tempdebugs + "\n" + getstream4.callsign + "\n" + stuff.welcomemessage + "\n" + getstream4.servfreq + "\n" + getstream4.servlocator + "\n" + ovas + "\n" + flrigs + "\n" + flrigfcs + "\n" + Long.toString(getstream4.flrigsfreq) + "\n" + Long.toString(getstream4.flrigmfreq) + "\n" + lbms + "\n" + stuff.modem + "\n" + serialhandler.serialport + "\n" + serialhandler.serialbaud + "\n" + tcphandler.tcpport);

			       myWriterps.close();
    			}
        	}
			System.out.println("Please wait...");
			if (ssaved == false) {
				stuff.modemconfig();

			}
			int leftLimit = 97; // letter 'a'
    	    int rightLimit = 122; // letter 'z'
    	    int targetStringLength = 16;
    	    Random random = new Random();
    	    StringBuilder buffer = new StringBuilder(targetStringLength);
    	    for (int i = 0; i < targetStringLength; i++) {
    	        int randomLimitedInt = leftLimit + (int) 
    	          (random.nextFloat() * (rightLimit - leftLimit + 1));
    	        buffer.append((char) randomLimitedInt);
    	    }
			getstream4.serverid = buffer.toString();
			
			getstream4.prevdatainthing = "";
			boolean weatherbroke = false;
			
			String postname = "";
			String postbody = "";
			getstream4.stp = "";
			getstream4.varalicensed = false;
			float recentsn = (float)0.00;
			if (stuff.modem == 1) {
				if (getstream4.lbm == true) {
					getstream4.cmdsoutp = "MYCALL "+getstream4.callsign+"\rPUBLIC ON\rLISTEN ON\rCHAT ON\rCLEANTXBUFFER\rBW500\rCOMPRESSION FILES\r";
				}
				else {
					getstream4.cmdsoutp = "MYCALL "+getstream4.callsign+"\rPUBLIC ON\rLISTEN ON\rCHAT ON\rCLEANTXBUFFER\rBW2300\rCOMPRESSION FILES\r";
				}
				getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
				getstream4.cmdsout.write(getstream4.cmdsdata);
			}
			Thread.sleep(1000);
			Thread object3 = new Thread(new getstream3());
			object3.start();
			long starttime = System.currentTimeMillis();
			getstream4.cqwaittime = 130;
			getstream4.cqresp = 0;
			getstream4.cqbw = "";
			getstream4.cqrespcall = "";
			String cmdoption = "";
			getstream4.updatecycle = 0;
			String openedchat = "";
			Thread object4 = new Thread(new getstream4());
			object4.start();
			System.out.println("The server has started. You may interact with the server from this terminal by entering a command:\n"+ stuff.commandslist.replaceAll("\r", "") + "\n|disc : Disconnects from the server\r");
			
			while (0==0) {

				if (getstream4.termconnect == true) {
					getstream4.curbuf = 0;
				}
				stuff.updusabled();

					if (getstream1.readingcmds == getstream4.prevrcind) {
						Thread.sleep(101);
						if (getstream1.readingcmds == getstream4.prevrcind) {
							getstream4.readyforreadingc = true;

						}

					}
					else {
						getstream4.readyforreadingc = false;
						getstream1.readingcmds = 0;
						getstream4.prevrcind = getstream1.readingcmds;
					}

					


					if (getstream1.gcmdsin != null) {
						if (getstream4.readyforreadingc == true) {
							if (getstream1.gcmdsin.length() > 0) {
								getstream4.usablec = getstream1.gcmdsin;
								//getstream4.cind = tgcsl;
							} else {
								//getstream4.usablec = "";
							}
						}
						else {
							//getstream4.usablec = "";
						}

					}
					// main loop things

					
					if (getstream4.conn == true) {
						if (getstream4.flrig == true && getstream4.flrigfc == true && getstream4.curbuf == 0 && getstream4.flrigfreq == getstream4.flrigsfreq && getstream4.flrigsfreq != getstream4.flrigmfreq) {
							while (getstream4.ptt == true) {
								Thread.sleep(10);
								if(getstream4.debug==true){System.out.println("looping2");}
							}

							getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigmfreq});
							getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigmfreq});
							getstream4.flrigfreq = getstream4.flrigmfreq;
						}
						if (getstream4.usabled.contains("|website")) {
							
							cmdoption = "";
							if (getstream4.curbuf == 0) {
								if (getstream4.usabled.contains("|website ")) {
									cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|website ")+9);
									getstream4.option = 1;
								}
								else {
									getstream4.option = 1;
									getstream4.encodedString = "Please provide the exact URL of the website you want to fetch. Example: 'https://www.example.com'. If you want the raw HTML from the website, please add a carat (^) behind the start of the URL. Example: '^https://www.example.com'.";
				                    stuff.transmit();
								}

			                    
							}
							getstream4.usabled = "";
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							
					}
					if (getstream4.usabled.contains("|search")) {
						cmdoption = "";
						if (getstream4.curbuf == 0) {
							if (getstream4.usabled.contains("|search ")) {
								cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|search ")+8);
								getstream4.option = 2;
							}
							else {
								getstream4.option = 2;
								getstream4.encodedString = "Please provide your query.";
								stuff.transmit();
							}

							
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|weather")) {
						cmdoption = "";
						if (getstream4.curbuf == 0) {
							if (getstream4.usabled.contains("|weather ")) {
								cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|weather ")+9);
								getstream4.option = 3;
							}
							else {
								getstream4.option = 3;
								getstream4.encodedString = "Please provide the city and state you would like the weather for in the format of 'city state'. If the command returns blank, try reformatting your query.";
								stuff.transmit();
							}

								
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|info")) {
						cmdoption = "";
						if (getstream4.curbuf == 0) {
							getstream4.option = 4;
							String totserv = "";
							int actservs = 0;
			                try {
			                	

									totserv = stuff.backend("gc", "activeservers");



			                }
			                catch (Exception e) { totserv = e.toString(); }
			                if (totserv.contains("\n")) {
			                	actservs = totserv.split("\n").length;
			                }
			                else {
			                	actservs = 1;
			                }
							String uptimestring = (float)((float)(System.currentTimeMillis() - starttime)/(float)86400000) + " days (" + ((System.currentTimeMillis() - starttime)) + " milliseconds)";
							String logstring = getstream4.logs;
							String backendstats = stuff.backend("gc", "backendstatus");
							if (getstream4.termconnect == false && getstream4.logs.length() > 1000) {
								logstring = getstream4.logs.substring(getstream4.logs.length()-1000);
								int thing = logstring.indexOf("\n");
								logstring = logstring.substring(thing+1);
							}
							getstream4.encodedString = "Total connections: " + stuff.totalconnections + "\nUptime: " + uptimestring + "\nServer version: " + getstream4.softver + "\n\nGlobal active servers: "+actservs+"\n-----\n" + totserv + "-----\n\nMost recent logs:\n-----\n" + logstring + "-----\n\nBackend status:\n-----\n" + backendstats + "\n-----\n" + stuff.commandslistshort;
	                        stuff.transmit();
							getstream4.option = 0;
							
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|download")) {
						cmdoption = "";

							if (getstream4.curbuf == 0) {
								if (getstream4.usabled.contains("|download ")) {
									cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|download ")+10);
									getstream4.option = 5;
								}
								else {
									getstream4.option = 5;
									getstream4.encodedString = "Please provide the URL of the file you would like to download.";
									stuff.transmit();
								}

								
							}
							getstream4.usabled = "";
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;

							
					}
					if (getstream4.usabled.contains("|forum")) {
						cmdoption = "";

						if (getstream4.curbuf == 0) {
							wstext = "";
							if (getstream4.usabled.contains("|forum ") && getstream4.usabled != "|forum ") {
								getstream4.option = 6;
								cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|forum ")+7);
								getstream4.usabled = "";
								

							}
							else {
							getstream4.dataoutp = "";
			                try {

									wstext = stuff.backend("gc", "forumindex");

			                }
			                catch (Exception e) { wstext = e.toString(); }
			                String lesswstext = "";
			                int i2=0;
			                while (i2 < wstext.split("\n").length) {
			                	if(getstream4.debug==true){System.out.println("looping3");}
			                	if (i2 < 25) {
			                		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
			                	}
			                	i2 = i2 + 1;
			                }
			                wstext = lesswstext;
			                	wstext = wstext.replaceAll("\\r", "");
			                    getstream4.encodedString = wstext + "\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the forum area?\n(view can be optionally followed by the number of lines from what end you wish to print, examples: 'the-test-thread last25', 'first-thread all', 'my-qsl-card first5'. Defaults to firstall.)\n";
			               stuff.transmit();
							getstream4.option = 6;
							}
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|home")) {
						cmdoption = "";
						if (getstream4.curbuf == 0) {
							stuff.connectinit(false);
							//getstream4.encodedString = stuff.homestring;
							//stuff.transmit();


								//getstream4.option = 0;
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|chat")) {
						cmdoption = "";
						if (getstream4.usabled.contains("|chat ") && getstream4.usabled != "|chat ") {
							getstream4.option = 10;
							cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|chat ")+6);
						}
						else {
							if (getstream4.curbuf == 0) {
								getstream4.encodedString = "Say a callsign to open your messages with someone (optionally followed by the number of lines from what end you wish to print, examples: 'KJ7QQG first25', 'W7JSP all', 'N7JSP last10'. Defaults to last3.) or say 'list' to show all your previous chats.";
								stuff.transmit();
								getstream4.option = 10;
							}
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
					if (getstream4.usabled.contains("|charlimit")) {
						cmdoption = "";
						if (getstream4.usabled.contains("|charlimit ") && getstream4.usabled != "|charlimit ") {
							cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("|charlimit ")+11);
							int testnumber = -1;
							try {
							testnumber = Integer.parseInt(cmdoption);
							} catch (Exception e) {
								if (getstream4.curbuf == 0) {
									getstream4.encodedString = "Add a number after |charlimit to set the maximum length for each response from the server. For example, say |charlimit 1000 to limit all responses to their first 1000 characters. Setting to 0 or less disables the limit." + stuff.commandslistshort;
									stuff.transmit();
								}
								if(getstream4.debug==true){e.printStackTrace();}
							}
							if (testnumber <= 0) {
								stuff.charlimit = 0;
								if (getstream4.curbuf == 0) {
									getstream4.encodedString = "Disabled the response length limit." + stuff.commandslistshort;
									stuff.transmit();
								}
							}
							else {
								stuff.charlimit = testnumber;
								if (getstream4.curbuf == 0) {
									getstream4.encodedString = "Now limiting responses to the first " + stuff.charlimit + " characters." + stuff.commandslistshort;
									stuff.transmit();
								}
							}
							
						}
						else {
							if (getstream4.curbuf == 0) {
								getstream4.encodedString = "Add a number after |charlimit to set the maximum length for each response from the server. For example, say |charlimit 1000 to limit all responses to their first 1000 characters. Setting to 0 or less disables the limit." + stuff.commandslistshort;
								stuff.transmit();
							}
						}
						getstream4.usabled = "";
						stuff.interactiontimeout = System.currentTimeMillis() + 300000;
					}
						if (getstream4.option == 1) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								if (getstream4.usabled.contains("^")) {
									getstream4.dataoutp = "Here is the raw HTML from the website you provided.\n-----\n";
									getstream4.usabled = getstream4.usabled.replace("^", "");
									getstream4.usabled = getstream4.usabled.replaceAll("\\r|\\n", "");
									
									try {
									wstext = Jsoup.parse(new URI(getstream4.usabled).toURL(), 10000).html().toString();
									} catch (Exception e) { wstext = e.toString(); }
			                        
			                        //int ind = 0;
			                        //while (ind < badwords.size()) {
			                        	//if(getstream4.debug==true){System.out.println("looping4");}
			                        	//wstext = wstext.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
			                        	
			                        	//ind = ind + 1;
			                        //}
			                        //wstext = wstext.replaceAll("porn", "[!]");
			                        getstream4.encodedString = getstream4.dataoutp + wstext + "\n-----" + stuff.commandslistshort;
		                            getstream4.logs = getstream4.logs + getstream4.rcall + " fetched html from URL " + getstream4.usabled + "\n";							
			                        stuff.transmit();                      
								}
								else {
									getstream4.dataoutp = "Here is the text from the website you provided.\n-----\n";
									
									getstream4.usabled = getstream4.usabled.replace("^", "");
									getstream4.usabled = getstream4.usabled.replaceAll("\\r|\\n", "");
									
									try {
									wstext = Jsoup.parse(new URI(getstream4.usabled).toURL(), 10000).wholeText();
									} catch (Exception e) { wstext = e.toString(); }
			                        wstext = wstext.replaceAll("	", " ");
			                        while (wstext.contains("  ")) {
				                        wstext = wstext.replaceAll("  ", " ");
			                        }
			                        while (wstext.contains("\n \n")) {
			                        	wstext = wstext.replaceAll("\n \n", "\n");
			                        }
			                        while (wstext.contains("\n\n")) {
			                        	wstext = wstext.replaceAll("\n\n", "\n");
			                        }
			                        //int ind = 0;
			                        //while (ind < badwords.size()) {
			                        	//if(getstream4.debug==true){System.out.println("looping4");}
			                        	//wstext = wstext.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
			                        	
			                        	//ind = ind + 1;
			                        //}
			                        //wstext = wstext.replaceAll("porn", "[!]");
			                        getstream4.encodedString = getstream4.dataoutp + wstext + "\n-----" + stuff.commandslistshort;
			                        getstream4.logs = getstream4.logs + getstream4.rcall + " fetched text from URL " + getstream4.usabled + "\n";
			                        stuff.transmit();
									
			                        
								}
								stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
						}
						if (getstream4.option == 2) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								getstream4.dataoutp = "Here are the results of your search.\n-----\n";
								searchthing = getstream4.usabled;
								getstream4.usabled = URLEncoder.encode(getstream4.usabled, StandardCharsets.UTF_8);
								getstream4.usabled = getstream4.usabled.replaceAll("\\+", "%20");
								getstream4.usabled = "https://duckduckgo.com/html/?q=" + getstream4.usabled + "&kp=1&kz=-1&kc=-1&kav=1&kac=-1&kd=-1&ko=-2&k1=-1";
								try {
									wstext = Jsoup.parse(new URI(getstream4.usabled).toURL(), 10000).wholeText();
									} catch (Exception e) { wstext = e.toString(); }
			                        wstext = wstext.replaceAll("	", " ");
			                        while (wstext.contains("  ")) {
				                        wstext = wstext.replaceAll("  ", " ");
			                        }
			                        while (wstext.contains("\n \n")) {
			                        	wstext = wstext.replaceAll("\n \n", "\n");
			                        }
			                        while (wstext.contains("\n\n")) {
			                        	wstext = wstext.replaceAll("\n\n", "\n");
			                        }
			                        wstext = wstext.substring(wstext.indexOf("Past Month\n Past Year")+21);
		                        //int ind = 0;
		                        //while (ind < badwords.size()) {
		                        	//if(getstream4.debug==true){System.out.println("looping6");}
		                        	//wstext = wstext.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
		                        	//ind = ind + 1;

		                        //}
		                        //wstext = wstext.replaceAll("porn", "[!]");
		                        getstream4.encodedString = getstream4.dataoutp + wstext + "\n-----" + stuff.commandslistshort;
		                        getstream4.logs = getstream4.logs + getstream4.rcall + " searched for " + searchthing + "\n";

			                    stuff.transmit();
			                    stuff.interactiontimeout = System.currentTimeMillis() + 300000;
								
							}
						}
						if (getstream4.option == 3) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								getstream4.dataoutp = "Here is the raw weather forecast data for the location you provided.\n-----\n";
								weatherbroke = false;
								weatherend = "";
								String address = getstream4.usabled;
								weatherthing = getstream4.usabled;
						        String key = stuff.weatherkey;
						        try{
						            String url = "https://geocode.maps.co/search?q=" + URLEncoder.encode(address, "UTF-8") + "&api_key=" + key;
						            URL obj = new URI(url).toURL();
						            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

						            con.setRequestMethod("GET");
						            con.setRequestProperty("User-Agent", "Mozilla/5.0");

						            BufferedReader in = new BufferedReader(
						                    new InputStreamReader(con.getInputStream()));
						            String inputLine;
						            StringBuffer response = new StringBuffer();
						            while ((inputLine = in.readLine()) != null) {
						            	if(getstream4.debug==true){System.out.println("looping7");}
						                response.append(inputLine);
						            }
						            in.close();

						            String latcheck = response.substring(response.indexOf(",\"lat\":\"")+8, response.indexOf("\",\"lon\":\""));
						            String lngcheck = response.substring(response.indexOf("\",\"lon\":\"")+9, response.indexOf("\",\"class\":\""));
						            //System.out.println("latcheck: " + latcheck);
						            //System.out.println("lngcheck: " + lngcheck);

						            //Pattern pattern = Pattern.compile("\"location\" : \\{(.*?)\\}");
						            //Matcher matcher = pattern.matcher(response.toString());
						         //   if (matcher.find())
						          //  {
						                //String latLngString = matcher.group(1).replaceAll("\\s", "");
						                //String[] latLngArray = latLngString.split(",");
						                //String lat = latLngArray[0].split(":")[1];
						               // String lng = latLngArray[1].split(":")[1];
						                latLng = new String[]{latcheck,lngcheck};
						         //   }
						         //   else {
						            //	weatherbroke = true;
						         //   }
						        }catch (Exception e) { weatherbroke = true;
					        	weatherend = weatherend + e.toString(); }
						        if (weatherbroke == false) {
						        	try{

							            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latLng[0] + "&longitude=" + latLng[1] + "&daily=temperature_2m_min,temperature_2m_max,precipitation_sum,precipitation_probability_max,wind_gusts_10m_max,wind_direction_10m_dominant,wind_speed_10m_max"; //update for international weather
							            URL obj = new URI(url).toURL();
							            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
							            
							            con.setRequestMethod("GET");
							            con.setRequestProperty("User-Agent", "Mozilla/5.0");



							            BufferedReader in = new BufferedReader(
							                    new InputStreamReader(con.getInputStream()));
							            String inputLine;
							            StringBuffer response = new StringBuffer();
							            while ((inputLine = in.readLine()) != null) {
							            	if(getstream4.debug==true){System.out.println("looping8");}
							                response.append(inputLine);
							            }
							            in.close();
							           
							            weatherend = response.toString().replaceAll(",", "\n").replaceAll("\\[", "\n").replaceAll("\\]", "\n").replaceAll("\\{", "\n").replaceAll("\\}", "\n");
							        }catch (Exception e) { weatherend = weatherend + e.toString(); }
						        }
						        //weatherend = weatherend.replaceAll("[^A-Za-z .0-9]", "");
						        //weatherend = weatherend.replaceAll(" ", "\n");
						        //weatherend = weatherend.replaceAll("\n\n", "\n");
						        //weatherend = weatherend.replaceAll("\n\n", "\n");
						        //weatherend = weatherend.replaceAll("\n\n", "\n");
						        wstext = weatherend;
		                        //int ind = 0;
		                        //while (ind < badwords.size()) {
		                        	//if(getstream4.debug==true){System.out.println("looping10");}
		                        	//wstext = wstext.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
		                        	//ind = ind + 1;
		                        //}
		                        //wstext = wstext.replaceAll("porn", "[!]");
		                        getstream4.encodedString = getstream4.dataoutp + wstext + "\n-----" + stuff.commandslistshort;
		                        getstream4.logs = getstream4.logs + getstream4.rcall + " got the weather for " + weatherthing + "\n";

			                    stuff.transmit();
			                    stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
						}
						if (getstream4.option == 5) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								try {
										InputStream in = new URI(getstream4.usabled).toURL().openStream();
										Files.copy(in, Paths.get("tempdownload"), StandardCopyOption.REPLACE_EXISTING);
										byte[] fileContent = FileUtils.readFileToByteArray(new File("tempdownload"));

											getstream4.encodedString = Base64.getEncoder().encodeToString(fileContent);
											getstream4.logs = getstream4.logs + getstream4.rcall + " downloaded " + getstream4.usabled + "\n";

								}
								catch (Exception e) { e.printStackTrace();
		                        getstream4.encodedString = e.toString(); }

								
								getstream4.encodedString = "This is your file, encoded in base64. You can decode it by copying the text below, saving the text as 'data.b64' then running 'certutil -decode data.b64 downloadedfile' in the command line in Windows or 'base64 -d data.b64 > downloadedfile' in Linux.\n-----\n" + getstream4.encodedString + "\n-----" + stuff.commandslistshort;

			                   stuff.transmit();
			                   stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
						}
						if (getstream4.option == 6) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
									if (getstream4.usabled.contains("view")) {
										if (getstream4.usabled.contains("view ")) {
											cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("view ")+5);
											getstream4.option = 7;
											getstream4.usabled = "";
										}
										else {
											getstream4.encodedString = "Please send the name of the thread you would like to view (optionally followed by the number of lines from what end you wish to print, examples: 'the-test-thread last25', 'first-thread all', 'my-qsl-card first5'. Defaults to firstall.) (or, send '|all' to list all threads)\n";
											stuff.transmit();
											getstream4.option = 7;
											getstream4.usabled = "";
										}

									}
									if (getstream4.usabled.contains("create")) {
										//create posts
										if (getstream4.usabled.contains("create ")) {
											cmdoption = getstream4.usabled.substring(getstream4.usabled.indexOf("create ")+7);
											getstream4.option = 8;
											getstream4.usabled = "";
										}
										else {
											getstream4.encodedString = "Please send the name of the thread you would like to create (or leave a comment on).";
											stuff.transmit();
											getstream4.option = 8;
											getstream4.usabled = "";
										}


									}
									stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
						}
						if (getstream4.option == 7) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "") {
								if (getstream4.usabled.contains("|")) {
									if (getstream4.usabled.contains("|all")) {
										//view posts
										getstream4.dataoutp = "Please send the name of the thread you would like to view (optionally followed by the number of lines from what end you wish to print, examples: 'the-test-thread last25', 'first-thread all', 'my-qsl-card first5'. Defaults to firstall.)\n";
										searchthing = getstream4.usabled;


											wstext = stuff.backend("gc", "forumindex");


				                        	wstext = wstext.replaceAll("\\r", "");
				                            getstream4.dataoutp = getstream4.dataoutp + wstext + "\n-----" + stuff.commandslistshort;
				                            getstream4.logs = getstream4.logs + getstream4.rcall + " looked at the entire forum index page\n";

				                        getstream4.encodedString = getstream4.dataoutp;
				                        

				                        stuff.transmit();
									}
								}
								else {
									int lines = -1;
									int totlines = 0;
									boolean ltype = false;
									if (getstream4.usabled.contains(" ")) {
										String[] uds = getstream4.usabled.split(" ");
										if (uds.length == 2) {
											uds[0].replaceAll(" ", "");
											uds[1].replaceAll(" ", "");
											if (uds[1].contains("first") || uds[1].contains("top")) {
												uds[1] = uds[1].replaceAll("first", "");
												uds[1] = uds[1].replaceAll("top", "");
												ltype = false;
											}
											if (uds[1].contains("last") || uds[1].contains("bottom")) {
												uds[1] = uds[1].replaceAll("last", "");
												uds[1] = uds[1].replaceAll("bottom", "");
												ltype = true;
											}
											try {
												lines = Integer.parseInt(uds[1]);
												if (lines < 1) {
													lines = -1; // some smart aleck is gonna think this overflowed or some shit
												}
											} catch (Exception e) { System.out.print(""); }
											if (uds[1].contains("all")) {
												lines = -1;
											}
											getstream4.usabled = uds[0];
										}
										else {
											getstream4.usabled = getstream4.usabled.replaceAll(" ", "");
										}
									}
									getstream4.dataoutp = "";
									searchthing = getstream4.usabled;
									stuff.backend("cln", getstream4.rcall + stuff.rwskey2 + stuff.rwskey1 + "forum-" + searchthing);
										getstream4.usabled = URLEncoder.encode(getstream4.usabled, StandardCharsets.UTF_8);
										getstream4.usabled = getstream4.usabled.replaceAll("\\+", "%20");
										wstext = stuff.backend("gc", "forum/"+getstream4.usabled);
								
				                        if (lines != -1) {
				                        	String[] wstextlines = wstext.split("\n");
				                        	totlines = wstextlines.length;
				                        	if (lines < totlines) {
					                        	String pwstext = "";
				                        		if (ltype == true) {
				                        			//from the bottom
						                        	getstream4.dataoutp = "Only printing the last " + lines + " out of " + totlines + " lines.\n-----\n";
						                        	int startline = totlines - lines;
						                        	for (int zz = startline; zz < totlines; zz++) {
						                        		if(getstream4.debug==true){System.out.println("loopingfor3");}
						                        		pwstext = pwstext + wstextlines[zz] + "\n";
						                        		if (zz == totlines) {
						                        			pwstext = pwstext.substring(0, pwstext.length()-1);
						                        		}
						                        	}
				                        		}
				                        		else {
				                        			// from the top
						                        	getstream4.dataoutp = "Only printing the first " + lines + " out of " + totlines + " lines.\n-----\n";
						                        	for (int zz = 0; zz < lines; zz++) {
						                        		if(getstream4.debug==true){System.out.println("loopingfor4");}
						                        		pwstext = pwstext + wstextlines[zz] + "\n";
						                        		if (zz == lines) {
						                        			pwstext = pwstext.substring(0, pwstext.length()-1);
						                        		}
						                        	}
				                        		}
					                        	wstext = pwstext;
				                        	}
				                        }

			                        	wstext = wstext.replaceAll("\\r", "");
			                            getstream4.dataoutp = getstream4.dataoutp + wstext + "\n-----\n";
			                            getstream4.logs = getstream4.logs + getstream4.rcall + " looked at the thread " + searchthing + "\n";

			                            wstext = stuff.backend("gc", "forumindex");
			                        String lesswstext = "";
			                        int i2=0;
			                        while (i2 < wstext.split("\n").length) {
			                        	if(getstream4.debug==true){System.out.println("looping11");}
			                        	if (i2 < 25) {
			                        		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
			                        	}
			                        	i2 = i2 + 1;
			                        }
			                        wstext = lesswstext;
			                        	wstext = wstext.replaceAll("\\r", "");
			                            getstream4.dataoutp = getstream4.dataoutp + "\n" + wstext + "\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the forum area?\n(view can be optionally followed by the number of lines from what end you wish to print, examples: 'view the-test-thread last25', 'view first-thread all', 'view my-qsl-card first5'. Defaults to firstall.)" + stuff.commandslistshort;
			                        getstream4.encodedString = getstream4.dataoutp;
			                        

			                        stuff.transmit();
									getstream4.option = 6;

								}
								stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
						}
						if (getstream4.option == 8) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								if (getstream4.usabled.contains(".") || getstream4.usabled.contains("~") || getstream4.usabled.contains("|") || getstream4.usabled.contains("/") || getstream4.usabled.contains("\\") || getstream4.usabled.contains(" ") || getstream4.usabled.length() > 100) {
									getstream4.encodedString = "That title is invalid. Make sure your title does not include ' ', '.', '~', '/', '\' or '|' and is not longer than 100 characters.\r";
									stuff.transmit();
									getstream4.usabled = "";

								}
								else {


									postname = getstream4.usabled;
									getstream4.encodedString = "Please send the body of the thread/comment you would like to create.\r";
									stuff.transmit();
									getstream4.option = 9;
									getstream4.usabled = "";

								}
								stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
							
						}
						if (getstream4.option == 9) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								//boolean postsafe = true;
								getstream4.dataoutp = "";
								postbody = getstream4.usabled;
		                        //int ind = 0;
		                        //while (ind < badwords.size()) {
		                        	//if(getstream4.debug==true){System.out.println("looping12");}
		                        	//postname = postname.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
		                        	
		                        	//ind = ind + 1;
		                        //}
		                        //postname = postname.replaceAll("porn", "[!]");
		                        
		                        //ind = 0;
		                        //while (ind < badwords.size()) {
		                        	//if(getstream4.debug==true){System.out.println("looping13");}
		                        	//postbody = postbody.replaceAll("(?i)[^a-zA-Z]"+Pattern.quote(badwords.get(ind))+"[^a-zA-Z]", " [!] ");
		                        	
		                        	//ind = ind + 1;
		                        //}
		                        //postbody = postbody.replaceAll("porn", "[!]");

			                    //if (postsafe == true) {
									getstream4.logs = getstream4.logs + getstream4.rcall + " requested an upload for a thread with title '" + postname + "'\n";
									


									        	try {
									        		

										            Instant instantp = Instant.now();


										            ZoneId zonep = ZoneId.of("GMT");


										            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
										            String dateTime = instantp.atZone(zonep).format(formatter);
										            stuff.backend("wfb", postname + stuff.rwskey2 + stuff.rwskey1 + "\n"+getstream4.rcall+", "+dateTime+" UTC:"+"\n"+postbody+"\n");
										            // figure out who to send notifications to
										            String nwstext = stuff.backend("gc", "forum/"+postname);
										            String[] nwstexts = nwstext.split("\n");
										            nwstext = nwstexts[1]; // just the list of callsigns in the thread
										            //System.out.println("\n\n\n" + nwstext + "\n\n\n");
										            nwstexts = nwstext.split(", ");
										            for (String nperson : nwstexts) {
										            	nperson = nperson.replaceAll(" ", "").replaceAll("\n", "");
										            		if (!nperson.equals(getstream4.rcall)) {
										            			stuff.backend("an", nperson + stuff.rwskey2 + stuff.rwskey1 + "forum-" + postname);
										            		}
												            
										            }


									          } catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }

									        try {

									          
										            Instant instant = Instant.now();


										            ZoneId zone = ZoneId.of("GMT");


										            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
										            String dateTime = instant.atZone(zone).format(formatter);
										       stuff.backend("wfi", "'"+postname+"'"+" - "+getstream4.rcall+", "+dateTime+" UTC\n");
									          } catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }


											getstream4.option = 6;
											getstream4.usabled = "";
			                    //}
										

					                            wstext = stuff.backend("gc", "forumindex");
				                        String lesswstext = "";
				                        int i2=0;
				                        while (i2 < wstext.split("\n").length) {
				                        	if(getstream4.debug==true){System.out.println("looping15");}
				                        	if (i2 < 25) {
				                        		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
				                        	}
				                        	i2 = i2 + 1;
				                        }
				                        wstext = lesswstext;
				                        	wstext = wstext.replaceAll("\\r", "");
				                        	//if (postsafe == true) {
					                        	getstream4.dataoutp = wstext + "\nYour thread/comment has been submitted for processing!\n\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the forum area?\n(view can be optionally followed by the number of lines from what end you wish to print, examples: 'the-test-thread last25', 'first-thread all', 'my-qsl-card first5'. Defaults to firstall.)" + stuff.commandslistshort;
				                        	//}
				                        	//else {
				                        		//getstream4.dataoutp = wstext + "\nOops, that contained material that is inappropriate for ham radio. Please try something else.\n\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the forum area?\n(view can be optionally followed by the number of lines from what end you wish to print, examples: 'the-test-thread last25', 'first-thread all', 'my-qsl-card first5'. Defaults to firstall.)\nCommands: |website |search |weather |download |forum |chat |info |help";
				                        	//}

				                        getstream4.encodedString = getstream4.dataoutp;


				                        stuff.transmit();
										getstream4.option = 6;


										stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							}
							
							getstream4.usabled = "";
						}
						if (getstream4.option == 10) {
							if (cmdoption != "") {
								getstream4.usabled = cmdoption;
								cmdoption = "";
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|' && getstream4.curbuf == 0) {
								// look at the chat index
								getstream4.dataoutp = "";


				                        wstext = stuff.backend("gc", "chatindex");
				                
				                	wstext = wstext.replaceAll("\\r", "");
				                	
									String[] indexes = wstext.split("\n");

									chatlines = 0;
									if (getstream4.usabled.contains(" ")) {
										String[] uds = getstream4.usabled.split(" ");
										if (uds.length == 2) {
											uds[0].replaceAll(" ", "");
											uds[1].replaceAll(" ", "");
											if (uds[1].contains("first") || uds[1].contains("top")) {
												uds[1] = uds[1].replaceAll("first", "");
												uds[1] = uds[1].replaceAll("top", "");
												chatltype = true;
											}
											if (uds[1].contains("last") || uds[1].contains("bottom")) {
												uds[1] = uds[1].replaceAll("last", "");
												uds[1] = uds[1].replaceAll("bottom", "");
												chatltype = false;
											}
											try {
												chatlines = Integer.parseInt(uds[1]);
												if (chatlines < 0) {
													chatlines = -1;
												}
											} catch (Exception e) { System.out.print(""); }
											if (uds[1].contains("all")) {
												chatlines = -1;
											}
											getstream4.usabled = uds[0];
										}
										else {
											getstream4.usabled = getstream4.usabled.replaceAll(" ", "");
										}
									}
									
								if (getstream4.usabled.contains("list")) {
									//show prev chats
									String compindex = "";
									for (int ci = 0; ci < indexes.length; ci++) {
										if(getstream4.debug==true){System.out.println("loopingfor7");}
										  if (indexes[ci].contains(getstream4.rcall)) {
											  compindex = compindex + indexes[ci].replaceFirst(getstream4.rcall, "").replaceAll("\\+", "") + "\n"; // replaceFirst instead of replaceAll, so ppl can see if they have a chat with themselves
										  }
									}
										getstream4.encodedString = "Previous chats:\n-----\n" + compindex + "-----\nSay a callsign to open your messages with them (optionally followed by the number of lines from what end you wish to print, examples: 'KJ7QQG first25', 'W7JSP all', 'N7JSP last10'. Defaults to last0.) or say 'list' to show all your previous chats.";
										stuff.transmit();
									stuff.interactiontimeout = System.currentTimeMillis() + 300000;
								}
								else {
									//messages with callsign
									getstream4.usabled = getstream4.usabled.replaceAll("[^A-Za-z0-9-]", "").toUpperCase();
									if (getstream4.usabled.equals("") || getstream4.usabled.length() < 3) {
										//maybe this will fix the broken thing?
									}
									else {
										
										boolean chatfound = false;
										if (!getstream4.usabled.equals(getstream4.rcall)) {
											
											for (int ci = 0; ci < indexes.length; ci++) {
												if(getstream4.debug==true){System.out.println("loopingfor82");}
												  if (indexes[ci].contains(getstream4.rcall) && indexes[ci].contains(getstream4.usabled) && (indexes[ci].length() == getstream4.rcall.length()+getstream4.usabled.length()+1)) {
													  chatfound = true;
													  cmdoption = indexes[ci];
													  break;
												  }
											}
											
										}
										else {
											for (int ci = 0; ci < indexes.length; ci++) {
												if(getstream4.debug==true){System.out.println("loopingfor81");}
												  if (indexes[ci].contains(getstream4.rcall + "+" + getstream4.rcall) && (indexes[ci].length() == getstream4.rcall.length()+getstream4.rcall.length()+1)) {
													  chatfound = true;
													  cmdoption = indexes[ci];
													  break;
												  }
											}
										}
										if (chatfound == false) {
											getstream4.encodedString = "You have no previous messages with the callsign '" + getstream4.usabled + "'. Starting a new chat. Type to send a message.\n-----\n\r";
											cmdoption = getstream4.usabled;
											stuff.transmit();

										}
										openedchat = getstream4.usabled;
										getstream4.option = 11;
										getstream4.logs = getstream4.logs + getstream4.rcall + " opened the chat " + cmdoption + "\n";
									}

									stuff.interactiontimeout = System.currentTimeMillis() + 900000;
								}

							}
							stuff.inchat = false;
							lastchat = "";
							getstream4.usabled = "";
						}
						if (getstream4.option == 11 && getstream4.curbuf == 0) {
							if (stuff.inchat == false) {
								if (cmdoption.contains("+")) {
									//existing chat

					                try {


											
											wstext = stuff.backend("gc", "chat/"+cmdoption);

										//lastchat = wstext.substring(0, wstext.lastIndexOf(getstream4.rcall));

					                }
					                catch (Exception e) { wstext = e.toString(); lastchat = "";}
				                	
								}
								else {
									//new chat
									

			        				



			        				
			        		        // create string array called names
			        		        String namesas[]
			        		            = { cmdoption, getstream4.rcall };
			        		        String tempas;
			        		        for (int i = 0; i < 2; i++) {
			        		        	if(getstream4.debug==true){System.out.println("loopingfor9");}
			        		            for (int j = i + 1; j < 2; j++) {
			        		            	if(getstream4.debug==true){System.out.println("loopingfor10");}
			        		               
			        		                // to compare one string with other strings
			        		                if (namesas[i].compareTo(namesas[j]) > 0) {
			        		                    // swapping
			        		                    tempas = namesas[i];
			        		                    namesas[i] = namesas[j];
			        		                    namesas[j] = tempas;
			        		                }
			        		            }
			        		        }
			        		        if (!(namesas[0].equals(namesas[1]))) {
				        		        stuff.backend("an", cmdoption + stuff.rwskey2 + stuff.rwskey1 + "chat-" + getstream4.rcall);
			        		        }
			        				cmdoption = namesas[0] + "+" + namesas[1];
			        				
			        				stuff.backend("wnc", cmdoption);
			        				
									 
					    				
									lastchat = "---beginning of chat---\n";
								}
								stuff.inchat = true;
							}
							else {
								
				                try {

				                	wstext = stuff.backend("gc", "chat/"+cmdoption);
									stuff.backend("cln", getstream4.rcall + stuff.rwskey2 + stuff.rwskey1 + "chat-" + openedchat);
				                	Thread.sleep(2000); // prevent spamming the backend?
										
										

				                }
				                catch (Exception e) { wstext = "error"; }
				                try {
				                if ((!wstext.equals("error")) && wstext.length() != 0) {
				                	wstext = wstext.replaceAll("\\r", "");
				                	if (!wstext.equals(lastchat)) {
				                		//System.out.println(wstext);
				                		if (lastchat == "") {
					                		lastchat = wstext;
					                        if (chatlines != -1) {
					                        	String[] wstextlines = wstext.split("\n");
					                        	chattotlines = wstextlines.length;
					                        	if (chatlines != 0) {
						                        	if (chatlines < chattotlines) {
						                        		String pwstext = "";
						                        		if (chatltype == true) {
						                        			// from the top
						                        																	
								                        	wstext = "Opening your chat with " + openedchat + ". Type to send a message.\n" + "Only printing the first " + chatlines + " out of " + chattotlines + " lines.\n-----\n";
								                        	for (int zz = 0; zz < chatlines; zz++) {
								                        		if(getstream4.debug==true){System.out.println("loopingfor11");}
								                        		pwstext = pwstext + wstextlines[zz] + "\n";
								                        		if (zz == chatlines) {
								                        			pwstext = pwstext.substring(0, pwstext.length()-1);
								                        		}
								                        	}
						                        		}
						                        		else {
						                        			// from the bottom
								                        	wstext = "Opening your chat with " + openedchat + ". Type to send a message.\n" + "Only printing the last " + chatlines + " out of " + chattotlines + " lines.\n-----\n";
								                        	int startline = chattotlines - chatlines;
								                        	for (int zz = startline; zz < chattotlines; zz++) {
								                        		if(getstream4.debug==true){System.out.println("loopingfor12");}
								                        		pwstext = pwstext + wstextlines[zz] + "\n";
								                        		if (zz == chattotlines) {
								                        			pwstext = pwstext.substring(0, pwstext.length()-1);
								                        		}
								                        	}
						                        		}
							                        	wstext = wstext + pwstext;
						                        	}
					                        	}
					                        	else {
					                        		wstext = "Opening your chat with " + openedchat + ". Type to send a message.\n" + "Showing the last 3 lines of your chat.\n-----\n";
					                        		int startline = chattotlines - 3;
					                        		if (startline < 0) {
					                        			startline = 0;
					                        		}
					                        		String pwstext = "";
						                        	for (int zz = startline; zz < chattotlines; zz++) {
						                        		if(getstream4.debug==true){System.out.println("loopingfor13");}
						                        		pwstext = pwstext + wstextlines[zz] + "\n";
						                        		if (zz == chattotlines) {
						                        			pwstext = pwstext.substring(0, pwstext.length()-1);
						                        		}
						                        	}
						                        	wstext = wstext + pwstext;
					                        	}
					                        }
			                				wstext = wstext.replaceAll("<", ""); //
			                				wstext = wstext.replaceAll(">", ""); // remove formatting from historical messages, that tic tac toe game ended a week ago varac!
					                        getstream4.encodedString = wstext;
					                		stuff.transmit();
				                		}
				                		else {
				                			String othcall = "";
				                			if (cmdoption.contains("+"+getstream4.rcall)) {
				                				// otherperson+me
				                				othcall = cmdoption.substring(0, cmdoption.length()-(getstream4.rcall.length()+1));
				                			}
				                			else {
				                				// me+otherperson
				                				othcall = cmdoption.substring(getstream4.rcall.length()+1);
				                			}
				                			String pcsend = wstext.substring(lastchat.length());
				                			if (wstext.substring(lastchat.length()).contains(" UTC] "+othcall+" : ")) {
				                				// other person, no need to remove anything
				                			}
				                			else {
				                				// us, remove formatting
				                				pcsend = pcsend.replaceAll("<", "");
				                				pcsend = pcsend.replaceAll(">", "");
				                			}
				                			//pcsend = pcsend.substring(1);
					                		getstream4.encodedString = pcsend;
					                		stuff.transmit();
					                		lastchat = wstext;
				                		}

				                	}
				                }
				                else {
				                	wstext = "";
				                }
				                } catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
							}
							if (getstream4.usabled != "" && getstream4.usabled.charAt(0) != '|') {
								//send a chat -----
			        				

					    				try {
					    					
					    					stuff.backend("woc", cmdoption + stuff.rwskey2 + stuff.rwskey1 + getstream4.rcall + " : " + getstream4.usabled + "\n");
					    					if (!(cmdoption.replaceAll("\\+", "").replaceAll(getstream4.rcall, "").equals(getstream4.rcall) || cmdoption.replaceAll("\\+", "").replaceAll(getstream4.rcall, "").equals(""))) {
					    						stuff.backend("an", cmdoption.replaceAll("\\+", "").replaceAll(getstream4.rcall, "") + stuff.rwskey2 + stuff.rwskey1 + "chat-" + getstream4.rcall);
					        		        }

					    				} catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
					    				getstream4.logs = getstream4.logs + getstream4.rcall + " sent a chat to " + cmdoption + "\n";
					    				stuff.interactiontimeout = System.currentTimeMillis() + 900000;
							}
						}
						else {
							stuff.inchat = false;
						}
						if (stuff.modem == 3 || stuff.modem == 4) {
							if (System.currentTimeMillis() > stuff.interactiontimeout || getstream4.usabled.contains("|disc")) {
								getstream4.encodedString = "You have been disconnected from the server. Reconnect if you want to continue using RWS.";
								stuff.transmit();
								stuff.disconnect();
								
							}
						}

					}
					else {
						if (getstream4.flrig == true && getstream4.flrigfc == true && getstream4.flrigfreq == getstream4.flrigmfreq && getstream4.flrigsfreq != getstream4.flrigmfreq) {
							while (getstream4.ptt == true) {
								if(getstream4.debug==true){System.out.println("looping16");}
								Thread.sleep(10);
							}
							getstream4.flrigfreq = getstream4.flrigsfreq;
							getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigfreq});
							getstream4.client.execute("rig.set_frequency", new Object[]{getstream4.flrigfreq});
						}
						if (getstream4.usabled != "" && getstream4.option == 0) {
							if (stuff.modem != 5) {
								getstream4.encodedString = "Please enter your callsign. Remember to send all data between two sets of two equal signs. Example: ==KJ7QQG==";
							} else {
								getstream4.encodedString = "Please enter your callsign. Example: KJ7QQG";
							}
							
							stuff.transmit();
							stuff.interactiontimeout = System.currentTimeMillis() + 300000;
							getstream4.option = -1;
						}
						if (getstream4.option == -1 && getstream4.usabled != "") {
							getstream4.rcall = getstream4.usabled.replaceAll("\n", "");
							if (getstream4.rcall.length() > 10) {
								getstream4.rcall = "";
								getstream4.option = 0;

							} else {
								getstream4.option = 0;
								stuff.connectinit(true);
							}

						}
						if (getstream4.option == -1 && System.currentTimeMillis() > stuff.interactiontimeout) {
							getstream4.option = 0;
							getstream4.rcall = "";
						}
					}
					// main loop things end
					
					// more vara stuff
					if (stuff.modem == 1) {
						if (getstream4.initwait > 30) {
							
							if (getstream4.bwcheck == true) {
								if (getstream4.lbm == false) {
									getstream4.cmdsoutp = "BW2300\r";
								}
								else {
									getstream4.cmdsoutp = "BW500\r";
								}
								
								getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
								try {
									//if (stuff.modem == 1) {
										getstream4.cmdsout.write(getstream4.cmdsdata);
									//}
								} catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
							}
							getstream4.initwait = 2;
							if (getstream4.termconnect == true) {
								getstream4.cmdsoutp = "DISCONNECT\r";
								getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
								try {
									//if (stuff.modem == 1) {
										getstream4.cmdsout.write(getstream4.cmdsdata);
									//}

								} catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
							}

							if (System.currentTimeMillis() >= getstream4.cqresp && getstream4.cqresp != 0) {
								if (getstream4.conn == false) {
									if (getstream4.cqbw.contains("500")) {
										getstream4.cmdsoutp = getstream4.cmdsoutp + getstream4.cqbw;
										getstream4.cqbw = "";
									}

									getstream4.cmdsoutp = getstream4.cmdsoutp + "CONNECT " + getstream4.callsign + " " + getstream4.cqrespcall + "\r";
									getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
									try {
										if (stuff.modem == 1) {
											getstream4.cmdsout.write(getstream4.cmdsdata);
										}

									} catch (Exception e) { System.out.print("");if(getstream4.debug==true){e.printStackTrace();} }
									getstream4.cqresp = 0;
									getstream4.cqwaittime = 130;
								}
								else {
									getstream4.cqresp = 0;
									getstream4.cqwaittime = 130;
								}

							}


						}
						else {
							getstream4.initwait = getstream4.initwait + 1;
						}

						getstream4.lastresponse = System.currentTimeMillis();
						//System.out.println(getstream4.lastresponse);
						//Thread.sleep(100);
						//if (getstream1.cfgci == true) {
							if (getstream4.debug == true && getstream4.usablec != "") {
								System.out.println("usablec: '" + getstream4.usablec + "'");
							}
							if (getstream4.usablec.length() > getstream4.usablec.lastIndexOf("BUFFER")+6) {
								if (getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+6) == ' ') {
									int thing = 7;
									String thingcounter = "";
									while (Character.isDigit(getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+thing))) {
										thingcounter = thingcounter + getstream4.usablec.charAt(getstream4.usablec.lastIndexOf("BUFFER")+thing);
										thing = thing + 1;
										if(getstream4.debug==true){System.out.println("looping1");}
									}
									if (thingcounter != "") {
										getstream4.curbuf = Integer.parseInt(thingcounter);
									}
								}
							}
						if (getstream4.usablec.contains("REGISTERED " + getstream4.callsign)) {
							getstream4.varalicensed = true;
						}
						if (getstream4.usablec.contains("SN ")) {
							String snusablec = getstream4.usablec.substring(getstream4.usablec.lastIndexOf("SN "));
							snusablec = snusablec.substring(0, snusablec.indexOf("\r"));
							String[] sncqframestrings = snusablec.split(" ");
							sncqframestrings[1] = sncqframestrings[1].replaceAll(",", ".");
							recentsn = Float.parseFloat(sncqframestrings[1]);
						}
						if (getstream4.usablec.contains("CQFRAME") && !(getstream4.usablec.contains("-9"))) {
							String dummyload = "";
				            try {
									dummyload = Jsoup.parse(new URI("https://www.google.com").toURL(), 10000).wholeText();

				            }
				            catch (Exception e) { 
				                e.printStackTrace();
				               dummyload = e.toString();
				               stuff.intaccess = false;
				            }
				            if (dummyload.contains("Privacy")) {
				            	stuff.intaccess = true;
				            }
							if (getstream4.conn == false && stuff.intaccess == true) {
								String pcqbw = "";
								getstream4.cmdsoutp = "";
								String cqusablec = getstream4.usablec.substring(getstream4.usablec.lastIndexOf("CQFRAME"));
								String[] cqcqframestrings = cqusablec.split("\r");
									if (cqcqframestrings[0].contains(" 500") && getstream4.lbm == false) {
										pcqbw = "BW500\r";
									}
									if (cqcqframestrings[0].contains(" 2300") && getstream4.lbm == true) {
										pcqbw = "BW500\r";
									}
									String[] cqrx = cqcqframestrings[0].split(" ");
									String[] cqrxcall = cqrx[1].split("-");
								float pcqwaittime = 0;
								long pcqresp = 0;
								String pcqrespcall = cqrxcall[0];
								
								int ind = 0;
								boolean fc = false;
								
		                        while (ind < getstream4.bannedcalls.size()) {
		                        	if(getstream4.debug==true){System.out.println("loopingbc");}
		                        	if (getstream4.bannedcalls.get(ind).contains(pcqrespcall)) {
		                        		fc = true;
		                        		break;
		                        	}
		                        	ind = ind + 1;
		                        }
								if (fc == false) { // banned calls
									if (getstream4.varalicensed == false && recentsn >= -10) {
										// limited response
										pcqwaittime = (-2*recentsn)+55;
										if (pcqwaittime < 75) {
											pcqwaittime = (float) (75 + (Math.random()*5));
										}
										pcqresp = (long) (System.currentTimeMillis()+(pcqwaittime*1000));
										getstream4.logs = getstream4.logs + "CQ heard from " + cqrxcall[0] + ", vara may not be licensed, S/N " + recentsn + ", waiting " + pcqwaittime + " sec before response\n";
									}
									else {
										// normal response
										pcqwaittime = (-2*recentsn)+50;
										if (pcqwaittime < 10) {
											pcqwaittime = (float) (10 + (Math.random()*5));
										}
										pcqresp = (long) (System.currentTimeMillis()+(Math.round(pcqwaittime*1000)));
										getstream4.logs = getstream4.logs + "CQ heard from " + cqrxcall[0] + ", S/N " + recentsn + ", waiting " + pcqwaittime + " sec before response\n";
									}
								if (pcqwaittime < getstream4.cqwaittime) {
									getstream4.cqwaittime = pcqwaittime;
									getstream4.cqresp = pcqresp;
									getstream4.cqrespcall = pcqrespcall;
									getstream4.cqbw = pcqbw;
								}
								else {
									getstream4.logs = getstream4.logs + "Above CQ was ignored because it was the same or weaker than the CQ we are waiting to respond to\n";
								}
								}
								else {
									getstream4.logs = getstream4.logs + "CQ heard from " + cqrxcall[0] + ", not responding because he's banned\n";
								}



							}
						}
						if (getstream4.usablec.contains("CONNECTED") && getstream4.termconnect == false) {
							boolean actualconnection = false;
							if (!(getstream4.usablec.contains("DISCONNECTED")) || (getstream4.usablec.lastIndexOf("CONNECTED") > (getstream4.usablec.lastIndexOf("DISCONNECTED") + 3))) {
								actualconnection = true;
							}
							if ((getstream4.usablec.contains("DISCONNECTED")) && (getstream4.usablec.lastIndexOf("CONNECTED") <= (getstream4.usablec.lastIndexOf("DISCONNECTED") + 3))) {
								actualconnection = false;
							}
							if (actualconnection == true) {
								String rcallcut = getstream4.usablec.substring(getstream4.usablec.lastIndexOf("CONNECTED"),getstream4.usablec.length());
								String[] rcallcutcall = rcallcut.split(" ");
								
								getstream4.rcall = "PERSON";
								for (int rcind = 0; rcind < rcallcutcall.length; rcind = rcind + 1) {
									if(getstream4.debug==true){System.out.println("loopingfor1");}
									if (rcallcutcall[rcind].contains(getstream4.callsign)) {
										if (rcind == 1) {
											getstream4.rcall = rcallcutcall[2].replaceAll("[^a-zA-Z0-9 -]", "").toUpperCase();
										}
										if (rcind == 2) {
											getstream4.rcall = rcallcutcall[1].replaceAll("[^a-zA-Z0-9 -]", "").toUpperCase();
										}
									}
								}
								stuff.connectinit(true);
								
							}
							else {
								if (getstream4.conn == true) {
									stuff.disconnect();
									getstream4.conn = false;
									getstream1.gcmdsin = getstream1.gcmdsin.replaceAll("DISCONNECTED", "");
									getstream4.option = 0;
									stuff.charlimit = 0;
									getstream4.dataoutp = "";
									getstream4.cmdsoutp = "CLEANTXBUFFER\r";
									getstream4.cmdsdata = getstream4.cmdsoutp.getBytes();
									if (stuff.modem == 1) {
										getstream4.cmdsout.write(getstream4.cmdsdata);
									}
									
								}
								getstream4.bwcheck = true; getstream1.dkill = false;
							}
							getstream1.dkill = false;

						}
						getstream1.gcmdsin = "";
						getstream4.usablec = "";
					}
					
						
						//getstream4.cind = 0;
					getstream4.lastresponse = System.currentTimeMillis();
						
					//}
		
					//getstream4.usabled = "";
			}
	}

}
