# LogThing

## Build
```shell
./gradlew jar copyDependencies
```


## Running
LogThing does *not* generate a fat jar, instead you will need to use a classpath.

for example (after running the build command):
```shell
cd env/
java -classpath "../build/libs/*:../build/libs/libraries/*" $(cat args.txt) com.github.kinetic.logthing.Start
```

##### To make java shut up about deprecation, pass:
```
-XX:+IgnoreUnrecognizedVMOptions --add-opens java.base/sun.misc=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/jdk.internal.misc=ALL-UNNAMED --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --sun-misc-unsafe-memory-access=allow
```


## Threads
### Names
- WMM = [Class: WebMonitorModule | Purpose: Powers the web dashboard](src/main/java/com/github/kinetic/logthing/module/impl/web/WebMonitorModule.java) (class)
- WD = [Package: Web | Purpose: Web dispatch, handle core web operations](src/main/java/com/github/kinetic/logthing/features/web) (package)
- B = [Class: Bootstrap | Purpose: handles launching LogThing](src/main/java/com/github/kinetic/logthing/Start.java) (class)
- MSH = [Class: LogThing | Purpose: Main Shutdown Hook](src/main/java/com/github/kinetic/logthing/LogThing.java) (class)
- LM = [Class: LogThing | Purpose: Main thread](src/main/java/com/github/kinetic/logthing/LogThing.java) (class)
- RLM = [Class: RequestLoggerModule | Purpose: Log network requests](src/main/java/com/github/kinetic/logthing/module/impl/misc/RequestLoggerModule.java) (class)
- LCM = [Class: LogConsumerModule | Purpose: Handle processed logs (e.g. save to database)](src/main/java/com/github/kinetic/logthing/module/impl/data/LogConsumerModule.java) (class)
- LSM = [Class: LogServiceModule | Purpose: Watch for the creation of new logs)](src/main/java/com/github/kinetic/logthing/module/impl/io/LogServiceModule.java) (class)
- WUW = [Class: WatchUtil | Purpose: Provides a high-level (WatchUtil Watcher) api to watch directories for logs)](src/main/java/com/github/kinetic/logthing/util/io/fs/WatchUtil.java) (class)
- FWD = [Class: WatchUtil | Purpose: File watcher daemon)](src/main/java/com/github/kinetic/logthing/util/io/fs/WatchUtil.java) (class)
- AM = [Class: AlertModule | Purpose: Alert manager daemon)](src/main/java/com/github/kinetic/logthing/module/impl/web/AlertModule.java) (class)
