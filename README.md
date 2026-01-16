Thank you for wanting to set up your own RWS server!  

If you have any problems, email me at jpradiophone@gmail.com or ask in our Discord server: https://discord.gg/mjzadeyNUs  

If you're on Linux and you're having trouble getting VARA to run, contact me using the above links, I can help.  

  

Instructions:


First, install the latest Java Development Kit (not the Java Runtime Environment). https://www.oracle.com/java/technologies/downloads/#java24


Next, install the VARA modem. If you want to put the server on 14.109 (recommended), choose the VARA HF version. https://rosmodem.wordpress.com


Now, download the latest "rws.jar" from https://github.com/Glitch31415/rws/releases.


To run the server, first start the VARA modem (VARA.exe), and make sure your settings are as follows:


Command Port: 8300

Data Port: 8301

Accept 500 Hz Connections should be checked

Tuner Enhancement should be unchecked

CW ID should be checked if you are in the US (and is good practice in general)


Unused at the moment but might be in the future:

KISS Port: 8100

KISS Interface should be checked

Ignore KISS DCD should be unchecked


I would recommend setting Retries to 3, at most. It causes less interference to other stations answering CQs and for the server to be used effectively the link should be strong enough to not need more than 3 retries.


Finally, open cmd.exe and type in "java -jar [path to jar]" replacing [path to jar] with the full path of the rws.jar file (example: C:\Users\glitch\Downloads\rws.jar).

Fill in the extra data as it asks and your server will be active! Set your radio frequency to 14.109 MHz USB, and PTT to VOX. I am starting to offer rudimentary flrig support, if you want to try using that for PTT and freqency control. Expect bugs, let me know if you need help.


*** For client use: if you want an external helper for dealing with the downloads and base64, KC3VPB has created a helper that can decode base64 automatically and save it to a file. https://github.com/Caleb-J773/rws-tools-release/releases ***


