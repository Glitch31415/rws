package VRWS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Timer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jgit.*;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.util.concurrent.ThreadLocalRandom;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

class getstream1 implements Runnable { // reads commands from modem
	public static String gcmdsin = "";
	public static String gcmdsout;
	public static Socket cmds = null;
	public static int readingcmds = 0;

	public void run() {
		InputStream cmdsin = null;
		try {
			cmdsin = cmds.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //InputStreamReader isr2 = new InputStreamReader(datain,StandardCharsets.UTF_8);
        //BufferedReader br2 = new BufferedReader(isr2);
		int gcmds;
		while (0==0) {


            try {
				if ((gcmds = cmdsin.read()) != -1) {
					readingcmds = readingcmds + 1;
					//if ((char)gcmds != '\n' && (char)gcmds != '\r') {
					    gcmdsin = gcmdsin + (char)gcmds;
					//}

				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

        //br2.lines().forEach(line2 -> gdatain=gdatain+line2);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //InputStreamReader isr2 = new InputStreamReader(datain,StandardCharsets.UTF_8);
        //BufferedReader br2 = new BufferedReader(isr2);
		//int gdata;
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

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
}

class getstream3 implements Runnable { // reads commands from terminal
	public static String termin = "";

	public void run() {
		// Enter data using BufferReader
        BufferedReader readerterm = new BufferedReader(
            new InputStreamReader(System.in));
 
        // Reading data using readLine
        String nameterm = "";
        while (0==0) {
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
    			
    			nameterm = readerterm.readLine();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     
            // Printing the read line
            termin = nameterm;
        }

	}
}

public class mainclass {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, NoFilepatternException, GitAPIException {
		String usablec = "";
		String usabled = "";
		String wstext = "";
		String textscanthing = "";
		Scanner webscan;
		int option = 0;
		String dataoutp = "";
		String rcall = "";
		String searchthing = "";
		String weatherthing = "";
		String logs = "";
		String encodedString = "";
		String encodedStringpart = "";
		int cind = 8;
		int dind = 0;
		int rcallind = 0;
		int filebytesleft = 0;
		int numbuf = 0;
		String weatherend = "";
		String[] latLng = null;
		String st = "";
		String downloadcheck = "";
		int initwait = 0;
		boolean beginning = true;
		boolean beggood = false;
		boolean formremove = false;
		boolean conn = false;
		boolean connmsg = false;
		boolean readyforreading = true;
		boolean readyforreadingc = true;
		boolean termconnect = false;
		int prevrdind = 0;
		String prevtermin = "";
		int prevrcind = 0;
		int curbuf = 0;
		String softver = "v55";
		int totalconnections = 0;
		long starttime = System.currentTimeMillis();
		boolean intaccess = true;
		boolean pexists = false;
		// TODO Auto-generated method stub
		getstream1.cmds = new Socket("127.0.0.1", 8300);
		getstream2.data = new Socket("127.0.0.1", 8301);
		OutputStream cmdsout = getstream1.cmds.getOutputStream();
		OutputStream dataout = getstream2.data.getOutputStream();
		String OS = System.getProperty("os.name").toLowerCase();
		boolean windows = false;
		if (OS.contains("windows")) {
			windows = true;
		}
		else {
			windows = false;
		}
		Thread object = new Thread(new getstream1());
		object.start();
		Thread object2 = new Thread(new getstream2());
		object2.start();
        Scanner callinp = new Scanner(System.in);
        System.out.println("Enter callsign without suffixes or prefixes (example: KJ7QQG)");
        String callsign = callinp.nextLine();
        System.out.println("Enter server welcome message (leave blank if none)");
        String welcomemessage = callinp.nextLine();
		String cmdsoutp = "MYCALL "+callsign+"\rPUBLIC ON\rLISTEN ON\rCHAT ON\rCLEANTXBUFFER\rBW2300\r";
		String prevdatainthing = "";
		boolean weatherbroke = false;
		byte[] cmdsdata = cmdsoutp.getBytes();
		String postname = "";
		String postbody = "";
		String stp = "";
		boolean varalicensed = false;
		float recentsn = (float) 0.00;
		cmdsout.write(cmdsdata);
		Thread.sleep(1000);
		Thread object3 = new Thread(new getstream3());
		object3.start();
		System.out.println("The server has started. You may interact with the server from this terminal by entering a command:"+"\n"+"|w : Fetch text or raw html from a website\n|s : Quick text-only search\n|f : Get weather forecast for given city+state\n|d : Download a given url through base64\n|c : View or create threads in the community folder on the github\n|i : Print server info\nIf you have entered a command through the terminal, you can disconnect from the server and allow other people to use it by saying '|disc'.");
		while (0==0) {
			usabled = "";
			if (termconnect == true) {
				curbuf = 0;
			}
				//if (getstream2.readingdata == prevrdind) {
					//Thread.sleep(0);
					//if (getstream2.readingdata == prevrdind) {
						//readyforreading = true;

					//}

				//}
				//else {
					//readyforreading = false;
					//prevrdind = getstream2.readingdata;
				//}
			if (getstream3.termin != "") {
				if (conn == true && termconnect == false) {
					System.out.println("You can't interact with the server locally right now, there is someone who is connected to it externally.");
					getstream3.termin = "";
				}
				else {

						//if (getstream3.termin == "website" || getstream3.termin == "search" || getstream3.termin == "weather" || getstream3.termin == "download" || getstream3.termin == "community" || getstream3.termin == "status" || getstream3.termin == "disc") {
						if (getstream3.termin.contains("|disc")) {
							termconnect = false;
							conn = false;
							logs = logs + rcall + " disconnected\n";
							System.out.println("Logs:\n-----\n" + logs + "\n-----");
							option = 0;
							getstream3.termin = "";
							

						}
						else {
							if (conn == false) {
								conn = true;
								rcall = callsign;
								connmsg = true;
							}
							termconnect = true;
							curbuf = 0;
							usabled = getstream3.termin;
							getstream3.termin = "";
						}
						
					//}


				}
				
			}
			
				if (getstream1.readingcmds == prevrcind) {
					Thread.sleep(100);
					if (getstream1.readingcmds == prevrcind) {
						readyforreadingc = true;

					}

				}
				else {
					readyforreadingc = false;
					prevrcind = getstream1.readingcmds;
				}
				if (termconnect == false) {
					if (prevdatainthing == getstream2.gdatain) {
						usabled = "";
					}
					else {
						if (getstream2.gdatain.contains("&IT&") || getstream2.gdatain.contains(rcall + " <R") ) {
							usabled = "";
						}
						else {
							usabled = getstream2.gdatain;
							prevdatainthing = getstream2.gdatain;

						}

					}
				}
				


				if (getstream1.gcmdsin != null) {
					if (readyforreadingc == true) {
						if (getstream1.gcmdsin.length() > cind) {

							usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

							cind = getstream1.gcmdsin.length();
							//System.out.println("CMD: " + usablec);
						} else {
							usablec = "";
						}
					}
					else {
						usablec = "";
					}

				}
				// main loop things
					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
							int thing = 7;
							String thingcounter = "";
							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
								thing = thing + 1;
							}
							if (thingcounter != "") {
								curbuf = Integer.parseInt(thingcounter);
							}
						}
					}
				if (usablec.contains("REGISTERED")) {
					varalicensed = true;
				}
				if (usablec.contains("SN ")) {
					String snusablec = usablec.substring(usablec.lastIndexOf("SN "));
					snusablec = snusablec.substring(0, snusablec.indexOf("\r"));
					String[] sncqframestrings = snusablec.split(" ");
					recentsn = Float.parseFloat(sncqframestrings[1]);
				}
				if (usablec.contains("CQFRAME")) {

					if (conn == false && intaccess == true) {
						cmdsoutp = "";

						if (varalicensed == false) {

							if (recentsn >= -10) {
								logs = logs + "CQ heard, vara not licensed, S/N " + recentsn + ", waiting 80 sec before response\n";
							}
							else {
								if (recentsn >= -15) {
									logs = logs + "CQ heard, vara not licensed, S/N " + recentsn + ", waiting 100 sec before response\n";
									Thread.sleep(20000);

								}
								else {
									logs = logs + "CQ heard, vara not licensed, S/N " + recentsn + ", waiting 120 sec before response\n";
									Thread.sleep(40000);

								}
							}
							Thread.sleep(70000);
						}
						else {
							if (recentsn >= 20) {
								logs = logs + "CQ heard, S/N " + recentsn + ", waiting 10 sec before response\n";
							}
							else {
								if (recentsn >= 15) {
									logs = logs + "CQ heard, S/N " + recentsn + ", waiting 20 sec before response\n";
									Thread.sleep(10000);
								}
								else {
									if (recentsn >= 10) {
										logs = logs + "CQ heard, S/N " + recentsn + ", waiting 30 sec before response\n";
										Thread.sleep(20000);
									}
									else {
										if (recentsn >= 5) {
											logs = logs + "CQ heard, S/N " + recentsn + ", waiting 40 sec before response\n";
											Thread.sleep(30000);
										}
										else {
											if (recentsn >= 0) {
												logs = logs + "CQ heard, S/N " + recentsn + ", waiting 50 sec before response\n";
												Thread.sleep(40000);
											}
											else {
												if (recentsn >= -5) {
													logs = logs + "CQ heard, S/N " + recentsn + ", waiting 60 sec before response\n";
													Thread.sleep(50000);
												}
												else {
													if (recentsn >= -10) {
														logs = logs + "CQ heard, S/N " + recentsn + ", waiting 70 sec before response\n";
														Thread.sleep(60000);
													}
													else {
														if (recentsn >= -15) {
															logs = logs + "CQ heard, S/N " + recentsn + ", waiting 90 sec before response\n";
															Thread.sleep(80000);
														}
														else {
															logs = logs + "CQ heard, S/N " + recentsn + ", waiting 110 sec before response\n";
															Thread.sleep(100000);
														}
													}
												}
											}
										}
									}
								}
							}
						}
						Thread.sleep(10000);
						String cqusablec = usablec.substring(usablec.lastIndexOf("CQFRAME"));
						String[] cqcqframestrings = cqusablec.split("\r");
							if (cqcqframestrings[0].contains(" 500")) {
								cmdsoutp = "BW500\r";
							}
							String[] cqrx = cqcqframestrings[0].split(" ");
							String[] cqrxcall = cqrx[1].split("-");
							cmdsoutp = cmdsoutp + "CONNECT " + callsign + " " + cqrxcall[0] + "\r";

							cmdsdata = cmdsoutp.getBytes();
							cmdsout.write(cmdsdata);


					}
				}
				if (usablec.contains("CONNECTED")) {
					String dummyload = "";
                    try {
                            URLConnection connectiontest = new URL("https://www.google.com").openConnection();
                            Scanner webscantest = new Scanner(connectiontest.getInputStream());
                            webscantest.useDelimiter("\\Z");
                            dummyload = webscantest.next();
                            webscantest.close();

                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                       dummyload = ex.toString();
                       intaccess = false;
                    }
                    if (dummyload.contains("Privacy")) {
                    	intaccess = true;
                    }
					if (termconnect == false) {
						if (usablec.contains("DISCONN")) {
							if (usablec.lastIndexOf("CONNECTED") > (usablec.lastIndexOf("DISCONN") + 3)) {
								conn = true;
								connmsg = true;
							}
							else {
								if (conn == true) {
									conn = false;
									logs = logs + rcall + " disconnected\n";
									//System.out.println("Logs:\n-----\n" + logs + "\n-----");
									option = 0;
								}
							}

						}
						else {
							conn = true;
							connmsg = true;
						}
					}

				}
				
				if (conn == true) {
					if (connmsg == true) {
						if (termconnect == false) {
							String rcallcut = usablec.substring(usablec.lastIndexOf("CONNECTED"),usablec.length());
							String[] rcallcutcall = rcallcut.split(" ");
							rcall = "person";
							for (int rcind = 0; rcind < rcallcutcall.length; rcind = rcind + 1) {
								if (rcallcutcall[rcind].contains(callsign)) {
									if (rcind == 1) {
										rcall = rcallcutcall[2];
									}
									if (rcind == 2) {
										rcall = rcallcutcall[1];
									}
								}
							}
							//rcallind = usablec.lastIndexOf("CONNECTED") + callsign.length() + 2;
							//rcall = "";
							//while (usablec.charAt(rcallind) != ' ') {
								//rcall = rcall + usablec.charAt(rcallind);
								//rcallind = rcallind + 1;
							//}
								dataoutp = "";
								dataoutp = "-----\nWelcome, " + rcall;
								if (welcomemessage != "") {
									dataoutp = dataoutp + "\n\n" + welcomemessage;
								}
								if (intaccess == true) {
									if (varalicensed == true) {
										dataoutp = dataoutp + "\n-----\nCommands:\n|w : Fetch text or raw html from a website\n|s : Quick text-only search\n|f : Get weather forecast for given city+state\n|d : Download a given url through base64\n|c : View or create threads in the community folder on the github\n|i : Print server info\r";
										
									}
									else {
										dataoutp = dataoutp + "(This server does not use a licensed copy of VARA. Download speeds will be limited to speed level 4.)\n-----\nCommands:\n|w : Fetch text or raw html from a website\n|s : Quick text-only search\n|f : Get weather forecast for given city+state\n|d : Download a given url through base64\n|c : View or create threads in the community folder on the github\n|i : Print server info\r";
										
									}
									
									logs = logs + rcall + " connected\n";
									//System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        dataoutp = dataoutp.length() + " " + dataoutp;
									byte[] datadata = dataoutp.getBytes();
									
									dataout.write(datadata);
									curbuf = 1;
									connmsg = false;
								}
								else {
									dataoutp = dataoutp + "\n-----\nSorry, but this server doesn't have access to the internet. Please let the server operator know this and/or try again later.\r";
									
									logs = logs + rcall + " connected, but the server did not have internet access\n";
									//System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        dataoutp = dataoutp.length() + " " + dataoutp;
									byte[] datadata = dataoutp.getBytes();
									
									dataout.write(datadata);
									curbuf = 1;
									connmsg = false;
								}

						}
						else {
							curbuf = 0;
							dataoutp = "";
							rcall = callsign;
							dataoutp = "";
							dataoutp = "-----\nWelcome, " + rcall;
							if (welcomemessage != "") {
								dataoutp = dataoutp + "\n\n" + welcomemessage;
							}
							dataoutp = dataoutp + "\n-----\nCommands:\n|w : Fetch text or raw html from a website\n|s : Quick text-only search\n|f : Get weather forecast for given city+state\n|d : Download a given url through base64\n|c : View or create threads in the community folder on the github\n|i : Print server info\n[You can disconnect by saying '|disc' because you are using the terminal.]\r";
							
							logs = logs + rcall + " connected\n";
							//System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        System.out.println(dataoutp);
	                        dataoutp = "";
							connmsg = false;
						}
						totalconnections = totalconnections + 1;

					}
					if (usabled.contains("|w")) {
						if (curbuf == 0) {
							option = 1;
							dataoutp = "Please provide the exact URL of the website you want to fetch. Example: 'https://www.example.com'. If you want the raw HTML from the website, please add a carat (^) behind the start of the URL. Example: '^https://www.example.com'.\nCommands: |w |s |f |d |c |i\r";
	                        if (termconnect == false) {
								dataoutp = dataoutp.length() + " " + dataoutp;
								byte[] datadata = dataoutp.getBytes();
								
								dataout.write(datadata);
								curbuf = 1;
	                        }
	                        else {
	                        	System.out.println(dataoutp);
	                        	dataoutp = "";
	                        }
	                        
						}
						usabled = "";
						
				}
				if (usabled.contains("|s")) {
					if (curbuf == 0) {
						option = 2;
						dataoutp = "Please provide your query.\nCommands: |w |s |f |d |c |i\r";
						if (termconnect == false) {
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							
							dataout.write(datadata);
							curbuf = 1;
						}
						else {
							System.out.println(dataoutp);
							dataoutp = "";
						}
						
					}
					usabled = "";
						
				}
				if (usabled.contains("|f")) {
					if (curbuf == 0) {
						option = 3;
						dataoutp = "Please provide the city and state you would like the weather for in the format of 'city state'. If the command returns blank, try reformatting your query.\nCommands: |w |s |f |d |c |i\r";
						if (termconnect == false) {
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							
							dataout.write(datadata);
							curbuf = 1;
						}
						else {
							System.out.println(dataoutp);
							dataoutp = "";
						}
							
					}
					usabled = "";
						
				}
				if (usabled.contains("|i")) {
					if (curbuf == 0) {
						option = 4;
						String uptimestring = ((System.currentTimeMillis() - starttime)/1000) + " seconds (" + ((System.currentTimeMillis() - starttime)/3600000) + " hours)";
						dataoutp = "Total connections: " + totalconnections + "\nUptime: " + uptimestring + "\nServer version: " + softver + "\nLogs:\n-----\n" + logs + "\n-----\nCommands: |w |s |f |d |c |i\r";
						if (termconnect == false) {
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							
							dataout.write(datadata);
							curbuf = 1;
						}
						else {
							System.out.println(dataoutp);
							dataoutp = "";
						}
						option = 0;
						
					}
					usabled = "";
						
				}
				if (usabled.contains("|d")) {
					if (usabled.contains("|disc")) {
						
					}
					else {
						if (curbuf == 0) {
							option = 5;
							dataoutp = "Please provide the URL of the file you would like to download.\nCommands: |w |s |f |d |c |i\r";
							if (termconnect == false) {
		                        dataoutp = dataoutp.length() + " " + dataoutp;
								byte[] datadata = dataoutp.getBytes();
								
								dataout.write(datadata);
								curbuf = 1;
							}
							else {
								System.out.println(dataoutp);
								dataoutp = "";
							}
							
						}
						usabled = "";
					}

						
				}
				if (usabled.contains("|c")) {
					if (curbuf == 0) {
						wstext = "";
						dataoutp = "";
						URLConnection connection = null;
                        try {
	                            connection = new URL("https://raw.githubusercontent.com/Glitch31415/rws/main/community/index").openConnection();
	                            webscan = new Scanner(connection.getInputStream());
	                            webscan.useDelimiter("\\Z");
	                            wstext = webscan.next();
	                            webscan.close();

                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            wstext = ex.toString();
                        }
                        String lesswstext = "";
                        int i2=0;
                        while (i2 < wstext.split("\n").length) {
                        	if (i2 < 25) {
                        		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
                        	}
                        	i2 = i2 + 1;
                        }
                        wstext = lesswstext;
                        	wstext = wstext.replaceAll("\\r", "");
                            dataoutp = wstext + "\n\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the community area?\nCommands: |w |s |f |d |c |i";
                            logs = logs + rcall + " looked at the community index page\n";
                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
                        encodedString = dataoutp;
                        

                        if (termconnect == false) {
                        	filebytesleft = encodedString.length();
							//System.out.println(filebytesleft + " bytes to send");
							dataoutp = (encodedString.length()+2) + " \n\r";
	                        byte[] datadata = dataoutp.getBytes();
	                        
							dataout.write(datadata);
							curbuf = 1;
							dataoutp = "";

								while (filebytesleft > 1024) {
									if (getstream1.readingcmds == prevrcind) {
										Thread.sleep(100);
										if (getstream1.readingcmds == prevrcind) {
											readyforreadingc = true;

										}

									}
									else {
										readyforreadingc = false;
										prevrcind = getstream1.readingcmds;
									}
									if (getstream1.gcmdsin != null) {
										if (readyforreadingc == true) {
											if (getstream1.gcmdsin.length() > cind) {

												usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

												cind = getstream1.gcmdsin.length();
												//System.out.println("CMD: " + usablec);
											} else {
												usablec = "";
											}
										}
										else {
											usablec = "";
										}

									}
			        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
			        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
			        							int thing = 7;
			        							String thingcounter = "";
			        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
			        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
			        								thing = thing + 1;
			        							}
			        							if (thingcounter != "") {
			        								curbuf = Integer.parseInt(thingcounter);
			        							}
			        						}
			        					}
									if (usablec.contains("BUFFER")) {
										int i = 7;
										String numbuild = "";
										
										if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
											if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
												boolean kloop = true;
												while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
													numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
													i = i + 1;
													if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
														kloop = false;
													}
												}
												numbuf = Integer.parseInt(numbuild);
											}
										}

									}
									if (usablec.contains("DISCONN")) {
										filebytesleft = 0;
										dataoutp = "";
										option = 0;
									}
									if (numbuf < 31744) {
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = filebytesleft - 1024;
										numbuf = numbuf + 1024;
									}
								}
								encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
								dataoutp = dataoutp + encodedStringpart;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
								dataoutp = "";
								filebytesleft = 0;
								usabled = "";
                        }
                        else {
                        	System.out.println(dataoutp);
                        	dataoutp = "";
                        	usabled = "";
                        }
						option = 6;
						
					}
					usabled = "";
						
				}
					if (option == 1) {
						if (usabled != "") {
							if (usabled.contains("^")) {
								dataoutp = "Here is the raw HTML from the website you provided.\n-----\n";
								usabled = usabled.replace("^", "");
								usabled = usabled.replaceAll("\\r|\\n", "");
								URLConnection connection = null;
								
		                        try {
			                            connection = new URL(usabled).openConnection();
			                            webscan = new Scanner(connection.getInputStream());
			                            webscan.useDelimiter("\\Z");
			                            wstext = webscan.next();
			                            webscan.close();


		                        }
		                        catch (Exception ex) {
		                            ex.printStackTrace();
		                            wstext = ex.toString();
		                        }
		                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
		                            dataoutp = dataoutp + "Oops, your website contained material that is inappropriate for ham radio. Please try a different website.\n-----\nCommands: |w |s |f |d |c |i\r";
		                            logs = logs + rcall + " tried to fetch html from URL " + usabled + " but was blocked\n";
		                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        } else {
		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + wstext + "\n-----\nCommands: |w |s |f |d |c |i\r";
		                            logs = logs + rcall + " fetched html from URL " + usabled + "\n";
		                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        }								
		                        if (termconnect == false) {
		                        	encodedString = dataoutp;
			                        filebytesleft = encodedString.length();
									//System.out.println(filebytesleft + " bytes to send");
									dataoutp = (encodedString.length()+2) + " \n\r";
			                        byte[] datadata = dataoutp.getBytes();
			                       
									dataout.write(datadata);
									curbuf = 1;
									dataoutp = "";

										while (filebytesleft > 1024) {
											if (getstream1.readingcmds == prevrcind) {
												Thread.sleep(100);
												if (getstream1.readingcmds == prevrcind) {
													readyforreadingc = true;

												}

											}
											else {
												readyforreadingc = false;
												prevrcind = getstream1.readingcmds;
											}
											if (getstream1.gcmdsin != null) {
												if (readyforreadingc == true) {
													if (getstream1.gcmdsin.length() > cind) {

														usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

														cind = getstream1.gcmdsin.length();
														//System.out.println("CMD: " + usablec);
													} else {
														usablec = "";
													}
												}
												else {
													usablec = "";
												}

											}
					        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
					        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
					        							int thing = 7;
					        							String thingcounter = "";
					        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
					        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
					        								thing = thing + 1;
					        							}
					        							if (thingcounter != "") {
					        								curbuf = Integer.parseInt(thingcounter);
					        							}
					        						}
					        					}
											if (usablec.contains("BUFFER")) {
												int i = 7;
												String numbuild = "";
												
												if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
													if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
														boolean kloop = true;
														while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
															numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
															i = i + 1;
															if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
																kloop = false;
															}
														}
														numbuf = Integer.parseInt(numbuild);
													}
												}

											}
											if (usablec.contains("DISCONN")) {
												filebytesleft = 0;
												dataoutp = "";
												option = 0;
											}
											if (numbuf < 31744) {
												encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
												dataoutp = dataoutp + encodedStringpart;
						                        datadata = dataoutp.getBytes();
						                        
												dataout.write(datadata);
												dataoutp = "";
												filebytesleft = filebytesleft - 1024;
												numbuf = numbuf + 1024;
											}

										}
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
				                        
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = 0;
		                        }
		                        else {
		                        	System.out.println(dataoutp);
		                        	dataoutp = "";
		                        }		                        
							}
							else {
								dataoutp = "Here is the text from the website you provided.\n-----\n";
								
								usabled = usabled.replaceAll("\\r|\\n", "");
								textscanthing = usabled;
								usabled = "https://www.w3.org/services/html2txt?url=" + usabled;
								URLConnection connection = null;
								
		                        try {
			                            connection = new URL(usabled).openConnection();
			                            webscan = new Scanner(connection.getInputStream());
			                            webscan.useDelimiter("\\Z");
			                            wstext = webscan.next();
			                            webscan.close();

		                        }
		                        catch (Exception ex) {
		                            ex.printStackTrace();
		                            wstext = ex.toString();
		                        }
		                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
		                            dataoutp = dataoutp + "Oops, your website contained material that is inappropriate for ham radio. Please try a different website.\n-----\nCommands: |w |s |f |d |c |i\r";
		                            logs = logs + rcall + " tried to fetch text from URL " + textscanthing + " but was blocked\n";
		                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        } else {
		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + wstext + "\n-----\nCommands: |w |s |f |d |c |i\r";
		                            logs = logs + rcall + " fetched text from URL " + textscanthing + "\n";
		                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        }
		                        if (termconnect == false) {
		                        	encodedString = dataoutp;
			                        filebytesleft = encodedString.length();
									//System.out.println(filebytesleft + " bytes to send");
									dataoutp = (encodedString.length()+2) + " \n\r";
			                        byte[] datadata = dataoutp.getBytes();
			                       
									dataout.write(datadata);
									curbuf = 1;
									dataoutp = "";

										while (filebytesleft > 1024) {
											if (getstream1.readingcmds == prevrcind) {
												Thread.sleep(100);
												if (getstream1.readingcmds == prevrcind) {
													readyforreadingc = true;

												}

											}
											else {
												readyforreadingc = false;
												prevrcind = getstream1.readingcmds;
											}
											if (getstream1.gcmdsin != null) {
												if (readyforreadingc == true) {
													if (getstream1.gcmdsin.length() > cind) {

														usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

														cind = getstream1.gcmdsin.length();
														//System.out.println("CMD: " + usablec);
													} else {
														usablec = "";
													}
												}
												else {
													usablec = "";
												}

											}
					        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
					        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
					        							int thing = 7;
					        							String thingcounter = "";
					        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
					        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
					        								thing = thing + 1;
					        							}
					        							if (thingcounter != "") {
					        								curbuf = Integer.parseInt(thingcounter);
					        							}
					        						}
					        					}
											if (usablec.contains("BUFFER")) {
												int i = 7;
												String numbuild = "";
												if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
													if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
														boolean kloop = true;
														while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
															numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
															i = i + 1;
															if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
																kloop = false;
															}
														}
														numbuf = Integer.parseInt(numbuild);
													}
												}


											}
											if (usablec.contains("DISCONN")) {
												filebytesleft = 0;
												dataoutp = "";
												option = 0;
											}
											if (numbuf < 31744) {
												encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
												dataoutp = dataoutp + encodedStringpart;
						                        datadata = dataoutp.getBytes();
												dataout.write(datadata);
												dataoutp = "";
												filebytesleft = filebytesleft - 1024;
												numbuf = numbuf + 1024;
											}
										}
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = 0;
		                        }
		                        else {
		                        	System.out.println(dataoutp);
		                        	dataoutp = "";
		                        }
								
		                        
							}
						}
					}
					if (option == 2) {
						if (usabled != "") {
							dataoutp = "Here are the results of your search.\n-----\n";
							searchthing = usabled;
							usabled = URLEncoder.encode(usabled, StandardCharsets.UTF_8);
							usabled = usabled.replaceAll("\\+", "%20");
							usabled = "https://www.w3.org/services/html2txt?url=https://duckduckgo.com/html/?q=" + usabled + "&kp=1&kz=-1&kc=-1&kav=1&kac=-1&kd=-1&ko=-2&k1=-1";
							URLConnection connection = null;
							
	                        try {

		                            connection = new URL(usabled).openConnection();
		                            webscan = new Scanner(connection.getInputStream());
		                            webscan.useDelimiter("\\Z");
		                            wstext = webscan.next();
		                            webscan.close();

	                        }
	                        catch (Exception ex) {
	                            ex.printStackTrace();
	                            wstext = ex.toString();
	                        }
	                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
	                            dataoutp = dataoutp + "Oops, your results contained material that is inappropriate for ham radio. Please try a different query.\n-----\nCommands: |w |s |f |d |c |i\r";
	                            logs = logs + rcall + " attempted to search " + searchthing + " but was blocked\n";
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        } else {
	                        	wstext = wstext.replaceAll("\\r", "");
	                            dataoutp = dataoutp + wstext + "\n-----\nCommands: |w |s |f |d |c |i\r";
	                            logs = logs + rcall + " searched for " + searchthing + "\n";
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        if (termconnect == false) {
	                        	encodedString = dataoutp;
		                        filebytesleft = encodedString.length();
								//System.out.println(filebytesleft + " bytes to send");
								dataoutp = (encodedString.length()+2) + " \n\r";
		                        byte[] datadata = dataoutp.getBytes();
		                        
								dataout.write(datadata);
								curbuf = 1;
								dataoutp = "";

									while (filebytesleft > 1024) {
										if (getstream1.readingcmds == prevrcind) {
											Thread.sleep(100);
											if (getstream1.readingcmds == prevrcind) {
												readyforreadingc = true;

											}

										}
										else {
											readyforreadingc = false;
											prevrcind = getstream1.readingcmds;
										}
										if (getstream1.gcmdsin != null) {
											if (readyforreadingc == true) {
												if (getstream1.gcmdsin.length() > cind) {

													usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

													cind = getstream1.gcmdsin.length();
													//System.out.println("CMD: " + usablec);
												} else {
													usablec = "";
												}
											}
											else {
												usablec = "";
											}

										}

				        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
				        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
				        							int thing = 7;
				        							String thingcounter = "";
				        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
				        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
				        								thing = thing + 1;
				        							}
				        							if (thingcounter != "") {
				        								curbuf = Integer.parseInt(thingcounter);
				        							}
				        						}
				        					}
										if (usablec.contains("BUFFER")) {
											int i = 7;
											String numbuild = "";
											
											if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
												if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
													boolean kloop = true;
													while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
														numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
														i = i + 1;
														if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
															kloop = false;
														}
													}
													numbuf = Integer.parseInt(numbuild);
												}
											}

										}
										if (usablec.contains("DISCONN")) {
											filebytesleft = 0;
											dataoutp = "";
											option = 0;
										}
										if (numbuf < 31744) {
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = filebytesleft - 1024;
											numbuf = numbuf + 1024;
										}
									}
									encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
									dataoutp = dataoutp + encodedStringpart;
			                        datadata = dataoutp.getBytes();
									dataout.write(datadata);
									dataoutp = "";
									filebytesleft = 0;
	                        }
	                        else {
	                        	System.out.println(dataoutp);
	                        	dataoutp = "";
	                        }
	                        
							
						}
					}
					if (option == 3) {
						if (usabled != "") {
							dataoutp = "Here is the weather forecast for the location you provided.\n-----\n";
							weatherbroke = false;
							weatherend = "";
							String address = usabled;
							weatherthing = usabled;
					        String key = "AIzaSyDr8Gh1WeemI_ovbqnX_RwecVFNy9POBgk";
					        try{
					            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, "UTF-8") + "&key=" + key;
					            URL obj = new URL(url);
					            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					            
					            con.setRequestMethod("GET");
					            con.setRequestProperty("User-Agent", "Mozilla/5.0");

					            //Response
					            int responseCode = con.getResponseCode();
					            BufferedReader in = new BufferedReader(
					                    new InputStreamReader(con.getInputStream()));
					            String inputLine;
					            StringBuffer response = new StringBuffer();
					            while ((inputLine = in.readLine()) != null) {
					                response.append(inputLine);
					            }
					            in.close();

					            Pattern pattern = Pattern.compile("\"location\" : \\{(.*?)\\}");
					            Matcher matcher = pattern.matcher(response.toString());
					            if (matcher.find())
					            {
					                String latLngString = matcher.group(1).replaceAll("\\s", "");
					                String[] latLngArray = latLngString.split(",");
					                String lat = latLngArray[0].split(":")[1];
					                String lng = latLngArray[1].split(":")[1];
					                latLng = new String[]{lat,lng};
					            }
					            else {
					            	weatherbroke = true;
					            }
					        }catch(IOException ioe){
					        	weatherbroke = true;
					        	weatherend = weatherend + ioe.toString();
					        }
					        if (weatherbroke == false) {
					        	try{
						            //Get Points
						            String url = "https://api.weather.gov/points/" + latLng[0]+","+latLng[1];
						            URL obj = new URL(url);
						            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						            
						            con.setRequestMethod("GET");
						            con.setRequestProperty("User-Agent", "Mozilla/5.0");

						            //Response
						            int responseCode = con.getResponseCode();
						            BufferedReader in = new BufferedReader(
						                    new InputStreamReader(con.getInputStream()));
						            String inputLine;
						            StringBuffer response = new StringBuffer();
						            while ((inputLine = in.readLine()) != null) {
						                response.append(inputLine);
						            }
						            in.close();

						            String gridX="";
						            String gridY="";
						            String gridId="";
						            Pattern pattern = Pattern.compile("\"gridX\": (.*?),");
						            Matcher matcher = pattern.matcher(response.toString());
						            if (matcher.find()) {
						                gridX = matcher.group(1);
						            }
						            pattern = Pattern.compile("\"gridY\": (.*?),");
						            matcher = pattern.matcher(response.toString());
						            if (matcher.find()) {
						                gridY = matcher.group(1);
						            }
						            pattern = Pattern.compile("\"gridId\": \"(.*?)\"");
						            matcher = pattern.matcher(response.toString());
						            if (matcher.find()) {
						                gridId = matcher.group(1);
						            }

						            //Get forecast
						            url = "https://api.weather.gov/gridpoints/"+ gridId + "/" + gridX + ","+ gridY + "/forecast";
						            obj = new URL(url);
						            con = (HttpURLConnection) obj.openConnection();
						            
						            con.setRequestMethod("GET");
						            con.setRequestProperty("User-Agent", "Mozilla/5.0");

						            //Response
						            responseCode = con.getResponseCode();
						            in = new BufferedReader(
						                    new InputStreamReader(con.getInputStream()));
						            response = new StringBuffer();
						            while ((inputLine = in.readLine()) != null) {
						                response.append(inputLine);
						            }
						            in.close();
						            weatherend = response.toString();
						        }catch(IOException ioe){
						        	weatherend = weatherend + ioe.toString();
						        }
					        }
					        weatherend = weatherend.replaceAll("[^A-Za-z .0-9]", "");
					        weatherend = weatherend.replaceAll(" ", "\n");
					        weatherend = weatherend.replaceAll("\n\n", "\n");
					        weatherend = weatherend.replaceAll("\n\n", "\n");
					        weatherend = weatherend.replaceAll("\n\n", "\n");
					        wstext = weatherend;
	                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
	                            dataoutp = dataoutp + "Oops, your results contained material that is inappropriate for ham radio. Please try a different city.\n-----\nCommands: |w |s |f |d |c |i\r";
	                            logs = logs + rcall + " attempted to get the weather for " + weatherthing + " but was blocked\n";
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        } else {
	                        	wstext = wstext.replaceAll("\\r", "");
	                            dataoutp = dataoutp + wstext + "\n-----\nCommands: |w |s |f |d |c |i\r";
	                            logs = logs + rcall + " got the weather for " + weatherthing + "\n";
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        if (termconnect == false) {
		                        encodedString = dataoutp;
		                        filebytesleft = encodedString.length();
								//System.out.println(filebytesleft + " bytes to send");
								dataoutp = (encodedString.length()+2) + " \n\r";
		                        byte[] datadata = dataoutp.getBytes();
		                        
								dataout.write(datadata);
								curbuf = 1;
								dataoutp = "";

									while (filebytesleft > 1024) {
										if (getstream1.readingcmds == prevrcind) {
											Thread.sleep(100);
											if (getstream1.readingcmds == prevrcind) {
												readyforreadingc = true;

											}

										}
										else {
											readyforreadingc = false;
											prevrcind = getstream1.readingcmds;
										}
										if (getstream1.gcmdsin != null) {
											if (readyforreadingc == true) {
												if (getstream1.gcmdsin.length() > cind) {

													usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

													cind = getstream1.gcmdsin.length();
													//System.out.println("CMD: " + usablec);
												} else {
													usablec = "";
												}
											}
											else {
												usablec = "";
											}

										}
				        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
				        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
				        							int thing = 7;
				        							String thingcounter = "";
				        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
				        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
				        								thing = thing + 1;
				        							}
				        							if (thingcounter != "") {
				        								curbuf = Integer.parseInt(thingcounter);
				        							}
				        						}
				        					}
										if (usablec.contains("BUFFER")) {
											int i = 7;
											String numbuild = "";
											
											if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
												if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
													boolean kloop = true;
													while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
														numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
														i = i + 1;
														if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
															kloop = false;
														}
													}
													numbuf = Integer.parseInt(numbuild);
												}
											}

										}
										if (usablec.contains("DISCONN")) {
											filebytesleft = 0;
											dataoutp = "";
											option = 0;
										}
										if (numbuf < 31744) {
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = filebytesleft - 1024;
											numbuf = numbuf + 1024;
										}
									}
									encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
									dataoutp = dataoutp + encodedStringpart;
			                        datadata = dataoutp.getBytes();
									dataout.write(datadata);
									dataoutp = "";
									filebytesleft = 0;
	                        }
	                        else {
	                        	System.out.println(dataoutp);
	                        	dataoutp = "";
	                        	
	                        }

						}
					}
					if (option == 5) {
						if (usabled != "") {
							try {
									InputStream in = new URL(usabled).openStream();
									Files.copy(in, Paths.get("tempdownload"), StandardCopyOption.REPLACE_EXISTING);
									byte[] fileContent = FileUtils.readFileToByteArray(new File("tempdownload"));
									downloadcheck = new String(fileContent);
									if (downloadcheck.contains("porn") || downloadcheck.contains(" sex ") || downloadcheck.contains("fuck") || downloadcheck.contains("shit") || downloadcheck.contains("bitch") || downloadcheck.contains(" ass ") || downloadcheck.contains("pussy") || downloadcheck.contains("hentai") || downloadcheck.contains("xvideos")) {
			                            encodedString = "Oops, that file contained material that is inappropriate for ham radio.";
			                            logs = logs + rcall + " attempted to download " + usabled + " but was blocked\n";
			                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        } else {
										encodedString = Base64.getEncoder().encodeToString(fileContent);
										logs = logs + rcall + " downloaded " + usabled + "\n";
										//System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        }


							}
							catch (Exception badthing) {
								badthing.printStackTrace();
	                            encodedString = badthing.toString();
							}


							filebytesleft = encodedString.length();
							//System.out.println(filebytesleft + " bytes to send");
	                        if (termconnect == false) {
								dataoutp = (encodedString.length()+23+269) + " Download started.\n-----\n\r";
	                        	byte[] datadata = dataoutp.getBytes();
	                        	
								dataout.write(datadata);
								curbuf = 1;
								dataoutp = "";

									while (filebytesleft > 1024) {
										if (getstream1.readingcmds == prevrcind) {
											Thread.sleep(100);
											if (getstream1.readingcmds == prevrcind) {
												readyforreadingc = true;

											}

										}
										else {
											readyforreadingc = false;
											prevrcind = getstream1.readingcmds;
										}
										if (getstream1.gcmdsin != null) {
											if (readyforreadingc == true) {
												if (getstream1.gcmdsin.length() > cind) {

													usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

													cind = getstream1.gcmdsin.length();
													//System.out.println("CMD: " + usablec);
												} else {
													usablec = "";
												}
											}
											else {
												usablec = "";
											}

										}
				        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
				        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
				        							int thing = 7;
				        							String thingcounter = "";
				        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
				        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
				        								thing = thing + 1;
				        							}
				        							if (thingcounter != "") {
				        								curbuf = Integer.parseInt(thingcounter);
				        							}
				        						}
				        					}
										if (usablec.contains("BUFFER")) {
											int i = 7;
											String numbuild = "";
											
											if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
												if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
													boolean kloop = true;
													while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
														numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
														i = i + 1;
														if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
															kloop = false;
														}
													}
													numbuf = Integer.parseInt(numbuild);
												}
											}

										}
										if (usablec.contains("DISCONN")) {
											filebytesleft = 0;
											dataoutp = "";
											option = 0;
										}
										if (numbuf < 31744) {
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = filebytesleft - 1024;
											numbuf = numbuf + 1024;
										}

									}
									encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
									dataoutp = dataoutp + encodedStringpart;
			                        datadata = dataoutp.getBytes();
									dataout.write(datadata);
									dataoutp = "";
									filebytesleft = 0;
						

								
								dataoutp = "";
								dataoutp = dataoutp + "\n-----\nYou can decode this info by copying the text between the dashes, saving the text as 'data.b64' then running 'certutil -decode data.b64 downloadedfile' in the command line in Windows or 'base64 -d data.b64 > downloadedfile' in Linux.\nCommands: |w |s |f |d |c |i\r";
		                        //dataoutp = dataoutp.length() + " " + dataoutp;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
	                        }
	                        else {
	                        	System.out.println("Download started.\n-----\n" + encodedString);
	                        	System.out.println("-----\nYou can decode this info by copying the text between the dashes, saving the text as 'data.b64' then running 'certutil -decode data.b64 downloadedfile' in the command line in Windows or 'base64 -d data.b64 > downloadedfile' in Linux.\nCommands: |w |s |f |d |c |i\r");
	                        	dataoutp = "";
	                        }
							
						}
					}
					if (option == 6) {
						if (usabled != "") {
								if (usabled.contains("view")) {
									dataoutp = "Please send the name of the thread you would like to view. (Or, send '|all' to list all threads)\r";
									if (termconnect == false) {
										dataoutp = dataoutp.length() + " " + dataoutp;
										byte[] datadata = dataoutp.getBytes();
										
										dataout.write(datadata);
										curbuf = 1;
										option = 7;
										usabled = "";
									}
									else {
										System.out.println(dataoutp);
										option = 7;
										usabled = "";
									}
								}
								if (usabled.contains("create")) {
									//create posts
									dataoutp = "Please send the name of the thread you would like to create (or leave a comment on)\r";
									if (termconnect == false) {
										dataoutp = dataoutp.length() + " " + dataoutp;
										byte[] datadata = dataoutp.getBytes();
										
										dataout.write(datadata);
										curbuf = 1;
										option = 8;
										usabled = "";
									}
									else {
										System.out.println(dataoutp);
										option = 8;
										usabled = "";
									}

								}
						}
					}
					if (option == 7) {
						if (usabled != "") {
							if (usabled.contains("|")) {
								if (usabled.contains("|all")) {
									//view posts
									dataoutp = "Please send the name of the thread you would like to view.\n-----\n";
									searchthing = usabled;
									usabled = "https://raw.githubusercontent.com/Glitch31415/rws/main/community/index";
									URLConnection connection = null;
			                        try {
				                            connection = new URL(usabled).openConnection();
				                            webscan = new Scanner(connection.getInputStream());
				                            webscan.useDelimiter("\\Z");
				                            wstext = webscan.next();
				                            webscan.close();

			                        }
			                        catch (Exception ex) {
			                            ex.printStackTrace();
			                            wstext = ex.toString();
			                        }

			                        	wstext = wstext.replaceAll("\\r", "");
			                            dataoutp = dataoutp + wstext + "\n-----\nCommands: |w |s |f |d |c |i\r";
			                            logs = logs + rcall + " looked at the community index page\n";
			                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        encodedString = dataoutp;
			                        

			                        if (termconnect == false) {
			                        	filebytesleft = encodedString.length();
										//System.out.println(filebytesleft + " bytes to send");
										dataoutp = (encodedString.length()+2) + " \n\r";
				                        byte[] datadata = dataoutp.getBytes();
				                        
										dataout.write(datadata);
										curbuf = 1;
										dataoutp = "";

											while (filebytesleft > 1024) {
												if (getstream1.readingcmds == prevrcind) {
													Thread.sleep(100);
													if (getstream1.readingcmds == prevrcind) {
														readyforreadingc = true;

													}

												}
												else {
													readyforreadingc = false;
													prevrcind = getstream1.readingcmds;
												}
												if (getstream1.gcmdsin != null) {
													if (readyforreadingc == true) {
														if (getstream1.gcmdsin.length() > cind) {

															usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

															cind = getstream1.gcmdsin.length();
															//System.out.println("CMD: " + usablec);
														} else {
															usablec = "";
														}
													}
													else {
														usablec = "";
													}

												}
						        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
						        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
						        							int thing = 7;
						        							String thingcounter = "";
						        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
						        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
						        								thing = thing + 1;
						        							}
						        							if (thingcounter != "") {
						        								curbuf = Integer.parseInt(thingcounter);
						        							}
						        						}
						        					}
												if (usablec.contains("BUFFER")) {
													int i = 7;
													String numbuild = "";
													
													if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
														if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
															boolean kloop = true;
															while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
																numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
																i = i + 1;
																if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
																	kloop = false;
																}
															}
															numbuf = Integer.parseInt(numbuild);
														}
													}

												}
												if (usablec.contains("DISCONN")) {
													filebytesleft = 0;
													dataoutp = "";
													option = 0;
												}
												if (numbuf < 31744) {
													encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
													dataoutp = dataoutp + encodedStringpart;
							                        datadata = dataoutp.getBytes();
													dataout.write(datadata);
													dataoutp = "";
													filebytesleft = filebytesleft - 1024;
													numbuf = numbuf + 1024;
												}
											}
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = 0;
											usabled = "";
											option = 7;
			                        }
			                        else {
			                        	System.out.println(dataoutp);
			                        	dataoutp = "";
			                        	usabled = "";
										option = 7;
			                        }
								}
							}
							else {
								dataoutp = "";
								searchthing = usabled;
									usabled = URLEncoder.encode(usabled, StandardCharsets.UTF_8);
									usabled = usabled.replaceAll("\\+", "%20");
									usabled = "https://raw.githubusercontent.com/Glitch31415/rws/main/community/" + usabled;

									URLConnection connection = null;
			                        try {

				                            connection = new URL(usabled).openConnection();
				                            webscan = new Scanner(connection.getInputStream());
				                            webscan.useDelimiter("\\Z");
				                            wstext = webscan.next();
				                            webscan.close();
			                        }
			                        catch (Exception ex) {
			                            ex.printStackTrace();
			                            wstext = ex.toString();
			                        }
								

		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + wstext + "\n-----\n";
		                            logs = logs + rcall + " looked at the thread " + searchthing + "\n";
		                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
								connection = null;
		                        try {
			                            connection = new URL("https://raw.githubusercontent.com/Glitch31415/rws/main/community/index").openConnection();
			                            webscan = new Scanner(connection.getInputStream());
			                            webscan.useDelimiter("\\Z");
			                            wstext = webscan.next();
			                            webscan.close();

		                        }
		                        catch (Exception ex) {
		                            ex.printStackTrace();
		                            wstext = ex.toString();
		                        }
		                        String lesswstext = "";
		                        int i2=0;
		                        while (i2 < wstext.split("\n").length) {
		                        	if (i2 < 25) {
		                        		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
		                        	}
		                        	i2 = i2 + 1;
		                        }
		                        wstext = lesswstext;
		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + "\n" + wstext + "\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the community area?\nCommands: |w |s |f |d |c |i\r";
		                            logs = logs + rcall + " looked at the community index page\n";
		                        encodedString = dataoutp;
		                        

		                        if (termconnect == false) {
		                        	filebytesleft = encodedString.length();
									//System.out.println(filebytesleft + " bytes to send");
									dataoutp = (encodedString.length()+2) + " \n\r";
			                        byte[] datadata = dataoutp.getBytes();
			                        
									dataout.write(datadata);
									curbuf = 1;
									dataoutp = "";

										while (filebytesleft > 1024) {
											if (getstream1.readingcmds == prevrcind) {
												Thread.sleep(100);
												if (getstream1.readingcmds == prevrcind) {
													readyforreadingc = true;

												}

											}
											else {
												readyforreadingc = false;
												prevrcind = getstream1.readingcmds;
											}
											if (getstream1.gcmdsin != null) {
												if (readyforreadingc == true) {
													if (getstream1.gcmdsin.length() > cind) {

														usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

														cind = getstream1.gcmdsin.length();
														//System.out.println("CMD: " + usablec);
													} else {
														usablec = "";
													}
												}
												else {
													usablec = "";
												}

											}
					        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
					        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
					        							int thing = 7;
					        							String thingcounter = "";
					        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
					        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
					        								thing = thing + 1;
					        							}
					        							if (thingcounter != "") {
					        								curbuf = Integer.parseInt(thingcounter);
					        							}
					        						}
					        					}
											if (usablec.contains("BUFFER")) {
												int i = 7;
												String numbuild = "";
												
												if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
													if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
														boolean kloop = true;
														while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
															numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
															i = i + 1;
															if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
																kloop = false;
															}
														}
														numbuf = Integer.parseInt(numbuild);
													}
												}

											}
											if (usablec.contains("DISCONN")) {
												filebytesleft = 0;
												dataoutp = "";
												option = 0;
											}
											if (numbuf < 31744) {
												encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
												dataoutp = dataoutp + encodedStringpart;
						                        datadata = dataoutp.getBytes();
												dataout.write(datadata);
												dataoutp = "";
												filebytesleft = filebytesleft - 1024;
												numbuf = numbuf + 1024;
											}
										}
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = 0;
										usabled = "";
		                        }
		                        else {
		                        	System.out.println(dataoutp);
		                        	dataoutp = "";
		                        	usabled = "";
		                        }
								option = 6;

							}

						}
					}
					if (option == 8) {
						if (usabled != "") {
							if (usabled.contains("README") || usabled == "index" || usabled.contains("|")) {
								dataoutp = "Oops, that title is invalid! Please make a title that does not include 'README' or '|' and is not 'index'.\r";
								if (termconnect == false) {
									dataoutp = dataoutp.length() + " " + dataoutp;
									byte[] datadata = dataoutp.getBytes();
									
									dataout.write(datadata);
									curbuf = 1;
									usabled = "";
								}
								else {
									System.out.println(dataoutp);
									dataoutp = "";
									usabled = "";
								}

							}
							else {
								//usabled = URLEncoder.encode(usabled, StandardCharsets.UTF_8);

								postname = usabled;
								dataoutp = "Please send the body of the thread/comment you would like to create.\r";
								if (termconnect == false) {

									dataoutp = dataoutp.length() + " " + dataoutp;
									byte[] datadata = dataoutp.getBytes();
									
									dataout.write(datadata);
									curbuf = 1;
									option = 9;
									usabled = "";
								}
								else {
									System.out.println(dataoutp);
									dataoutp = "";
									option = 9;
									usabled = "";
								}

							}

						}
					}
					if (option == 9) {
						if (usabled != "") {
							boolean postsafe = true;
							dataoutp = "";
							postbody = usabled;

	                        if (postname.contains("porn") || postname.contains(" sex ") || postname.contains("fuck") || postname.contains("shit") || postname.contains("bitch") || postname.contains(" ass ") || postname.contains("pussy") || postname.contains("hentai") || postname.contains("xvideos")) {
	                            logs = logs + rcall + " attempted to post a thread named '"+postname+"' but was blocked\n";
	                            postsafe = false;
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        if (postbody.contains("porn") || postbody.contains(" sex ") || postbody.contains("fuck") || postbody.contains("shit") || postbody.contains("bitch") || postbody.contains(" ass ") || postbody.contains("pussy") || postbody.contains("hentai") || postbody.contains("xvideos")) {
	                            logs = logs + rcall + " attempted to post a thread or comment with content '"+postbody+"' but was blocked\n";
	                            postsafe = false;
	                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
                            //System.out.println("Logs:\n-----\n" + logs + "\n-----");

                            if (postsafe == true) {
    							logs = logs + rcall + " requested an upload for a thread with title '" + postname + "' with contents '" + postbody + "'\n";
                                st = "";
    							String pathToClone = "./repo";
    							Path directory = Path.of(pathToClone);
    							try {
    								Files.walk(directory)
    				                .sorted(Comparator.reverseOrder())
    				                .map(Path::toFile)
    				                .forEach(File::delete);
    							} catch (java.nio.file.NoSuchFileException e) {
    								
    							}

    						        Git git = Git.cloneRepository()
    						                .setURI("https://github.com/Glitch31415/rws.git")
    						                .setDirectory(new File(pathToClone))
    						                .call();

    							        try {
    							        	try {
    							        		File myObjp;
    							        		stp = "";
    							        		pexists = false;
    								        	      myObjp = new File(git.getRepository().getDirectory().getParent() + "/community/", postname);


    							        	      Scanner myReaderp = new Scanner(myObjp);
    							        	      while (myReaderp.hasNextLine()) {
    							        	        stp = stp + myReaderp.nextLine() + "\n";
    							        	        pexists = true;
    							        	      }
    							        	      myReaderp.close();
    							        	    } catch (FileNotFoundException e) {
    							        	    	
    							        	    }
    							            //System.out.println("index was read as '" + st + "'");
    							            FileWriter myWriterp;

    								            myWriterp = new FileWriter(git.getRepository().getDirectory().getParent() + "/community/" + postname);
    								            Instant instantp = Instant.now();

    								            // Set the time zone to GMT
    								            ZoneId zonep = ZoneId.of("GMT");

    								            // Format the date and time as a string
    								            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    								            String dateTime = instantp.atZone(zonep).format(formatter);
    								            if (pexists == false) {
    									        	myWriterp.write(postname+"\n\n"+rcall+"\n"+dateTime+" UTC"+"\n----------\n"+postbody+"\n----------\n\n");
    								            }
    								            else {
    								            	myWriterp.write(stp + "\n"+rcall+"\n"+dateTime+" UTC"+"\n----------\n"+postbody+"\n----------\n\n");
    								            }
    								       myWriterp.close();
    							          } catch (IOException e) {
    							            e.printStackTrace();
    							        }
    							        //git.add().addFilepattern(postname).call();
    							       //if (windows == true) {
    							        	//git.add().addFilepattern(".").call();
    						            //}
    						            //else {
    						            	//git.add().addFilepattern(".").call();
    						            //}
    							        try {
    							        	try {
    							        		File myObj;
    								        	      myObj = new File(git.getRepository().getDirectory().getParent() + "/community/index");


    							        	      Scanner myReader = new Scanner(myObj);
    							        	      while (myReader.hasNextLine()) {
    							        	    	  String tempnextline = myReader.nextLine();
    							        	    	  if (tempnextline.contains("'" + postname + "'")) {
    							        	    		  
    							        	    	  }
    							        	    	  else {
    									        	        st = st + tempnextline + "\n";
    							        	    	  }

    							        	      }
    							        	      myReader.close();
    							        	    } catch (FileNotFoundException e) {
    							        	      e.printStackTrace();
    							        	    }
    							            //System.out.println("index was read as '" + st + "'");
    							            FileWriter myWriter;

    								            myWriter = new FileWriter(git.getRepository().getDirectory().getParent() + "/community/index");
    								            Instant instant = Instant.now();

    								            // Set the time zone to GMT
    								            ZoneId zone = ZoneId.of("GMT");

    								            // Format the date and time as a string
    								            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    								            String dateTime = instant.atZone(zone).format(formatter);
    								        	myWriter.write("'"+postname+"'"+" - "+rcall+", "+dateTime+" UTC"+ "\n" + st);
    								       myWriter.close();
    							          } catch (IOException e) {
    							            e.printStackTrace();
    							        }
    							        //git.add().addFilepattern("/community/index").call();
    							        //if (windows == true) {
    							        	//git.add().addFilepattern("\\community\\index").call();
    						            //}
    						            //else {
    						            	//git.add().addFilepattern("/community/index").call();
    						            //}
    							        git.add().addFilepattern(".").call();
    							        git.commit().setMessage("Committed from server").call();
    							        byte[] decodedBytes = Base64.getDecoder().decode(Base64.getDecoder().decode("WjJod1gxRlVSRlJoWlZSaU5qSmtSbVkwVkdwNmVESXlXSFE1U1d0NlpHUnVSekZGVERsME53PT0="));
    							        String decodedString = new String(decodedBytes);
    							        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(decodedString, "")).call(); // if anyone else sees this please don't break everything
    							        git.close();
    							        git = null;
    							        //Git.shutdown();
    							        
    							        try {
    										Files.walk(directory)
    						                .sorted(Comparator.reverseOrder())
    						                .map(Path::toFile)
    						                .forEach(File::delete);
    									} catch (java.nio.file.NoSuchFileException e) {
    										
    									}

    									option = 6;
    									usabled = "";
                            }
									URLConnection connection = null;
			                        try {
				                            connection = new URL("https://raw.githubusercontent.com/Glitch31415/rws/main/community/index").openConnection();
				                            webscan = new Scanner(connection.getInputStream());
				                            webscan.useDelimiter("\\Z");
				                            wstext = webscan.next();
				                            webscan.close();

			                        }
			                        catch (Exception ex) {
			                            ex.printStackTrace();
			                            wstext = ex.toString();
			                        }
			                        String lesswstext = "";
			                        int i2=0;
			                        while (i2 < wstext.split("\n").length) {
			                        	if (i2 < 25) {
			                        		lesswstext = lesswstext + wstext.split("\n")[i2] + "\n"; 
			                        	}
			                        	i2 = i2 + 1;
			                        }
			                        wstext = lesswstext;
			                        	wstext = wstext.replaceAll("\\r", "");
			                        	if (postsafe == true) {
				                        	dataoutp = wstext + "\nYour thread/comment has been uploaded!\n\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the community area?\nCommands: |w |s |f |d |c |i";
			                        	}
			                        	else {
			                        		dataoutp = wstext + "\nOops, that contained material that is inappropriate for ham radio. Please try something else.\n\nUse the command 'view' then '|all' to see all threads, this is just the most recently modified 25.\nWould you like to 'view' or 'create' a thread/comment in the community area?\nCommands: |w |s |f |d |c |i";
			                        	}
			                            logs = logs + rcall + " looked at the community index page\n";

			                        encodedString = dataoutp;
			                        

			                        if (termconnect == false) {
			                        	filebytesleft = encodedString.length();
										//System.out.println(filebytesleft + " bytes to send");
										dataoutp = (encodedString.length()+2) + " \n\r";
				                        byte[] datadata = dataoutp.getBytes();
				                        
										dataout.write(datadata);
										curbuf = 1;
										dataoutp = "";

											while (filebytesleft > 1024) {
												if (getstream1.readingcmds == prevrcind) {
													Thread.sleep(100);
													if (getstream1.readingcmds == prevrcind) {
														readyforreadingc = true;

													}

												}
												else {
													readyforreadingc = false;
													prevrcind = getstream1.readingcmds;
												}
												if (getstream1.gcmdsin != null) {
													if (readyforreadingc == true) {
														if (getstream1.gcmdsin.length() > cind) {

															usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

															cind = getstream1.gcmdsin.length();
															//System.out.println("CMD: " + usablec);
														} else {
															usablec = "";
														}
													}
													else {
														usablec = "";
													}

												}
						        					if (usablec.length() > usablec.lastIndexOf("BUFFER")+6) {
						        						if (usablec.charAt(usablec.lastIndexOf("BUFFER")+6) == ' ') {
						        							int thing = 7;
						        							String thingcounter = "";
						        							while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+thing))) {
						        								thingcounter = thingcounter + usablec.charAt(usablec.lastIndexOf("BUFFER")+thing);
						        								thing = thing + 1;
						        							}
						        							if (thingcounter != "") {
						        								curbuf = Integer.parseInt(thingcounter);
						        							}
						        						}
						        					}
												if (usablec.contains("BUFFER")) {
													int i = 7;
													String numbuild = "";
													
													if (usablec.lastIndexOf("BUFFER")+i < (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
														if (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i))) {
															boolean kloop = true;
															while (Character.isDigit(usablec.charAt(usablec.lastIndexOf("BUFFER")+i)) && kloop == true) {
																numbuild = numbuild + usablec.charAt(usablec.lastIndexOf("BUFFER")+i);
																i = i + 1;
																if (i >= (usablec.length()-usablec.lastIndexOf("BUFFER"))) {
																	kloop = false;
																}
															}
															numbuf = Integer.parseInt(numbuild);
														}
													}

												}
												if (usablec.contains("DISCONN")) {
													filebytesleft = 0;
													dataoutp = "";
													option = 0;
												}
												if (numbuf < 31744) {
													encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
													dataoutp = dataoutp + encodedStringpart;
							                        datadata = dataoutp.getBytes();
													dataout.write(datadata);
													dataoutp = "";
													filebytesleft = filebytesleft - 1024;
													numbuf = numbuf + 1024;
												}
											}
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = 0;
											usabled = "";
			                        }
			                        else {
			                        	System.out.println(dataoutp);
			                        	dataoutp = "";
			                        	usabled = "";
			                        }
									option = 6;


					        //System.out.println("remade local repo"); 
						     
						}
						usabled = "";
					}
					

				}
				//System.out.println("Logs:\n-----\n" + logs + "\n-----");
				// main loop things end
				if (initwait > 3) {
					cmdsoutp = "BW2300\r";
					cmdsdata = cmdsoutp.getBytes();
					cmdsout.write(cmdsdata);
					initwait = 2;
					if (termconnect == true) {
						cmdsoutp = "DISCONNECT\r";
						cmdsdata = cmdsoutp.getBytes();
						cmdsout.write(cmdsdata);
					}
				}
				else {
					initwait = initwait + 1;
				}
				Thread.sleep(1000);


		}
	}

}
