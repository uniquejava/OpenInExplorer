This tiny eclipse plug-in enables you to open windows explorer for any selected node from package explorer view.
MyEclipse has this feature but Eclipse doesn't, so I wrote this one.
You can open node java such as, package, file, referenced libraries, etc.

![pic](http://static.oschina.net/uploads/space/2013/0831/194848_VEcq_113421.jpg)

##prerequisites
Eclipse3.6+ is required. I only tested in Eclipse Helios, Indigo, Kepler.
```shell
JDK 1.5 +
Eclipse 3.6+ (CDT not supported)
```

##dependencies
There are for dependencies for this plug in. You can find them in MANIFEST.MF file.
```shell
Require-Bundle: org.eclipse.ui,
 org.eclipse.core.runtime,
 org.eclipse.core.resources;bundle-version="3.6.1",
 org.eclipse.jdt.core;bundle-version="3.6.2"
```

##how to use it
1. Copy the output "cyper.openinexplorer_1.0.0.beta.jar" to eclipse/dropins
2. Restart eclipse, and you will see it on the tool bar as well as menu bar.

Contact [me](http://my.oschina.net/uniquejava) if you need any help.