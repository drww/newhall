# About #

The Newhall XML2CSV tool is a small tool meant to be used in conjunction with Newhall XML formatted
files. The tool will take in a directory that contains Newhall XML files that have been run through the model (XML files not run will not be processed), and compile a summary report in a CSV file format.

This tool is included in the "Tools" folder with Newhall, and can be started by double clicking the JAR file.

# Usage #

![http://newhall.googlecode.com/files/xml2csv_screenshot_1.1.1.png](http://newhall.googlecode.com/files/xml2csv_screenshot_1.1.1.png)

To run a summary report, you need to begin by selecting a directory that is filled with
Newhall XML files. You can do this by pressing the first "..." button below where it asks you to
specify such a directory. This will bring up a directory selection dialog. From here you can select
any directory on your local filesystems. Once you select a directory, its absolute file path will show
up next to the first "..." button.

Next, you must specify where the CSV report needs to go. You can do this by clicking the
second "..." button, below where it asks you to specify such a path. You can either navigate to a
directory where you want a new CSV file to go to, or you can select an existing file to completely
overwrite with new result, which the tool will warn you of. Once you have selected a path for the
CSV file to go, its absolute path will show up next to the second "..." button.

The final step is to click the "Summarize to CSV" button. The tool will now take each XML
file in that directory, and attempt to read it. Make sure the file suffix of the XML files is ".xml",
although it does not need to be case-sensitive (".XmL" is valid, for example). Should any of the
files not fit the correct format (Eg, not a Newhall XML file, or XML file hasn't been processed yet),
it will be skipped over.

Any errors that are encountered by the tool are displayed at the lower left of the tool's
window. Such errors include warnings about invalid folder paths (Eg, you may have specified a
folder and then deleted it), and similar problems with the CSV save location (Eg, on a flash drive,
but then you removed the flash drive).

While running a batch, the window will update the status display as to its progress
processing the XML files. The current number file it is on, plus an overall percentage of progress
will be presented, and then cleared upon completion.

This status display will then tell you the results of processing your directory full of XML
files. A successful run will report that it has processed a number of valid XML files, out of a total
number of XML files located. XML files that could not be successfully processed are merely
skipped over, and do not disrupt the rest of the results.

The resulting CSV file can then be opened in most spreadsheet programs, or parsed by other
programs and scripts for further processing.