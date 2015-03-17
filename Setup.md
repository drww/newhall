# Downloading Newhall #

Newhall is available as either a zipped release file, or as a Subversion repository.  You can download the latest version by going to the [Newhall project page](http://code.google.com/p/newhall/), and looking under "Downloads" for the newest zipped release.

If you wish to download and work with the Newhall source code, simply flip over to the "[Source](http://code.google.com/p/newhall/source/checkout)" tab of the Newhall project site, and follow the instructions for checking out the source code via Subversion.


# Setting up Newhall #

If you downloaded the zip file, simply extract it anywhere on your hard drive.  Newhall is distributed as a JAR file, and you can launch it on most systems by double-clicking it.  Doing so will greet you with the main Newhall UI panel if everything is fine.  If your system does not seem to recognize the JAR file, you probably need to [install Java](Java_Installation.md).

If you're building and running Newhall from source, you will want to use Maven to build and launch it.  You should be able to use Maven's default build and run actions to launch Newhall normally.  If you want to produce a distributiable JAR file, see the `packageJar.*` files at the root of the repository.  They invoke the Maven assembler plugin.  The resulting JAR will be in your `target` directory, having a build name that includes `with-dependencies`.  This JAR file is suitable for sending to other people.