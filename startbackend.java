package rwsbackendpackage;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.math.*;

import java.util.List;
class sgh implements Runnable {
	public static Git git = null;
	public static boolean writing = false;
	public static int req = 0;
	public static boolean[] dms = {false, false, false, false, false, false, false, false, false, false};
	public static long ldms = 0;
	public static long lastdms = System.currentTimeMillis();
	public static long btime = 0;
	public static long ntimer = System.nanoTime();
	public static int threadindex = 0;
	public static int otherrs = 0;
	public static int ee = 0;
	public static int cr = 0;
	public static int utfe = 0;
	public static int te = 0;
	public static int bp = 0;
	public static String vlogs = "";
	public static boolean busy = false;
	public static String rwskey1 = "[rwskey1]";
	public static String rwskey2 = "[rwskey2]";
	public static String discordkey;
	public static String githubkey;
	public void run() {
				try {
		    	FileUtils.deleteDirectory(new File(System.getProperty("user.home")+File.separator+"repofolder"));
				git = Git.cloneRepository()
			                .setURI("https://github.com/Glitch31415/rwsbackend.git")
			                .setDirectory(new File(System.getProperty("user.home")+File.separator+"repofolder"))
			                .setDepth(1)
			                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubkey, ""))
			                .call();
				} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
				JDA jda = JDABuilder.createDefault(sgh.discordkey).build();
				try {
		        jda.awaitReady();
				} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
		        List<TextChannel> channels = jda.getTextChannelsByName("status", true);
			    System.out.println("git cloned and discord prepared");
			    while (0==0) {
			    	try {
			    	Thread.sleep(300000);
			    	} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
			    	try {
				    	while(writing==true || busy == true){Thread.sleep(0);}writing=true;busy=true;
			    	} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
				    	
				    	System.out.println("committing files");
			    		   
						  		
						  		BigDecimal dividend = new BigDecimal(btime*10); // divided by 10 for 10 threads, multiplied by 100 to make percentage
						  		btime = 0;
						  		ntimer = System.nanoTime() - ntimer;
						  		BigDecimal divisor = new BigDecimal(ntimer);
						  		ntimer = System.nanoTime();

						  		BigDecimal result = dividend.divide(divisor, 5, RoundingMode.HALF_EVEN);
						  		
		  		
						  		// backendstatus
						  		FileWriter myWriterp;
						  		try {
    				            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"backendstatus");
    			            	myWriterp.write("Time of this commit: " + System.currentTimeMillis() + "\nSince last commit:\nRequests made to backend: " + req + "\nApproximate busy time: " + result.toString() + "%\nThreads responded: " + ldms + "/10"); // write the new thing before the rest of stp and into the file
    			            	myWriterp.close();
						  		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
						  		
						  		try {
	    				            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"verbosebackendlogs");
	    			            	myWriterp.write("EOFExceptions: " + ee + "\nConnections reset: " + cr + "\nUTFDataFormatExceptions: " + utfe + "\nTransportExceptions: " + te + "\nBroken pipes: " + bp + "\nEverything else: (" + otherrs + ")\n" + vlogs);
	    			            	myWriterp.close();
							  	} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
						  		
						  		try {
						  		git.add().addFilepattern(".").call();
						  		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
						        Date d2 = new Date(); 
							  		git.gc().setAggressive(true).setExpire(d2);
							  		try {
					        git.commit().setMessage("r: " + req + ", u: " + result.toString() + "%, t: " + ldms + "/10").call(); // request count, usage percent, and threads that are responsive. usage multiplied by 100 to make percentage, divided by 100 because of 100 threads
							  		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
					        
					        

					        try {
					        git.push().setForce(true).setCredentialsProvider(new UsernamePasswordCredentialsProvider(sgh.githubkey, "")).call();
					        } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
					        // discord start
					        
					        File myObjp;
	    	        	      myObjp = new File(sgh.git.getRepository().getDirectory().getParent(), "activeservers");
	    	        		String stp = "";
	    	        		try {
	    	        	      Scanner myReaderp = new Scanner(myObjp);
	    	        	      

	    	        	      while (myReaderp.hasNextLine()) {
	    	        	    	String servupdtemp = myReaderp.nextLine();

	    	        	    	if (servupdtemp != "") { 

	    			        	        stp = stp + servupdtemp + "\n"; // read into stp

	    	        	    	}


	    	        	      }
	    	        	      myReaderp.close();
	    	        		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
					        
	    	        		int actservs = 0;
	    	        		if (stp.contains("\n")) {
			                	actservs = stp.split("\n").length;
			                }
			                else {
			                	actservs = 1;
			                }
	    	        		if (stp.length() > 1500) {
	    	        			stp = stp.substring(0, 1499);
	    	        		}
	    	        		try {
					        for(TextChannel ch : channels)
					        {
					            ch.sendMessage("Time of this commit: <t:" + (long)Math.floor(System.currentTimeMillis()/1000) + ":R>\n\nSince last commit:\nRequests made to backend: `" + req + "`\nApproximate busy time: `" + result.toString() + "%`\nThreads responded: `" + ldms + "/10`\n\nActive servers: " + actservs + "\n(first 1500 characters of list)\n```" + stp + "```").queue();
					            if (!stp.contains("KJ7RBS")) {
					            	ch.sendMessage("<@322955217538646018> your server is down").queue();
					            }
					            if (ldms < 5) {
					            	ch.sendMessage("<@998736610436857926> backend needs to restart, check logs").queue();
					            }
					            if (otherrs > 0) {
					            	ch.sendMessage("<@998736610436857926> unusual error occurred, check logs").queue();
					            }
					        }
	    	        		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
					        req = 0;
					        // discord end
					        //if (ldms < 5) {
					        	//try {
									//Thread.sleep(5000); // just here so the discord message finishes sending hopefully
								//} catch (InterruptedException e) {
									//e.printStackTrace();
								//}
					        	//System.exit(1); // after committing everything, shut down
					        //}
					        System.out.println("done");
					        writing = false;
					        busy = false;
					        System.gc();
			    }

	}
	public static void pgh(String message, DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException, NoFilepatternException, GitAPIException, InterruptedException {
		while(busy==true){Thread.sleep(0);}busy=true;
		if (message.contains(sgh.rwskey1) && message.contains(sgh.rwskey2)) {
        	System.out.println("authorized");
        	String optiontext = message.substring(message.indexOf(sgh.rwskey1)+sgh.rwskey1.length());
        	optiontext = optiontext.substring(0, optiontext.indexOf(sgh.rwskey2));
        	String bodytext = message.substring(message.indexOf(sgh.rwskey2)+sgh.rwskey2.length());
        	switch (optiontext) {
        	case "gc":
        		// get content
        		while(writing==true){Thread.sleep(0);}writing=true;
        		byte[] fileContentgc = {};
        		if (new File(System.getProperty("user.home")+File.separator+"repofolder", bodytext).exists() && bodytext != "") {
        		try {
        		fileContentgc = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+bodytext));
        		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		}
        		writing = false;
        		req = req + 1;
        		String filecontent = new String(fileContentgc, StandardCharsets.UTF_8);
        		dataOutputStream.writeUTF(filecontent + sgh.rwskey1);
                dataOutputStream.flush(); // send the message
                dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "wa":
        		// write active servers
    	        try {
            	      List<String> sutlist = new ArrayList<>();
    	        		File myObjp;
    	        		while(writing==true){Thread.sleep(0);}writing=true;
    	        	      myObjp = new File(sgh.git.getRepository().getDirectory().getParent(), "activeservers");
    	        		String stp = "";
    	        		
    	        	      Scanner myReaderp = new Scanner(myObjp);

    	        	      while (myReaderp.hasNextLine()) {
    	        	    	String servupdtemp = myReaderp.nextLine();

    	        	    	if (servupdtemp != "") { 

    			        	        stp = stp + servupdtemp + "\n"; // read into stp

    	        	    	}


    	        	      }
    	        	      myReaderp.close();


    				            FileWriter myWriterp;

    				            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"activeservers");
    				            if (bodytext.contains(" v11")) { // old server, apply compatibility thing
    				            	int sind = bodytext.indexOf(" ");
    				            	bodytext = bodytext.substring(sind+1);
    				            }
    			            	myWriterp.write(System.currentTimeMillis() + " " +  bodytext + stp); // write the new thing before the rest of stp and into the file

    				            	
    				            	

    				       myWriterp.close();
    				       stp = ""; // clear stp
    				       myReaderp = new Scanner(myObjp);
    	        	      while (myReaderp.hasNextLine()) {
    	        	    	String servupdtemp = myReaderp.nextLine();

    	        	    	if (servupdtemp != "") {
    		        	    	String[] sutstuff = servupdtemp.split(" ");
    		        	    	if (Long.parseLong(servupdtemp.substring(0, servupdtemp.indexOf(" "))) < (System.currentTimeMillis()-900000) || (sutlist.contains((sutstuff[1])))) {
    		        	    		
    		        	    	}
    		        	    	else {
    			        	        stp = stp + servupdtemp + "\n";  // reread the file and correct things
    			        	    	sutlist.add((String)(sutstuff[1]));
    		        	    	}
    	        	    	}


    	        	      }
    	        	      myReaderp.close();

    			            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"activeservers");
    		            	myWriterp.write(stp); // write the full corrected stp to the file

    			            	
    			            	

    			       myWriterp.close();
    			       writing = false;
    			        req = req + 1;
    				        dataOutputStream.writeUTF(sgh.rwskey1);
    		                dataOutputStream.flush(); // send the message
    		                
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
    	        dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "wnc":
        		// write new chat
        		
    			try {
    				FileWriter myWriterp;
            		while(writing==true){Thread.sleep(0);}writing=true;
    				myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"chat"+File.separator+bodytext);
    				 myWriterp.write("---beginning of chat---\n");
    	 	       myWriterp.close();
    			//} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
    			//try {
    				myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"chatindex", true);
    				 myWriterp.write("\n" + bodytext);
    	 	       myWriterp.close();
       			writing = false;
       			req = req + 1;
       			
       		        dataOutputStream.writeUTF(sgh.rwskey1);
                       dataOutputStream.flush(); // send the message
    			} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }

                    dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "woc":
        		// write old chat

    	                try {
	                		//tell server to add chat

	                		//tell server to add chat
		                	Instant instantp = Instant.now();


				            ZoneId zonep = ZoneId.of("GMT");


				            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				            String dateTime = instantp.atZone(zonep).format(formatter);

    	                	String caname2 = "[error]";
    		    			caname2 = bodytext.substring(0, bodytext.indexOf(sgh.rwskey2 + sgh.rwskey1));
    		    			bodytext = bodytext.substring(bodytext.indexOf(sgh.rwskey2 + sgh.rwskey1) + (sgh.rwskey2 + sgh.rwskey1).length());
    		    			//bodytext = bodytext.substring(bodytext.indexOf("---beginning"));

    		    				while(writing==true){Thread.sleep(0);}writing=true;
    		    				FileWriter myWriterp;
    	    					myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"chat"+File.separator+caname2, true);

    	    					//tempchatstr = tempchatstr.replaceAll("\n\n", "\n");
    	    					myWriterp.write("[" + dateTime + " UTC] " + bodytext);
    	    		 	       myWriterp.close();
    	    		 	       writing = false;
    	    		 	      req = req + 1;
    					        dataOutputStream.writeUTF(sgh.rwskey1);
    			                dataOutputStream.flush(); // send the message
    			                //dataOutputStream.close(); // close the output stream when we're done.
    	    				} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
    	                dataOutputStream.close(); // close the output stream when we're done.
    	                break;
        	case "wfi":
        		// write forum index
        		try {
    	        	List<String> listedposts = new ArrayList<>();
    	        		File myObjp;
    	        		while(writing==true){Thread.sleep(0);}writing=true;
    	        	      myObjp = new File(sgh.git.getRepository().getDirectory().getParent(), "forumindex");
    	        		String stp = "";
    	        		
    	        	      Scanner myReaderp = new Scanner(myObjp);

    	        	      while (myReaderp.hasNextLine()) {
    	        	    	String servupdtemp = myReaderp.nextLine();

    	        	    	if (servupdtemp != "") { 

    			        	        stp = stp + "\n" + servupdtemp; // read into stp


    	        	    	}


    	        	      }
    	        	      myReaderp.close();


    	        	      FileWriter myWriterp;
    				            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"forumindex");
    			            	myWriterp.write(bodytext + stp); // write the new thing before the rest of stp and into the file

    				            	
    				            	

    				       myWriterp.close();
    				       stp = ""; // clear stp
    				       myReaderp = new Scanner(myObjp);
    	        	      while (myReaderp.hasNextLine()) {
    	        	    	String servupdtemp = myReaderp.nextLine();
    	        	    	if (servupdtemp.contains("'")) {
    		        	    		  String tempnextname = servupdtemp.split("'")[1];
    		        	    	  if (listedposts.contains("'"+tempnextname+"'")) {
    		        	    	  }
    		        	    	  else {
    		        	    		  if (stp == "") {
    			        	    		    stp = servupdtemp;
    		        	    		  }
    		        	    		  else {
    			        	    		    stp = stp + "\n" + servupdtemp;
    		        	    		  }

    				        	        listedposts.add((String)"'"+tempnextname+"'");
    		        	    	  }
    	        	    	}


    	        	      }
    	        	      myReaderp.close();

    			            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"forumindex");
    		            	myWriterp.write(stp); // write the full corrected stp to the file

    			            	
    			            	

    			       myWriterp.close();
    			       writing = false;

    			       req = req + 1;
    			        dataOutputStream.writeUTF(sgh.rwskey1);
    	                dataOutputStream.flush(); // send the message
    	                
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "wfb":
        		// write forum body
        		try {
        			String caname = "[error]";
        			caname = bodytext.substring(0, bodytext.indexOf(sgh.rwskey2 + sgh.rwskey1));
        			bodytext = bodytext.replaceAll(sgh.rwskey2 + sgh.rwskey1, "").replaceAll(caname, "");
            	   

        				while(writing==true){Thread.sleep(0);}writing=true;
        				
        				String filecontentwfb = "";
        				byte[] fileContentgcwfb = {};
        				if (new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"forum", caname).exists() && caname != "") {
        				try {
        				fileContentgcwfb = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"forum"+File.separator+caname));
            			filecontentwfb = new String(fileContentgcwfb, StandardCharsets.UTF_8);
        				} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        				}
        				if (filecontentwfb == "") {
        					// empty thread
        					String rcallwfb = bodytext.substring(0, bodytext.indexOf(", ")).replaceAll("\n", "").replaceAll(" ", "");;

                			System.out.println(rcallwfb);
                			FileWriter myWriterp;
                			myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"forum"+File.separator + caname, false);
    		            	myWriterp.write(caname + "\n" + rcallwfb + "\n");
    		       myWriterp.close();
        				}
        				else {
        					// not empty
                			String[] fcwfbl = filecontentwfb.split("\n");
                			filecontentwfb = fcwfbl[1]; // just the list of callsigns in the thread
                			String pfcwfb = filecontentwfb;
                			System.out.println("\n\n\n" + filecontentwfb + "\n\n\n");
                			String rcallwfb = bodytext.substring(0, bodytext.indexOf(", "));
                			rcallwfb = rcallwfb.replaceAll("\n", "").replaceAll(" ", "");
                			System.out.println(rcallwfb);
                			if (!filecontentwfb.contains(rcallwfb)) {
                				filecontentwfb = filecontentwfb + ", " + rcallwfb;
                			}
                			String filecontentwfbnew = new String(fileContentgcwfb, StandardCharsets.UTF_8);
                			filecontentwfbnew = filecontentwfbnew.replaceFirst(pfcwfb, filecontentwfb);
                			FileWriter myWriterp;
                			myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"forum"+File.separator + caname, false);
    		            	myWriterp.write(filecontentwfbnew);
    		       myWriterp.close();
        				}

        				
        				FileWriter myWriterp;
    		            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"forum"+File.separator + caname, true);
    		            	myWriterp.write(bodytext);
    		       myWriterp.close();
    		       writing = false;
    		       req = req + 1;
    		        dataOutputStream.writeUTF(sgh.rwskey1);
                    dataOutputStream.flush(); // send the message
                    
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "wl":
        		// write logs
        		try {
        			System.out.println("l1");
        			while(writing==true){Thread.sleep(0);}writing=true;
    		    	   String[] things = bodytext.split("\n");
    		    	   int ll = 0;
    		    	   while (ll < things.length) {
    		    		   System.out.println("l2");
    		    		   String sthings = things[ll];
    		    		   System.out.println(sthings);
    		    		   if (sthings.contains(" connected") && !(sthings.contains("connected, but they are banned"))) {


    			    			   String ccall = sthings.substring(0, sthings.indexOf(" connected"));
    				            	File myObjpl;
    				            	//while(writing==true){Thread.sleep(0);}writing=true;
    				            	 myObjpl = new File(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator, "connections");
    				        		boolean foundcall = false;
    				        		boolean foundtitle = false;
    				        	      Scanner myReaderpl = new Scanner(myObjpl);
    				        	      String servupdtemplt = "";

    				        	      while (myReaderpl.hasNextLine()) {
    				        	    	String servupdtempl = myReaderpl.nextLine();
    				        	    	if (servupdtempl.contains("All connections to servers v92 and up: ")) {
    				        	    		foundtitle = true;
    				        	    		String gltc = servupdtempl.substring(servupdtempl.indexOf("All connections to servers v92 and up: ")+39);
    						        	      int gltcn = Integer.valueOf(gltc);

    						        	      gltcn = gltcn + 1;
    						        	      String gltcns = Integer.toString(gltcn);
    						        	      servupdtempl = "All connections to servers v92 and up: " + gltcns;
    				        	    	}
    					    			   if (servupdtempl.contains(ccall + ": ") && servupdtempl.indexOf(ccall) == 0) {
    					    				   foundcall = true;

    					    				   String stpll = servupdtempl.substring(servupdtempl.indexOf(ccall)+ccall.length()+2);

    					    				   int ccallc = Integer.valueOf(stpll);
    					    				   
    					    				   ccallc = ccallc + 1;
    					    				   String ccallcs = Integer.toString(ccallc);
    					    				   servupdtempl = ccall + ": " + ccallcs;
    					    			   }

    				        	    	if (servupdtempl == "") { 
    				        	    		if (foundtitle == false) {
    				        	    			servupdtempl = "All connections to servers v92 and up: 1\n";
    				        	    			foundtitle = true;
    				        	    		}
    				        	    		if (foundcall == false) {
    				        	    			servupdtempl = servupdtempl + ccall + ": 1";
    				        	    			foundcall = true;
    				        	    		}
    						        	        //stpl = stpl + servupdtempl + "\n"; // read into stp
    				        	    	}
    						            
    				        	    	if (servupdtempl != "") {
    					        	    	servupdtemplt = servupdtemplt + servupdtempl + "\n";
    				        	    	}
    				        	      }
    				        	      servupdtemplt.replaceAll("\n\n", "\n");
    				        	      servupdtemplt = servupdtemplt + "\n\n";
    				        	      myReaderpl.close();
    				        	      FileWriter myWriterp;
    				        	      myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"connections");
    				        	      myWriterp.write(servupdtemplt);
    				        	      myWriterp.close();
    				        	     

    		    		   }
    		    		   if (sthings.contains(" fetched text from URL ")) {
    		    			   String ccall = sthings.substring(0, sthings.indexOf(" fetched text from URL "));
    		    			   String crest = sthings.substring(sthings.indexOf(" fetched text from URL ")+23);
    		    			   FileWriter myWriterp;
    		    			   myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"website-views", true);
    		    			   myWriterp.write(ccall + ": "+crest+" (text)\n");
    		    			   myWriterp.close();
    			            	
    		    		   }
    		    		   if (sthings.contains(" fetched html from URL ")) {
    		    			   String ccall = sthings.substring(0, sthings.indexOf(" fetched html from URL "));
    		    			   String crest = sthings.substring(sthings.indexOf(" fetched html from URL ")+23);
    		    			   FileWriter myWriterp;
    		    			   myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"website-views", true);
    		    			   myWriterp.write(ccall + ": "+crest+" (HTML)\n");
    		    			   myWriterp.close();
    			            	
    		    		   }
    		    		   if (sthings.contains(" searched for ")) {
    		    			   String ccall = sthings.substring(0, sthings.indexOf(" searched for "));
    		    			   String crest = sthings.substring(sthings.indexOf(" searched for ")+14);
    		    			   FileWriter myWriterp;
    		    			   myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"searches", true);
    		    			   myWriterp.write(ccall + ": "+crest+"\n");
    		    			   myWriterp.close();
    			            	
    		    		   }
    		    		   if (sthings.contains(" downloaded ")) {
    		    			   String ccall = sthings.substring(0, sthings.indexOf(" downloaded "));
    		    			   String crest = sthings.substring(sthings.indexOf(" downloaded ")+12);
    		    			   FileWriter myWriterp;
    		    			   myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"downloads", true);
    		    			   myWriterp.write(ccall + ": "+crest+"\n");
    			               myWriterp.close();
    			            	
    		    		   }
    		    		   if (sthings.contains(" got the weather for ")) {
    		    			   String ccall = sthings.substring(0, sthings.indexOf(" got the weather for "));
    		    			   String crest = sthings.substring(sthings.indexOf(" got the weather for ")+21);
    		    			   FileWriter myWriterp;
    		    			   myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"global-logs"+File.separator+"weather", true);
    		    			   myWriterp.write(ccall + ": "+crest+"\n");
    			               myWriterp.close();
    			            	
    		    		   }
    		    		   ll = ll + 1;
    		    		   
    		    	   }
    		    	   System.out.println("l3");
    		    	   writing = false;
    		    	   req = req + 1;
    		    	   dataOutputStream.writeUTF(sgh.rwskey1);
   	                dataOutputStream.flush(); // send the message
   	                
         	} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } } 
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "cn":
        		// check notifications
        		try {
        			System.out.println("1");
        			String cnresult = "\n\n---\nNotifications:\n";
        			while(writing==true){Thread.sleep(0);}writing=true;
            		
        			String filecontentn = "";
        			
        			if (new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata", bodytext).exists() && (!bodytext.replaceAll(" ", "").isBlank())) {
        				try {
                			byte[] fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata"+File.separator+bodytext));
                			filecontentn = new String(fileContentgcn, StandardCharsets.UTF_8);
                			System.out.println("\n\n\n" + filecontentn + "\n\n\n");
                			} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }

        			}
        			        		
        			

    		       if (filecontentn.contains("chat-") || filecontentn.contains("forum-")) {
    		    	   String[] indnots = filecontentn.split("\n");
    		    	   System.out.println("2");
    		    	   for (String nots : indnots)
				        {
    		    		   nots = nots.replaceAll(" ", "").replaceAll("\n", "");
    		    		   if (nots.contains("chat-")) {
    		    			   cnresult = cnresult + "You have a new chat message from " + nots.replaceAll("chat-", "") + ".\n";
    		    		   }
    		    		   else {
    		    			   if (nots.contains("forum-")) {
    		    				   cnresult = cnresult + "There is a new message in the forum post " + nots.replaceAll("forum-", "") + ".\n";
    		    			   }
    		    		   }
				        }
    		    	   cnresult = cnresult + "---\n\n";
    		    	   
    		       }
    		       else {
    		    	   cnresult = "\n";
    		       }
    		       

    		       System.out.println("3");
    		       writing = false;
    		       req = req + 1;
    		        dataOutputStream.writeUTF(cnresult + sgh.rwskey1);
                    dataOutputStream.flush(); // send the message
                    dataOutputStream.close(); // close the output stream when we're done.
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		break;
        	case "cln":
        		// clear notification
        		try {
        			String ncall = "[error]";
        			ncall = bodytext.substring(0, bodytext.indexOf(sgh.rwskey2 + sgh.rwskey1));
        		    String nfile = bodytext.replaceAll(sgh.rwskey2 + sgh.rwskey1, "").replaceAll(ncall, "");
        			while(writing==true){Thread.sleep(0);}writing=true;
            		
        			String filecontentn = "";
        			if (new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata", ncall).exists() && ncall != "") {
        				try {
                			byte[] fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata"+File.separator+ncall));
                			filecontentn = new String(fileContentgcn, StandardCharsets.UTF_8);
                			} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }

        			}
        			        			
        			filecontentn = filecontentn.replaceAll(nfile, "").replaceAll("\n\n", "\n");
        			FileWriter myWriterp;
		            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"userdata"+File.separator + ncall, false);
	            	myWriterp.write(filecontentn);
	            	myWriterp.close();
    		       

    		       
    		       writing = false;
    		       req = req + 1;
    		        dataOutputStream.writeUTF(sgh.rwskey1);
                    dataOutputStream.flush(); // send the message
                    
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "an":
        		// add notification
        		try {
        			String ncall = "[error]";
        			ncall = bodytext.substring(0, bodytext.indexOf(sgh.rwskey2 + sgh.rwskey1));
        		    String nfile = bodytext.replaceAll(sgh.rwskey2 + sgh.rwskey1, "").replaceAll(ncall, "");
        			while(writing==true){Thread.sleep(0);}writing=true;
            		
        			String filecontentn = "";
        			if (new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata", ncall).exists() && ncall != "") {
        			try {
        			byte[] fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"repofolder"+File.separator+"userdata"+File.separator+ncall));
        			filecontentn = new String(fileContentgcn, StandardCharsets.UTF_8);
        			} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        			}
        			
        			filecontentn = filecontentn.replaceAll(nfile, "").replaceAll("\n\n", "\n");
        			FileWriter myWriterp;
        			myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"userdata"+File.separator + ncall, false);
	            	myWriterp.write(filecontentn);
	            	myWriterp.close();
        			
		            myWriterp = new FileWriter(sgh.git.getRepository().getDirectory().getParent() + File.separator+"userdata"+File.separator + ncall, true);
	            	myWriterp.write(nfile + "\n");
	            	myWriterp.close();
    		       

    		       
    		       writing = false;
    		       req = req + 1;
    		        dataOutputStream.writeUTF(sgh.rwskey1);
                    dataOutputStream.flush(); // send the message
                    
    	          } catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	case "k":
        		// get api keys or other sensitive stuff
        		while(writing==true){Thread.sleep(0);}writing=true;
        		if (new File(System.getProperty("user.home")+File.separator+"rwsdata", bodytext).exists() && bodytext != "") {
        			try {
        			byte[] fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+bodytext));
        			String filecontentn = new String(fileContentgcn, StandardCharsets.UTF_8).replaceAll("\\n|\\r", "");;
        			writing = false;
     		       req = req + 1;
     		        dataOutputStream.writeUTF(filecontentn + sgh.rwskey1);
                     dataOutputStream.flush(); // send the message
        			} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
        		}
        		dataOutputStream.close(); // close the output stream when we're done.
        		break;
        	default:
        		System.out.println("invalid option");
        		break;
        	}
        	
        }
        else {
        	System.out.println("unauthorized");
        }
		busy = false;
	}
}
class p1 implements Runnable {
	public void run() {
		sgh.threadindex = sgh.threadindex + 1;
		int tindex = sgh.threadindex;
		System.out.println("thread " + tindex + " started");
		long lcdms = 0;
		while (0==0) {
		try (ServerSocket ss = new ServerSocket()) {
			
		//ss.setPerformancePreferences(2, 1, 0);
	      InetSocketAddress socketAddress = new InetSocketAddress(5000+tindex);  
	      ss.bind(socketAddress); 
	      ss.setSoTimeout(6000);
		
		while (0==0) {
			try {
	        Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
	        
	        long stime = System.nanoTime();
	        socket.setSoTimeout(5000);
	        System.out.println("connection from " + socket);

	        // get the input stream from the connected socket
	        InputStream inputStream = socket.getInputStream();
	        // create a DataInputStream so we can read data from it.
	        DataInputStream dataInputStream = new DataInputStream(inputStream);
	        OutputStream outputStream = socket.getOutputStream();
	        // create a data output stream from the output stream so we can send data through it
	        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
	        // read the message from the socket
	        String message = dataInputStream.readUTF();
	        System.out.println("message: '" + message + "'");


        	sgh.pgh(message, dataInputStream, dataOutputStream);
	        System.out.println("connection finished");
        	dataInputStream.close(); // close the output stream when we're done.
	        dataOutputStream.close(); // close the output stream when we're done.
	        stime = System.nanoTime() - stime;
	        sgh.btime = sgh.btime + stime;
			} catch (Exception e) {
				if (!e.toString().contains("Accept timed out") && !e.toString().contains("Read timed out")) {
				e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e.toString();} } }
				}
			}
	        //ss.close();
	        //Thread.sleep(1);
			if (lcdms != sgh.lastdms) {
				sgh.dms[tindex-1] = true;
				lcdms = sgh.lastdms;
			}
		}
			
		} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
	}
		
	}
}

