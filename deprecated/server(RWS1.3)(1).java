/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.xmlrpc.XmlRpcException
 *  org.apache.xmlrpc.client.XmlRpcClient
 *  org.apache.xmlrpc.client.XmlRpcClientConfig
 *  org.apache.xmlrpc.client.XmlRpcClientConfigImpl
 */
package rws;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import com.aspose.words.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

public class server {
    public static String thing = "hi";
    public static long tim = System.currentTimeMillis();
    public static byte[] rx = new byte[0];
    public static String rxstr = "";
    public static Integer connect = 0;
    public static long timeout = 0L;
    public static Integer warn = 0;
    public static Integer option = 0;
    public static String urlthing = "";
    public static Integer connections = 0;
    public static String toturls = "";
    public static String callsign = "";
    public static String content = null;
    public static String callsigns = "";
    public static long txtimer = 0L;
    public static Integer prevmtx = 0;
    public static Integer att = 0;

    public static void main(String[] args) throws Exception {
        Scanner callinp = new Scanner(System.in);
        System.out.println("Enter callsign:");
        callsign = callinp.nextLine();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:7362/RPC2"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig((XmlRpcClientConfig)config);
        client.execute("text.clear_rx", new Object[0]);
        callinp.close();
        while (true) {
            Scanner scanner;
            System.out.println("connected: " + connect);
            System.out.println("option selected: " + option);
            System.out.println("total connections: " + connections);
            System.out.println("inputs: " + toturls);
            System.out.println("callsigns: " + callsigns);
            System.out.println();
            if (connect == 1 && timeout < System.currentTimeMillis()) {
                if (warn == 1) {
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        
                    }
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nSorry, but you didn't respond fast enough. Your connection has been terminated. But, you can reconnect by saying '<<<" + callsign + " de [callsign]>>>'." + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                    timeout = 0L;
                    warn = 0;
                    connect = 0;
                } else {
                	while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                		Thread.sleep(1000L);
                		
                	}
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nAre you still there? Please respond or your connection will be terminated.\n\nde " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                    timeout = System.currentTimeMillis() + 120000L;
                    warn = 1;
                }
            }
            if (tim + 300000L < System.currentTimeMillis() && connect == 1) {
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                client.execute("text.add_tx", new Object[]{"\n"});
                client.execute("text.add_tx", new Object[]{"\nThis is " + callsign + "'s RWS (Radio Web Services) server. You can connect by sending '<<<" + callsign + " de [callsign]>>>'." + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                client.execute("main.tx", new Object[]{""});
                att = 0;
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    rxstr = " ";
                    att = att + 1;
                    if (att > 600) {
                    	att = 0;
                    	client.execute("main.rx", new Object[]{""});
                    }
                }
                Thread.sleep(1000L);
                client.execute("text.clear_rx", new Object[0]);
                tim = System.currentTimeMillis();
            }
            if ((int)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<" + callsign + " de") && connect == 0) {
                Thread.sleep(15000L);
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
                int i = 2;
                while (rxstr.indexOf("<<<" + callsign + " de") + i + 11 < rxstr.length()) {
                    if (i < 8) {
                        callsigns = String.valueOf(callsigns) + rxstr.charAt(i + rxstr.indexOf("<<<" + callsign + " de") + 11);
                    }
                    ++i;
                }
                callsigns = String.valueOf(callsigns) + ", ";
                if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                    rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                    rxstr = new String(rx, StandardCharsets.UTF_8);
                    rxstr = rxstr.replaceAll("\\r|\\n", "");
                }
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                   Thread.sleep(1000L);
                   
                }
                connections = connections + 1;
                client.execute("text.add_tx", new Object[]{"\n"});
                client.execute("text.add_tx", new Object[]{"\nHello, welcome to " + callsign + "'s RWS (Radio Web Services) server. Please send '<<<web>>>' to get raw HTML or text from a given website, '<<<search>>>' to do a quick search, '<<<weather>>>' to look up the weather for a given city, '<<<status>>>' to print the status of the server, or '<<<exit>>>' to disconnect." + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                client.execute("main.tx", new Object[]{""});
                att = 0;
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    rxstr = " ";
                    att = att + 1;
                    if (att > 600) {
                    	att = 0;
                    	client.execute("main.rx", new Object[]{""});
                    }
                }
                Thread.sleep(1000L);
                client.execute("text.clear_rx", new Object[0]);
                client.execute("text.clear_rx", new Object[0]);
                connect = 1;
                warn = 0;
                option = 0;
                timeout = System.currentTimeMillis() + 180000L;
                
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<web>>>") && connect == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                option = 1;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                client.execute("text.add_tx", new Object[]{"\n"});
                client.execute("text.add_tx", new Object[]{"\nPlease enter the URL you want to get the raw HTML from. Start it with '<<<' and end it with '>>>'. For example, '<<<https://www.google.com>>>'. If you want a text-only website, please include a 't' after the '>>>'.\n\nde " + callsign + '\n' + '\n' + "^r"});
                client.execute("main.tx", new Object[]{""});
                att = 0;
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    rxstr = " ";
                    timeout = System.currentTimeMillis() + 180000L;
                    att = att + 1;

                    if (att > 600) {
                    	att = 0;
                    	client.execute("main.rx", new Object[]{""});
                    }
                }
                Thread.sleep(1000L);
                client.execute("text.clear_rx", new Object[0]);
                tim = System.currentTimeMillis();
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<search>>>") && connect == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                option = 2;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.clear_rx", new Object[0]);
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nPlease enter a search query starting with '<<<' and ending with '>>>'.\n\nde " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    rxstr = " ";
                    tim = System.currentTimeMillis();
                }
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<weather>>>") && connect == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                option = 3;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.clear_rx", new Object[0]);
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nPlease enter the city you want to get the weather forecast of." + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                }
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<exit>>>") && connect == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.clear_rx", new Object[0]);
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nThanks for using " + callsign + "'s RWS server. You can reconnect by saying '<<<" + callsign + " de [callsign]>>>'." + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                    timeout = 0L;
                    warn = 0;
                    connect = 0;
                }
            }
            if (rxstr.contains("<<<status>>>") && connect == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.clear_rx", new Object[0]);
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nHere is the status of the server:\n" + "This info was last updated: " + java.time.LocalDate.now() + " " + java.time.LocalTime.now() + "\n" + "\n" + "connected: " + connect + "\n" + "option selected: " + option + "\n" + "total connections: " + connections + "\n" + "inputs: " + toturls + "\n" + "callsigns: " + callsigns + "\n" + "\n" + "The server is on " + ((double)client.execute("main.get_frequency", new Object[]{""})/1000000) + " MHz " + client.execute("rig.get_mode", new Object[]{""}) + " (" + client.execute("modem.get_carrier", new Object[]{""}) + " hz audio frequency) " + "using " + client.execute("modem.get_name", new Object[]{""}) + "." + "\n" + "\n" + "Source code is at https://github.com/Glitch31415/rws, my github repository" + '\n' + '\n' + "de " + callsign + '\n' + '\n' + "^r"});
                    client.execute("main.tx", new Object[]{""});
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                }
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<") && rxstr.contains(">>>") && connect == 1 && option == 1) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                Thread.sleep(15000L);
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.add_tx", new Object[]{"\n"});
                    if ((rxstr.length() > rxstr.indexOf(">>>") + 3)) {
                        if (rxstr.charAt(rxstr.indexOf(">>>") + 3) == 't') {
                            client.execute("text.add_tx", new Object[]{"\nHere is the text from the website you provided.\n\n-----\n"});
                        } else {
                            client.execute("text.add_tx", new Object[]{"\nHere is the raw HTML from the website you provided. Please copy everything between the '-----' and paste it into Notepad or something similar. Then, save it as a .html file, and open it in your browser.\n\n-----\n"});
                        }
                    } else {
                        client.execute("text.add_tx", new Object[]{"\nHere is the raw HTML from the website you provided. Please copy everything between the '-----' and paste it into Notepad or something similar. Then, save it as a .html file, and open it in your browser.\n\n-----\n"});
                    }
                    if (0==1) {
                        content = "Oops, I think you typed that wrong. Please try again, but make sure to include the '*htt p' and the '* *' with no spaces.";
                    } else {
                        urlthing = rxstr.substring(rxstr.indexOf("<<<") + 3, rxstr.indexOf(">>>"));
                        toturls = String.valueOf(toturls) + urlthing + ", ";
                        if (rxstr.length() > rxstr.indexOf("<<<") + 3 && rxstr.charAt(rxstr.indexOf(">>>") + 3) == 't') {
                            urlthing = "https://www.w3.org/services/html2txt?url=" + urlthing;
                        }
                        URLConnection connection = null;
                        try {
                            connection = new URL(urlthing).openConnection();
                            scanner = new Scanner(connection.getInputStream());
                            scanner.useDelimiter("\\Z");
                            content = scanner.next();
                            scanner.close();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            content = ex.toString();
                        }
                    }
                    if (content.contains("porn") || content.contains(" sex ") || content.contains("fuck") || content.contains("shit") || content.contains("bitch") || content.contains(" ass ") || content.contains("pussy")) {
                        client.execute("text.add_tx", new Object[]{"Oops. Looks like your website contained some inappropriate stuff which is illegal to transmit on ham radio."});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    } else {
                        client.execute("text.add_tx", new Object[]{content});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    }
                    txtimer = System.currentTimeMillis();
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                }
            }
            if (rxstr.contains("<<<") && rxstr.contains(">>>") && connect == 1 && option == 3) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                Thread.sleep(15000L);
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
                System.out.println(rxstr);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nHere is the weather forecast for the city you provided.\n\n-----\n"});
                    if (0==1) {
                        content = "Oops, I think you typed that wrong. Please try again, but make sure to include the '*htt p' and the '* *' with no spaces.";
                    } else {
                        urlthing = rxstr.substring(rxstr.indexOf("<<<") + 3, rxstr.indexOf(">>>"));
                        toturls = String.valueOf(toturls) + urlthing + ", ";
                        urlthing = "https://www.w3.org/services/html2txt?url=https://wttr.in/" + urlthing;
                        URLConnection connection = null;
                        try {
                            connection = new URL(urlthing).openConnection();
                            scanner = new Scanner(connection.getInputStream());
                            scanner.useDelimiter("\\Z");
                            content = scanner.next();
                            scanner.close();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            content = ex.toString();
                        }
                    }
                    content = content.replaceAll("[^a-zA-Z0-9'\n'' '()|]", "");
                    String regex = "\s+";
                    //Compiling the regular expression
                    Pattern pattern = Pattern.compile(regex);
                    //Retrieving the matcher object
                    Matcher matcher = pattern.matcher(content);
                    //Replacing all space characters with single space
                    content = matcher.replaceAll(" ");
                    if (content.contains("porn") || content.contains(" sex ") || content.contains("fuck") || content.contains("shit") || content.contains("bitch") || content.contains(" ass ") || content.contains("pussy")) {
                        client.execute("text.add_tx", new Object[]{"Oops. Looks like your website contained some inappropriate stuff which is illegal to transmit on ham radio."});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    } else {
                        client.execute("text.add_tx", new Object[]{content});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    }
                    txtimer = System.currentTimeMillis();
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    tim = System.currentTimeMillis();
                }
            }
            if ((Integer)client.execute("text.get_rx_length", new Object[]{""}) != 0) {
                rx = (byte[])client.execute("text.get_rx", new Object[]{0, (int)(client.execute("text.get_rx_length", new Object[]{""}))});
                rxstr = new String(rx, StandardCharsets.UTF_8);
                rxstr = rxstr.replaceAll("\\r|\\n", "");
            }
            if (rxstr.contains("<<<") && rxstr.contains(">>>") && connect == 1 && option == 2) {
                warn = 0;
                timeout = System.currentTimeMillis() + 180000L;
                Thread.sleep(15000L);
                while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                    Thread.sleep(1000L);
                    
                }
                if (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == true) {
                    client.execute("text.add_tx", new Object[]{"\n"});
                    client.execute("text.add_tx", new Object[]{"\nHere are the results of your query.\n\n-----\n"});
                    if (0==1) {
                        content = "Oops, I think you typed that wrong. Please try again, but make sure to include the 's *' and the '* f' with no spaces.";
                    } else {
                        if (0==1) {
                            rxstr = new StringBuilder(rxstr).insert(rxstr.indexOf("s*") + 2, " ").toString();
                            Thread.sleep(250L);
                        }
                        urlthing = rxstr.substring(rxstr.indexOf("<<<") + 3, rxstr.indexOf(">>>"));
                        toturls = String.valueOf(toturls) + urlthing + ", ";
                        urlthing = urlthing.replaceAll(" ", "+");
                        urlthing = "https://www.w3.org/services/html2txt?url=https://www.google.com/search?q=" + URLEncoder.encode(urlthing, StandardCharsets.UTF_8);
                        URLConnection connection = null;
                        try {
                            connection = new URL(urlthing).openConnection();
                            scanner = new Scanner(connection.getInputStream());
                            scanner.useDelimiter("\\Z");
                            content = scanner.next();
                            scanner.close();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            content = ex.toString();
                        }
                    }
                    if (content.contains("porn") || content.contains(" sex ") || content.contains("fuck") || content.contains("shit") || content.contains("bitch") || content.contains(" ass ") || content.contains("pussy")) {
                        client.execute("text.add_tx", new Object[]{"Oops. Looks like your search results contained some inappropriate stuff which is illegal to transmit on ham radio."});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    } else {
                        client.execute("text.add_tx", new Object[]{content});
                        client.execute("text.add_tx", new Object[]{"\n-----\n\nde " + callsign + '\n' + '\n' + "^r"});
                        client.execute("main.tx", new Object[]{""});
                    }
                    att = 0;
                    while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
                        Thread.sleep(1000L);
                        client.execute("text.clear_rx", new Object[0]);
                        rxstr = " ";
                        timeout = System.currentTimeMillis() + 180000L;
                        att = att + 1;
                        if (att > 600) {
                        	att = 0;
                        	client.execute("main.rx", new Object[]{""});
                        }
                    }
                    Thread.sleep(1000L);
                    client.execute("text.clear_rx", new Object[0]);
                    rxstr = "";
                    tim = System.currentTimeMillis();
                }
            }
            txtimer = System.currentTimeMillis();
            //while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false) {
            //    Thread.sleep(1000L);while (client.execute("main.get_trx_status", new Object[]{""}).equals("rx") == false)
            //    client.execute("text.clear_rx", new Object[0]);
            //    rxstr = "";
            //    prevmtx = 1;
            //    if (System.currentTimeMillis() <= txtimer + 600000L) continue;
            //    client.execute("main.rx", new Object[]{""});
            //}
            //if (prevmtx == 1) {
            //	Thread.sleep(1000L);
            //    client.execute("text.clear_rx", new Object[0]);
            //    prevmtx=0;
            //}
            Thread.sleep(1000L);
            
            Document doc = new Document("/home/glitch/testrwsdoc");
            DocumentBuilder builder = new DocumentBuilder(doc);

            // Insert text at the beginning of the document.
            builder.moveToDocumentStart();
            builder.insertHtml("<p style=" + '"' + "font-family: Fira Code" + '"' + ">" + "<br>" + "Last updated: " + java.time.LocalDate.now() + " " + java.time.LocalTime.now() + "<br>" + "<br>" + "connected: " + connect + "<br>" + "option selected: " + option + "<br>" + "total connections: " + connections + "<br>" + "inputs: " + toturls + "<br>" + "callsigns: " + callsigns + "<br>" + "<br>" + "The server is on " + ((double)client.execute("main.get_frequency", new Object[]{""})/1000000) + " MHz " + client.execute("rig.get_mode", new Object[]{""}) + " (" + client.execute("modem.get_carrier", new Object[]{""}) + " hz audio frequency) " + "using " + client.execute("modem.get_name", new Object[]{""}) + "." + "<br>" + "<br>" + "Source code is at " + "<a href=\"https://github.com/Glitch31415/rws\">my github repository</a>"+ "</p>");

            doc.save("/var/www/html/index.html");
        }
    }
}

