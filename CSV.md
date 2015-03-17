# Format Structure #

The classic CSV file format used by the BASIC version of Newhall is a simple file format
which contains the bare minimum in order to run the model. It consists only of a station name, a
country, degrees/minutes for a location, hemispheres, elevation, unit system, precipitation, temperature,
and year(s). Unit system is denoted as either a “E” or “M” for english and metric respectively.

The general structure follows the following pattern. White space is ignored by both the BASIC
version and the Java version.

```
Station Name, Country, Degrees Latitude, Minutes Latitude, N/S Hemisphere,
Degrees Longitude, Minutes Longitude, E/W Hemisphere, Elevation [NEWLINE]

Pre0, Pre1, Pre2, Pre3, Pre4, Pre5, Pre6, Pre7, Pre8, Pre9, Pre10, Pre11,
Tmp0, Tmp1, Tmp2, Tmp3, Tmp4, Tmp5, Tmp6, Tmp7, Tmp8, Tmp9, Tmp10, Tmp11,
Starting Year, Ending Year, Unit System [NEWLINE]
```

# Example #

Included in the binary distribution of Newhall should be several dozen example legacy CSV files.  Refer to them if you need more examples.

```
"Mead Agronomy Lab","USA",41,10,"N",96,25,"W",1180 [NEWLINE]
.8,.46,.06,.99,4.08,4.14,3.26,1.62,2.94,.85,.05,.66,32,15,37,55,63,69,76,
73,63,54,36,17,1989,1989,"E" [NEWLINE]
```

```
"Dickinson Expt Station ND","USA",46,53,"N",102,48,"W",2460 [NEWLINE]
.43 , .38 , .68 , 1.42 , 2.38 , 3.67 , 2.16 , 1.79 , 1.36 , .95 , .52 , .39
, 10.8 , 15 , 26.2 , 41.2 , 52.6 , 61.9 , 68.5 , 66.9 , 55.9 , 44.2 ,
28.3 , 16.5 ,1903,1996,"E" [NEWLINE]
```