public class startbackend {
	public static void main(String[] args) throws IOException {
		byte[] fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"discordkey"));
		sgh.discordkey = new String(fileContentgcn, StandardCharsets.UTF_8).replaceAll("\\n|\\r", "");;
		fileContentgcn = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+File.separator+"rwsdata"+File.separator+"githubkey"));
		sgh.githubkey = new String(fileContentgcn, StandardCharsets.UTF_8).replaceAll("\\n|\\r", "");
	Thread t0 = new Thread(new sgh());
	t0.start();
	while (sgh.git == null) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	while (sgh.threadindex < 10) {
		Thread t1 = new Thread(new p1());
		t1.start();
		try {
			Thread.sleep((long) (100+Math.random()*100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	while (0==0) {
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
	    	while(sgh.writing==true || sgh.busy == true){Thread.sleep(0);}sgh.writing=true;sgh.busy=true;
    	} catch (Exception e) {e.printStackTrace(); if (e.toString().contains("EOFException")) {sgh.ee = sgh.ee + 1;} else { if (e.toString().contains("SocketException: Connection reset")) {sgh.cr = sgh.cr + 1;} else { if (e.toString().contains("UTFDataFormatException")) {sgh.utfe = sgh.utfe + 1;} else { if (e.toString().contains("TransportException")) {sgh.te = sgh.te + 1;} else { if (e.toString().contains("Broken pipe")) { sgh.bp = sgh.bp + 1; } else { {sgh.otherrs = sgh.otherrs + 1; sgh.vlogs = sgh.vlogs + "\n"+e;} } } } } } }
		
		
		sgh.ldms = 0;
		int ti = 0;
		while (ti < 10) {
			if (sgh.dms[ti] == true) {
				sgh.ldms = sgh.ldms + 1;
				sgh.dms[ti] = false;
			}
			ti = ti + 1;
		}
		System.out.println(sgh.ldms + " threads responded in the last minute");
		sgh.lastdms = System.currentTimeMillis();
		sgh.writing=false;sgh.busy=false;
	}
	
	}

}

