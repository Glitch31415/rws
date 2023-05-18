package VRWS;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

class getstream1 implements Runnable {
	public static String gcmdsin = "";
	public static String gcmdsout;
	public static Socket cmds = null;
	public static Integer readingcmds = 0;

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
					if ((char)gcmds != '\n' && (char)gcmds != '\r') {
					    gcmdsin = gcmdsin + (char)gcmds;
					}

				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

        //br2.lines().forEach(line2 -> gdatain=gdatain+line2);
	}
}

class getstream2 implements Runnable {
	public static String gdatain = "";
	public static String gdataout;
	public static Socket data = null;
	public static Integer readingdata = 0;
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
        Integer totalnum = 0;
        Integer charnum = 0;
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
							totalnum = Integer.valueOf(totalnumbuild);
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

public class mainclass {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException, NoFilepatternException, GitAPIException {
		String usablec = "";
		String usabled = "";
		String wstext = "";
		String textscanthing = "";
		Scanner webscan;
		Integer option = 0;
		String dataoutp;
		String rcall = "";
		String searchthing = "";
		String weatherthing = "";
		String logs = "";
		String encodedString = "";
		String encodedStringpart = "";
		Integer cind = 8;
		Integer dind = 0;
		Integer rcallind = 0;
		Integer filebytesleft = 0;
		Integer numbuf = 0;
		String weatherend = "";
		String[] latLng = null;
		String st = "";
		String downloadcheck = "";
		
		boolean beginning = true;
		boolean beggood = false;

