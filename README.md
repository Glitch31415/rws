# RWS (Radio Web Services)

Allows accessing webpages and utilizing other internet abilities over amateur radio

If you have any questions, email me at jpradiophone@gmail.com or ask in our Discord server: https://discord.gg/mjzadeyNUs

### Features

**|website : Fetches text or raw html from a given URL**

**|search : Quick text-only search using DuckDuckGo**

**|weather : Get weather forecast for any location**

**|download : Download a file at a given URL, encoded and sent through base64**

**|forum : View or create threads in the forum hosted on a private GitHub repo**

**|chat : Send and receive direct messages with a callsign in real time, with messages also saved on the private GitHub repo**

### Compatible modems

**VARA (recommended)**: VARA HF (500 and 2300 Hz) and VARA FM

FLDigi: Any digital mode that supports full ASCII text

~~FreeDATA~~ (not yet implemented, coming soon)

*For developers:*

Serial: Any port and baud rate

TCP: Any port

## Connecting to a server

The main calling frequency for the project is 14.109 MHz USB. Look at the active servers list in the status channel on the discord to see what frequency each server is on and what modem it's using.

If you want an external tool for dealing with the downloads and base64, KC3VPB has created a helper that can decode base64 automatically and save it to a file. https://github.com/Caleb-J773/rws-tools-release/releases

### Connecting to a VARA server (recommended)

Any VARA chat clients will work for talking to the server. Some examples:

VarAC (https://www.varac-hamradio.com/)

VARA Chat (https://downloads.winlink.org/VARA%20Products/)

Follow the instructions on those sites to get the chat client and the VARA modem.

Since VARA is the recommended modem and 14.109 is the main calling frequency, you can just call CQ on that frequency and wait for a server to respond back. However, if no servers are able to reach you in that way, pick a VARA server from the activeservers list (linked above) , go to its listed frequency (on the list, the frequency is the number to the right of its callsign, in MHz), and send a connection request to the callsign directly.

After you're connected, the server will automatically reply back with a list of commands, and you're good to go.

### Connecting to a FLDigi server

Server hosters are supposed to include the mode they're using alongside their frequency in the activeservers list, so if they do, go onto that frequency and mode.

All data sent to the server needs to be between two equal signs in order to be recognized. Send a test message to get the attention of the server. Example: "==hi==" (the contents of this message doesn't matter)

If the server is hearing you correctly, it will respond back and ask for your callsign. Respond back using the format above. Example: "==KJ7QQG=="

You are now fully connected to the server, and it will respond back with a list of commands. You can go from there, remembering to put every message between double equal signs.

Some things to be aware of:

Your connection will time out after 5 minutes of inactivity (or 15 minutes if you're waiting for a chat message). If it times out, you will need to reconnect from the beginning.

All newlines are removed from the messages you send, as a workaround due to how FLDigi reads text.

FLDigi's digital modes don't provide 100% guaranteed integrity of transferred text (unlike VARA), so file transfers, HTML, etc is *very very* likely to get corrupted.

## Hosting a server

Thank you for wanting to set up your own RWS server! This project relies on many servers being distributed around the globe for maximum coverage. The calling frequency for the project is 14.109 MHz USB, and you should set up your server there if you can.

If your radio doesn't have VOX, there is experimental FLRig support. Contact me if you need help getting that working with RWS.

These instructions will assume you're on Windows, but the server is completely usable on Linux (including the VARA modem). You can also contact me if you want help getting that working too.

### 1. Installing and testing Java

First, install the latest Java Runtime Environment (JRE). The server will work on any platform you can get Java running on. https://www.java.com/en/download/manual.jsp

Next, download the latest rws.jar. https://github.com/Glitch31415/rws/releases

Try running the server by opening Command Prompt and typing `java -jar .\rws.jar`. If it starts asking for you to enter information, good, just close the program for now. If the command errors, contact me for help.

### 2. Choosing a modem

It's recommended that you use the VARA modem. It's best for performance and ease of use.

#### 2a. VARA

First, get the VARA modem from https://downloads.winlink.org/VARA%20Products/. Both VARA HF and VARA FM work. If you're going to host on 14.109, use VARA HF.

Start the modem and get the audio configured correctly. In the VARA Setup menu, make sure the settings are as follows:

Accept 500 Hz Connections is checked

Tuner enhancement is UNchecked

KISS Interface is checked

Retries is 3

Make note of the Command, Data, and KISS ports, as you will enter them into RWS now. Go to step 3.

#### 2b. FLDigi

Install fldigi however you prefer. https://sourceforge.net/projects/fldigi/

RWS will try to talk to FLDigi using port 7362, so make sure that port isn't already taken up by something (unlikely, but mentioning it just in case).

Make sure AFC and squelch are turned off on whatever mode you choose to use.

### 3. Getting everything started and configured

Make sure your modem of choice is started before you start RWS.

Start RWS using `java -jar .\rws.jar`.

It will ask you to start inputting information. Here's what to enter:

*VARA command port*: If you're using VARA, set this to the Command port listed in the VARA Setup menu.

*VARA data port*: If you're using VARA, set this to the Data port listed in the VARA Setup menu.

*VARA KISS port*: If you're using VARA, set this to the KISS port listed in the VARA Setup menu.

*Debug mode*: I recommend turning this on.

*Callsign*: Your callsign. Don't add any suffixes. For example, "KJ7QQG-9" should just be "KJ7QQG".

*Server welcome message*: Banner message that's displayed when a person connects to your server and when they use |home.

*Server frequency*: Set this to the frequency you are putting your server on. Include any other information for how to connect to the server; for example, if you're using FLDigi, set the frequency to something like "14.109 PSK125 2000 Hz", showing the radio's frequency, the mode, and the audio frequency.

*Server location*: Your gridsquare. Does not need all 6 characters, just the 4 character will be fine. Examples: "CN84" or "CN84OR"

*Open VARA automatically*: Enable if you're using VARA and you want RWS to try to launch VARA for you when it starts, otherwise leave it disabled.

*Connect to FLRig*: Leave disabled unless you need it. Ask me for help with these settings if you do need it.

*FLRig automatic frequency control*: Only applies if you're using FLRig.

*Standby frequency*: Same as above.

*Traffic frequency*: Same as above.

*500 Hz connections*: Only applies if you're using VARA. Enable if you're in the US and you're going to leave your server unattended.

*Modem*: Say 1 if you're using VARA, 3 if you're using FLDigi.

*Serial port*: Leave as default.

*Serial baud*: Leave as default.

*TCP port*: Leave as default.

*Save settings automatically*: This will write the above configuration to [home]\rwsdata\rws.conf. I recommend doing this.

The server should now start. You can interact with it from the terminal just like clients would from the radio side; just remember to disconnect from the server by typing |disc after messing around from the terminal so you allow outside connections again.

If you run into any errors, I'm always willing to help.
