export ROOT=/Users/andrea/Documents/projects/JAME/project/bundles
export ECLIPSE=/Applications/eclipse-3.5

classespath=''
classespath=$classespath:$ROOT/main/net.sf.jame.core/bin
classespath=$classespath:$ROOT/main/net.sf.jame.core.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.launcher/bin
classespath=$classespath:$ROOT/main/net.sf.jame.launcher.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.media/bin
classespath=$classespath:$ROOT/main/net.sf.jame.media.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.runtime/bin
classespath=$classespath:$ROOT/main/net.sf.jame.runtime.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.service/bin
classespath=$classespath:$ROOT/main/net.sf.jame.service.encoders/bin
classespath=$classespath:$ROOT/main/net.sf.jame.service.network/bin
classespath=$classespath:$ROOT/main/net.sf.jame.service.jxta/bin
classespath=$classespath:$ROOT/main/net.sf.jame.twister/bin
classespath=$classespath:$ROOT/main/net.sf.jame.twister.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.mandelbrot/bin
classespath=$classespath:$ROOT/main/net.sf.jame.mandelbrot.swing/bin
classespath=$classespath:$ROOT/main/net.sf.jame.contextfree/bin
classespath=$classespath:$ROOT/main/net.sf.jame.contextfree.swing/bin
classespath=$classespath:$ECLIPSE/plugins/org.eclipse.equinox.registry_3.4.100.v20090520-1800.jar
classespath=$classespath:$ECLIPSE/plugins/org.eclipse.core.runtime_3.5.0.v20090525.jar
classespath=$classespath:$ECLIPSE/plugins/org.eclipse.equinox.app_1.2.0.v20090520-1800.jar
classespath=$classespath:$ECLIPSE/plugins/org.eclipse.equinox.common_3.5.0.v20090520-1800.jar
classespath=$classespath:$ECLIPSE/plugins/org.eclipse.osgi_3.5.0.v20090520.jar
classespath=$classespath:$ROOT/main/net.jxta/jxta.jar
classespath=$classespath:$ROOT/main/org.mozilla.rhino/js.jar
classespath=$classespath:$ROOT/main/org.jdesktop.swingworker/swing-worker-1.2.jar
classespath=$classespath:$ROOT/main/org.apache.log4j/log4j-1.2.15.jar
classespath=$classespath:$ROOT/main/net.sf.ffmpeg4java/ffmpeg4java.jar
classespath=$classespath:$ROOT/main/net.sf.freeimage4java/freeimage4java.jar

sourcespath=''
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.core/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.core.swing/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.launcher/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.launcher.swing/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.media/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.media.swing/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.runtime/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.runtime.swing/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.service/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.service.encoders/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.service.network/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.service.jxta/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.twister/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.twister.swing/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.mandelbrot/src
sourcespath=$sourcespath:$ROOT/main/net.sf.jame.mandelbrot.swing/src
#sourcespath=$sourcespath:$ROOT/main/net.sf.jame.contextfree/src
#sourcespath=$sourcespath:$ROOT/main/net.sf.jame.contextfree.swing/src

opts='-J-Xmx512M -author -version -use -d javadocs -subpackages net.sf.jame'

javadoc -classpath $classespath -sourcepath $sourcespath $opts 2>generate-javadocs.log