		boolean formremove = false;
		boolean conn = false;
		boolean connmsg = false;
		boolean readyforreading = true;
		boolean readyforreadingc = true;
		Integer prevrdind = 0;
		Integer prevrcind = 0;
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
        System.out.println("Enter exact callsign:");
        String callsign = callinp.nextLine();
		String cmdsoutp = "MYCALL "+callsign+"\rPUBLIC ON\rLISTEN ON\rCHAT ON\rCLEANTXBUFFER\r";
		String prevdatainthing = "";
		boolean weatherbroke = false;
		byte[] cmdsdata = cmdsoutp.getBytes();
		String postname = "";
		String postbody = "";
		cmdsout.write(cmdsdata);
		Thread.sleep(1000);
		while (0==0) {
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
				if (prevdatainthing == getstream2.gdatain) {
					usabled = "";
				}
				else {
					if (getstream2.gdatain.contains("&IT&") || getstream2.gdatain.contains(rcall + " <R") ) {
						usabled = "";
					}
					else {
						usabled = getstream2.gdatain;
						System.out.println("DATA: " + usabled);
						prevdatainthing = getstream2.gdatain;

					}

				}
				if (getstream1.gcmdsin != null) {
					if (readyforreadingc == true) {
						if (getstream1.gcmdsin.length() > cind) {

							usablec = getstream1.gcmdsin.substring(cind,getstream1.gcmdsin.length());

							cind = getstream1.gcmdsin.length();
							System.out.println("CMD: " + usablec);
						} else {
							usablec = "";
						}
					}
					else {
						usablec = "";
					}

				}
				// main loop things
				if (usablec.contains("CONNECTED")) {
					if (conn == false) {
						conn = true;
						connmsg = true;
					}
					else {
						conn = false;
						logs = logs + rcall + " disconnected\n";
						System.out.println("Logs:\n-----\n" + logs + "\n-----");
						option = 0;
					}
				}
				
				if (conn == true) {
					if (connmsg == true) {
						rcallind = usablec.indexOf("CONNECTED") + 10;
						rcall = "";
						while (usablec.charAt(rcallind) != ' ') {
							rcall = rcall + usablec.charAt(rcallind);
							rcallind = rcallind + 1;
						}
						if (rcall != "USY") {
							dataoutp = "";
							dataoutp = "Welcome to the VARA Radio Web Services (VRWS) server, " + rcall + "! You can fetch the html or text from a website by saying 'website'. You can do a quick Google search by saying 'search'. You can check the weather for a given city by saying 'weather'. You can download files from a URL by saying 'download'. You can view or create posts or files on the community folder in the github by saying 'community'. You can return the server logs by saying 'status'. All commands are case sensitive.\r";
							logs = logs + rcall + " connected\n";
							System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
							connmsg = false;
						}
						else {
							conn = false;
							option = 0;
							cmdsoutp = "CLEANTXBUFFER\r";
							cmdsdata = cmdsoutp.getBytes();
							cmdsout.write(cmdsdata);
						}

					}
					if (option != 0) {
						if (usabled.contains("|exit")) {
							option = 0;
							logs = logs + rcall + " went back to the main menu\n";
							System.out.println("Logs:\n-----\n" + logs + "\n-----");
							dataoutp = "Commands: website, search, weather, download, community, status. All commands are case sensitive.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (option == 1) {
						if (usabled != "") {
							if (usabled.contains("^")) {
								dataoutp = "Here is the text from the website you provided.\n-----\n";
								usabled = usabled.replace("^", "");
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
		                            dataoutp = dataoutp + "Oops, your website contained material that is inappropriate for ham radio. Please try a different website.\n-----\nSay '|exit' to return to the main menu.\r";
		                            logs = logs + rcall + " tried to fetch text from URL " + textscanthing + " but was blocked\n";
		                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        } else {
		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + wstext + "\n-----\nSay '|exit' to return to the main menu.\r";
		                            logs = logs + rcall + " fetched text from URL " + textscanthing + "\n";
		                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        }
		                        encodedString = dataoutp;
		                        filebytesleft = encodedString.length();
								System.out.println(filebytesleft + " bytes to send");
								dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
		                        byte[] datadata = dataoutp.getBytes();
								dataout.write(datadata);
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
													System.out.println("CMD: " + usablec);
												} else {
													usablec = "";
												}
											}
											else {
												usablec = "";
											}

										}
										if (usablec.contains("BUFFER")) {
											Integer i = 7;
											String numbuild = "";

											while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
												numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
												i = i + 1;
											}
											numbuf = Integer.valueOf(numbuild);
										}
										if (usablec.contains("DISCONN")) {
											filebytesleft = 0;
											dataoutp = "";
											option = 0;
										}
										if (numbuf < 4096) {
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = filebytesleft - 1024;
											numbuf = numbuf + 1024;
										}
										Thread.sleep(1000);
									}
									encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
									dataoutp = dataoutp + encodedStringpart;
			                        datadata = dataoutp.getBytes();
									dataout.write(datadata);
									dataoutp = "";
									filebytesleft = 0;
							}
							else {
								dataoutp = "Here is the raw HTML from the website you provided.\n-----\n";
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
		                            dataoutp = dataoutp + "Oops, your website contained material that is inappropriate for ham radio. Please try a different website.\n-----\nSay '|exit' to return to the main menu.\r";
		                            logs = logs + rcall + " tried to fetch html from URL " + usabled + " but was blocked\n";
		                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        } else {
		                        	wstext = wstext.replaceAll("\\r", "");
		                            dataoutp = dataoutp + wstext + "\n-----\nSay '|exit' to return to the main menu.\r";
		                            logs = logs + rcall + " fetched html from URL " + usabled + "\n";
		                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        }								
		                        encodedString = dataoutp;
		                        filebytesleft = encodedString.length();
								System.out.println(filebytesleft + " bytes to send");
								dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
		                        byte[] datadata = dataoutp.getBytes();
								dataout.write(datadata);
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
													System.out.println("CMD: " + usablec);
												} else {
													usablec = "";
												}
											}
											else {
												usablec = "";
											}

										}
										if (usablec.contains("BUFFER")) {
											Integer i = 7;
											String numbuild = "";

											while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
												numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
												i = i + 1;
											}
											numbuf = Integer.valueOf(numbuild);
										}
										if (usablec.contains("DISCONN")) {
											filebytesleft = 0;
											dataoutp = "";
											option = 0;
										}
										if (numbuf < 4096) {
											encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
											dataoutp = dataoutp + encodedStringpart;
					                        datadata = dataoutp.getBytes();
											dataout.write(datadata);
											dataoutp = "";
											filebytesleft = filebytesleft - 1024;
											numbuf = numbuf + 1024;
										}
										Thread.sleep(1000);
									}
									encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
									dataoutp = dataoutp + encodedStringpart;
			                        datadata = dataoutp.getBytes();
									dataout.write(datadata);
									dataoutp = "";
									filebytesleft = 0;
							}
						}
					}
					if (option == 2) {
						if (usabled != "") {
							dataoutp = "Here are the results of your search.\n-----\n";
							searchthing = usabled;
							usabled = usabled.replaceAll(" ", "+");
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
	                            dataoutp = dataoutp + "Oops, your results contained material that is inappropriate for ham radio. Please try a different query.\n-----\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " attempted to search " + searchthing + " but was blocked\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        } else {
	                        	wstext = wstext.replaceAll("\\r", "");
	                            dataoutp = dataoutp + wstext + "\n-----\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " searched for " + searchthing + "\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        encodedString = dataoutp;
	                        filebytesleft = encodedString.length();
							System.out.println(filebytesleft + " bytes to send");
							dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
	                        byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
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
												System.out.println("CMD: " + usablec);
											} else {
												usablec = "";
											}
										}
										else {
											usablec = "";
										}

									}
									if (usablec.contains("BUFFER")) {
										Integer i = 7;
										String numbuild = "";

										while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
											numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
											i = i + 1;
										}
										numbuf = Integer.valueOf(numbuild);
									}
									if (usablec.contains("DISCONN")) {
										filebytesleft = 0;
										dataoutp = "";
										option = 0;
									}
									if (numbuf < 4096) {
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = filebytesleft - 1024;
										numbuf = numbuf + 1024;
									}
									Thread.sleep(1000);
								}
								encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
								dataoutp = dataoutp + encodedStringpart;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
								dataoutp = "";
								filebytesleft = 0;
							
						}
					}
					if (option == 3) {
						if (usabled != "") {
							dataoutp = "Here is the weather forecast for the location you provided.\n-----\n";
							weatherbroke = false;
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
					            System.out.println(ioe.toString());
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
						            System.out.println(ioe.toString());

						        }
					        }
					        else {
					        	weatherend = "Oops, I was unable to get the weather from your input. Please try again.";
					        }
					        
					        wstext = weatherend;
	                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
	                            dataoutp = dataoutp + "Oops, your results contained material that is inappropriate for ham radio. Please try a different city.\n-----\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " attempted to get the weather for " + weatherthing + " but was blocked\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        } else {
	                        	wstext = wstext.replaceAll("\\r", "");
	                            dataoutp = dataoutp + wstext + "\n-----\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " got the weather for " + weatherthing + "\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        encodedString = dataoutp;
	                        filebytesleft = encodedString.length();
							System.out.println(filebytesleft + " bytes to send");
							dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
	                        byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
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
												System.out.println("CMD: " + usablec);
											} else {
												usablec = "";
											}
										}
										else {
											usablec = "";
										}

									}
									if (usablec.contains("BUFFER")) {
										Integer i = 7;
										String numbuild = "";

										while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
											numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
											i = i + 1;
										}
										numbuf = Integer.valueOf(numbuild);
									}
									if (usablec.contains("DISCONN")) {
										filebytesleft = 0;
										dataoutp = "";
										option = 0;
									}
									if (numbuf < 4096) {
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = filebytesleft - 1024;
										numbuf = numbuf + 1024;
									}
									Thread.sleep(1000);
								}
								encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
								dataoutp = dataoutp + encodedStringpart;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
								dataoutp = "";
								filebytesleft = 0;
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
		                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        } else {
									encodedString = Base64.getEncoder().encodeToString(fileContent);
									logs = logs + rcall + " downloaded " + usabled + "\n";
									System.out.println("Logs:\n-----\n" + logs + "\n-----");
		                        }

							}
							catch (Exception badthing) {
								badthing.printStackTrace();
	                            encodedString = badthing.toString();
							}


							filebytesleft = encodedString.length();
							System.out.println(filebytesleft + " bytes to send");
							dataoutp = (encodedString.length()+23+281) + " Download started.\n-----\r";
	                        byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
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
												System.out.println("CMD: " + usablec);
											} else {
												usablec = "";
											}
										}
										else {
											usablec = "";
										}

									}
									if (usablec.contains("BUFFER")) {
										Integer i = 7;
										String numbuild = "";

										while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
											numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
											i = i + 1;
										}
										numbuf = Integer.valueOf(numbuild);
									}
									if (usablec.contains("DISCONN")) {
										filebytesleft = 0;
										dataoutp = "";
										option = 0;
									}
									if (numbuf < 4096) {
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = filebytesleft - 1024;
										numbuf = numbuf + 1024;
									}
									Thread.sleep(1000);
								}
								encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
								dataoutp = dataoutp + encodedStringpart;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
								dataoutp = "";
								filebytesleft = 0;
					

							
							dataoutp = "";
							dataoutp = dataoutp + "\n-----\nYou can decode this info by copying the text between the dashes, saving the text as 'data.b64' then running 'certutil -decode data.b64 downloadedfile' in the command line in Windows or 'base64 -d data.b64 > downloadedfile' in Linux.\nSay '|exit' to return to the main menu.\r";
	                        //dataoutp = dataoutp.length() + " " + dataoutp;
	                        datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (option == 6) {
						if (usabled != "") {
								if (usabled.contains("view")) {
									//view posts
									dataoutp = "Please send the name of the post you would like to view.\n-----\n";
									searchthing = usabled;
									usabled = usabled.replaceAll(" ", "+");
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
			                        if (wstext.contains("porn") || wstext.contains(" sex ") || wstext.contains("fuck") || wstext.contains("shit") || wstext.contains("bitch") || wstext.contains(" ass ") || wstext.contains("pussy") || wstext.contains("hentai") || wstext.contains("xvideos")) {
			                            dataoutp = dataoutp + "Oops, the index page contained material that is inappropriate for ham radio. Please try a different query.\n-----\nSay '|exit' to return to the main menu.\r";
			                            logs = logs + rcall + " attempted to look at the community index page but was blocked\n";
			                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        } else {
			                        	wstext = wstext.replaceAll("\\r", "");
			                            dataoutp = dataoutp + wstext + "\n-----\nSay '|exit' to return to the main menu.\r";
			                            logs = logs + rcall + " looked at the community index page\n";
			                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
			                        }
			                        encodedString = dataoutp;
			                        filebytesleft = encodedString.length();
									System.out.println(filebytesleft + " bytes to send");
									dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
			                        byte[] datadata = dataoutp.getBytes();
									dataout.write(datadata);
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
														System.out.println("CMD: " + usablec);
													} else {
														usablec = "";
													}
												}
												else {
													usablec = "";
												}

											}
											if (usablec.contains("BUFFER")) {
												Integer i = 7;
												String numbuild = "";

												while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
													numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
													i = i + 1;
												}
												numbuf = Integer.valueOf(numbuild);
											}
											if (usablec.contains("DISCONN")) {
												filebytesleft = 0;
												dataoutp = "";
												option = 0;
											}
											if (numbuf < 4096) {
												encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
												dataoutp = dataoutp + encodedStringpart;
						                        datadata = dataoutp.getBytes();
												dataout.write(datadata);
												dataoutp = "";
												filebytesleft = filebytesleft - 1024;
												numbuf = numbuf + 1024;
											}
											Thread.sleep(1000);
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
								if (usabled.contains("create")) {
									//create posts
									dataoutp = "Please send the name of the post you would like to create.\r";
									dataoutp = dataoutp.length() + " " + dataoutp;
									byte[] datadata = dataoutp.getBytes();
									dataout.write(datadata);
									option = 8;
									usabled = "";
								}
						}
					}
					if (option == 7) {
						if (usabled != "") {
							dataoutp = "Here is the post you requested.\n-----\n";
							usabled = "https://raw.githubusercontent.com/Glitch31415/rws/main/community/" + usabled;
							searchthing = usabled;
							usabled = usabled.replaceAll(" ", "+");
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
	                            dataoutp = dataoutp + "Oops, that post contained material that is inappropriate for ham radio. Please try a different query.\n-----\nWould you like to 'view' or 'create' something in the community area?\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " attempted to look at the post " + searchthing + "but was blocked\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        } else {
	                        	wstext = wstext.replaceAll("\\r", "");
	                            dataoutp = dataoutp + wstext + "\n-----\nWould you like to 'view' or 'create' something in the community area?\nSay '|exit' to return to the main menu.\r";
	                            logs = logs + rcall + " looked at the post " + searchthing + "\n";
	                            System.out.println("Logs:\n-----\n" + logs + "\n-----");
	                        }
	                        encodedString = dataoutp;
	                        filebytesleft = encodedString.length();
							System.out.println(filebytesleft + " bytes to send");
							dataoutp = (encodedString.length()+19) + " Transfer started.\n\r";
	                        byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
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
												System.out.println("CMD: " + usablec);
											} else {
												usablec = "";
											}
										}
										else {
											usablec = "";
										}

									}
									if (usablec.contains("BUFFER")) {
										Integer i = 7;
										String numbuild = "";

										while (Character.isDigit(usablec.charAt(usablec.indexOf("BUFFER")+i))) {
											numbuild = numbuild + usablec.charAt(usablec.indexOf("BUFFER")+i);
											i = i + 1;
										}
										numbuf = Integer.valueOf(numbuild);
									}
									if (usablec.contains("DISCONN")) {
										filebytesleft = 0;
										dataoutp = "";
										option = 0;
									}
									if (numbuf < 4096) {
										encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length()-filebytesleft+1024);
										dataoutp = dataoutp + encodedStringpart;
				                        datadata = dataoutp.getBytes();
										dataout.write(datadata);
										dataoutp = "";
										filebytesleft = filebytesleft - 1024;
										numbuf = numbuf + 1024;
									}
									Thread.sleep(1000);
								}
								encodedStringpart = encodedString.substring(encodedString.length()-filebytesleft, encodedString.length());
								dataoutp = dataoutp + encodedStringpart;
		                        datadata = dataoutp.getBytes();
								dataout.write(datadata);
								dataoutp = "";
								filebytesleft = 0;
								option = 6;
						}
					}
					if (option == 8) {
						if (usabled != "") {
							if (usabled.contains("/") || usabled.contains(".") || usabled.contains("README") || usabled == "index" ) {
								dataoutp = "Oops, that title is invalid! Please make a title that does not include '/', '.', 'README', or a title named 'index'.\r";
								dataoutp = dataoutp.length() + " " + dataoutp;
								byte[] datadata = dataoutp.getBytes();
								dataout.write(datadata);
								usabled = "";
							}
							else {
								postname = usabled;
								dataoutp = "Please send the body of the post you would like to create.\r";
								dataoutp = dataoutp.length() + " " + dataoutp;
								byte[] datadata = dataoutp.getBytes();
								dataout.write(datadata);
								option = 9;
								usabled = "";
							}

						}
					}
					if (option == 9) {
						if (usabled != "") {
							dataoutp = "Uploading...\r";
							dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
							dataoutp = "";
							st = "";
							postbody = usabled;
							String pathToClone = "./repo";
					        Git git = Git.cloneRepository()
					                .setURI("https://github.com/Glitch31415/rws.git")
					                .setDirectory(new File(pathToClone))
					                .call();
					        System.out.println("remade local repo");
						        new File(git.getRepository().getDirectory().getParent() + "/community/", postname);
					        try {
					        	FileWriter myWriter;
						            myWriter = new FileWriter(git.getRepository().getDirectory().getParent() + "/community/" + postname);
							        System.out.println("writing the file to " + git.getRepository().getDirectory().getParent() + "/community/" + postname);
					            myWriter.write(postbody);
					            System.out.println("wrote " + postbody);
					            myWriter.close();
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
					        	        st = st + myReader.nextLine() + "\n";
					        	      }
					        	      myReader.close();
					        	    } catch (FileNotFoundException e) {
					        	      e.printStackTrace();
					        	    }
					            System.out.println("index was read as '" + st + "'");
					            FileWriter myWriter;

						            myWriter = new FileWriter(git.getRepository().getDirectory().getParent() + "/community/index");


					            
					            myWriter.write(st+postname+"\n");
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
					        byte[] decodedBytes = Base64.getDecoder().decode("Z2hwX1lQdFNXSTBiaG9uYlpnalBuRE14VnNibVN5UkkyUDBjbkFkRw==");
					        String decodedString = new String(decodedBytes);
					        git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(decodedString, "")).call(); //if anyone else sees this please don't break everything
					        Path directory = Path.of(pathToClone);
					        Files.walk(directory)
			                .sorted(Comparator.reverseOrder())
			                .map(Path::toFile)
			                .forEach(File::delete);
							dataoutp = "Your post has been uploaded!\nWould you like to 'view' or 'create' something in the community area?\nSay '|exit' to return to the main menu.\r";
							dataoutp = dataoutp.length() + " " + dataoutp;
							datadata = dataoutp.getBytes();
							dataout.write(datadata);
							option = 6;
							usabled = "";
						}
					}
					if (usabled.contains("website")) {
						if (option == 0) {
							option = 1;
							dataoutp = "Please provide the exact URL of the website you want to fetch. Example: 'https://www.example.com'. If you want a text-only website, please add a carat (^) behind the start of the URL. Example: '^https://www.example.com'.\nSay '|exit' to return to the main menu.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (usabled.contains("search")) {
						if (option == 0) {
							option = 2;
							dataoutp = "Please provide your query.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (usabled.contains("weather")) {
						if (option == 0) {
							option = 3;
							dataoutp = "Please provide the city and state you would like the weather for.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (usabled.contains("status")) {
						if (option == 0) {
							option = 4;
							dataoutp = "Logs:\n-----\n" + logs + "\n-----\nCommands: website, search, weather, download, community, status. All commands are case sensitive.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
							option = 0;
						}
					}
					if (usabled.contains("download")) {
						if (option == 0) {
							option = 5;
							dataoutp = "Please provide the URL of the file you would like to download.\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}
					if (usabled.contains("community")) {
						if (option == 0) {
							option = 6;
							dataoutp = "Would you like to 'view' or 'create' something in the community area?\r";
	                        dataoutp = dataoutp.length() + " " + dataoutp;
							byte[] datadata = dataoutp.getBytes();
							dataout.write(datadata);
						}
					}

				}
				//System.out.println("Logs:\n-----\n" + logs + "\n-----");
				// main loop things end
				Thread.sleep(1000);
		}
	}

}
