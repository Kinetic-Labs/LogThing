package com.github.kinetic.logthing;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.StdUtil;

/**
 * Main class for starting the application
 */
public class Start {

    /**
     * Utilities
     */
    private static final LogUtil log = new LogUtil();
    private static final StdUtil stdUtil = new StdUtil();

    /**
     * Colors
     */
    private static final String MAGENTA = "\033[35m";
    private static final String RESET = "\033[0m";

    public static void main(String[] args) {
        Thread.currentThread().setName("B");

        final String asciiArt = getAsciiArt();
        System.out.println(asciiArt);

        log.debug("Booting...");

        LogThing.getInstance().launch(stdUtil.concat(new String[]{""}, args));
    }

    private static String getAsciiArt() {
        return MAGENTA + """
                           LogThing
                      ,--,           ,----,
                   ,---.'|         ,/   .`|
                   |   | :       ,`   .'  :
                   :   : |     ;    ;     /
                   |   ' :   .'___,/    ,'
                   ;   ; '   |    :     |
                   '   | |__ ;    |.';  ;
                   |   | :.'|`----'  |  |
                   '   :    ;    '   :  ;
                   |   |  ./     |   |  '
                   ;   : ;       '   :  |
                   |   ,/        ;   |.'
                   '---'         '---'
                      By Kinetic Labs
                """ + RESET;
    }
}
