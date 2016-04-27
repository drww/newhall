# Newhall - A Soil Moisture/Temperature Regime Simulation Model


## Downloads

Latest Version: 1.6.1 (September 21st, 2015)

Fixed an error with interpolation based on latitude.  This had the side-effect of lower model resolution in the southern hemisphere.

JAR: https://github.com/drww/newhall/blob/master/download/newhall-1.6.1.jar?raw=true

## Overview

Newhall is a Java implementation of Franklin Newhall's model for soil moisture regime determination. This implementation stems directly from the Wambeke BASIC version of the Newhall simulation model, which was a BASIC reimplementation of the original COBOL version. The model functions by taking temperature and precipitation data from a point location, interpreted as monthly averages, and simulates the behavior of the moisture within a column of soil. It functions off the concept of horizontal moisture recharge (through precipitation) and vertical discharge within the moisture profile (through evaporation).

Newhall is a desktop Java 5 application capable of running on all platforms supporting Java SE (Windows, Linux, MacOS X, Solaris, so forth).

The original sources that Newhall is derived from are public domain due to age and lack of original licensing. Newhall itself is released under the New BSD open-source license. This means you are free to download, share, and modify the program and its source code.

## Documentation / Wiki

Documentation for Newhall is stored in a github wiki.  To begin exploring, start with the landing page: 

https://github.com/drww/newhall/blob/wiki/Documentation.md

A manual is also available, in OpenDoc format:

https://github.com/drww/newhall/blob/master/misc/Newhall%20Manual.odt
