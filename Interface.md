![http://newhall.googlecode.com/files/interface.png](http://newhall.googlecode.com/files/interface.png)

Above is an example of the Newhall interface with a dataset loaded. The loading was
accomplished by selecting “Open Dataset” (Control-O) from the “File” menu, and selecting either an
XML or CSV file adhering to either the original BASIC NSM file format, or the newer XML file
format. The model runs as soon as the dataset is open. Newhall starts up with no dataset loaded.

The main interface consists of four tabs. “Input” covers the input data to the model. This
includes temperature and precipitation data, along with some of the most basic metadata. From here
you can specify the waterholding capacity (lower left) along with the parameters for the soil-air
relationship with the offset and amplitude (these augment the sine wave, shifting the waveform forward
and back, and also diminishing the amplitude). Altering any of these fields will cause the model to
rerun instantaneously.

“Calendars” covers the temperature and moisture calendar for the location. The temperature
calendar is static and directly related to the monthly temperature values. The moisture calendar is
altered by both the dataset and the waterholding capacity. These calendars provide general overviews
of the dataset's location given the model inputs.

“Results” shows the conclusions of the model run. This includes evapotranspiration
calculations, extended statistics about the location (generally these are summaries of what the calendars
have laid out), annual rainfall, and regime determinizations.

“Metadata” includes any extended metadata included in the dataset. For legacy datasets, only
the most basic fields are populated, due to the sparse nature of the dataset. XML datasets may have a
full compliment of fields in the metadata. Refer to the XML file format documentation later in this
document to add and change these values.

The menu bars consist of three menus: “File”, “Options”, and “Help”. “File” contains actions
to open datasets, and to exit the application. “Options” includes the action to toggle the unitsystem
mode. Newhall starts up in metric mode by default, and can toggle back and forth between metric and
english seamlessly. The active unit system is displayed at the bottom-center of the window. The
“Help” menu includes the option to view the current version and license information for your current
version of the Newhall application.

Two buttons are available at the bottom-right of the window: “Export to XML” and “Export to
FLX”. These two buttons export the results of the model to two different file formats. The FLX file
format is the legacy output of the BASIC version of the model, denoted by the `.FLX` file suffix. The
XML export is a new format meant to accommodate metadata and extended input and output data in a
file format that is both machine-readable and human-readable. The FLX file format does not include
the input data used to make it, but the XML file will, as it retains the `<INPUT>` tag.