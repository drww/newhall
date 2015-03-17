Java must be installed on any computers that wish to use this version of NSM. Attempting to
launch Newhall by double clicking on the included JAR file can quickly test if you have an appropriate
version of Java installed. If Newhall does not launch immediately, follow the following instructions
per your operating system.

# MacOS X #
Versions of MacOS X 10.1 and later should have at least Java 1.4 or later installed. Newhall
requires Java 1.5 or later. To verify your version of Java, open a terminal (located in your Utilities
folder inside the Applications folder), and type in `java -version` at the prompt. Because Apple is
responsible for the inclusion of the JVM in MacOS X, if your version is too old, you will need to check
for system updates.

Due to Oracle's acquisition of Sun Microsystems, they have changed the licensing terms of
outside vendor developed JVMs. At some point in the future (as of February 2011), Apple may no
longer be responsible for the inclusion of Java in OS X. Refer to modern documentation as to getting
Java installed on OS X if this occurs.

# Solaris #
Java is part of a standard install of Solaris. If Newhall will not run on your Solaris machine,
please contact your system administrator to setup a Java runtime for your use. Otherwise, refer to the
instructions for Linux.

# Linux (Ubuntu, Fedora, Debian, SUSE, etc.) #
Most Linux distributions offer Java (in the form of either the OpenJDK or official JVM), either
pre-installed or as an entry in their package manager of choice. Refer to your distribution's
documentation on how to install software via their package manager, and search for a “JRE” package.
Install that, and Java will be available to you. If the JAR will not open with a double-click following
installation, you can run the JAR file from the command line. Use the command `java -jar Newhall.jar` to launch Newhall.

For specific instructions per your distribution, a simple search of “(distro name here) java
installation” will generally yield up-to-date instructions for how to accomplish the task. More and
more distributions are coming with the OpenJDK preinstalled, which is suitable for running Newhall.

# Windows (2000, XP, Vista, 7) #
Java is not included by default with Windows. If the JAR does not execute with a double-click,
you will need to install Java. Browse to http://www.java.com/ and follow the instructions to install
Java on your computer. When Java is properly installed, the icon for Newhall's JAR file should
change, and should now launch with a double-click. You will need to have administrator access to your
computer to do this, and if your workstation is managed by your employer, you will need to contact
your IT administrators to assist you in installing Java.
You can verify your version of Java by opening a terminal (Start > Run > cmd.exe or just hit
Windows-R and type cmd.exe) and typing `java -version`.