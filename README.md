This tiny eclipse plug-in enables you to open windows explorer for any selected node from package explorer view.
MyEclipse has this feature but Eclipse doesn't, so I wrote this one.
You can open any node such as folder, file, JRE System Library, Referenced Libraries, etc.

**update in 2017** Add support for macOS.

![pic](http://static.oschina.net/uploads/space/2013/0831/194848_VEcq_113421.jpg)

## prerequisites
Eclipse3.6+ is required. I tested in Eclipse Helios, Indigo, Kepler, Neon.

```sh
JDK 1.5 +
Eclipse 3.6+ (CDT not supported)
```

## dependencies
All the dependencies can be found in MANIFEST.MF file.

## how to use it

1. Copy the output [cyper.openinexplorer_1.0.1.jar](target/cyper.openinexplorer_1.0.1.jar) to eclipse/dropins
2. Restart eclipse, and you will see it on the tool bar as well as menu bar.

Contact [me](http://my.oschina.net/uniquejava) if you need any help.

## todo
use cyper-utils as git submodules.