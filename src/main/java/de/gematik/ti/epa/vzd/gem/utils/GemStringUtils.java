/*
 * Copyright (c) 2022 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gem.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GemStringUtils {

  private static final String GOTHINC = "____   ______________________            _________ .__  .__               __   \n\\   \\ /   /\\____    /\\______ \\           \\_   ___ \\|  | |__| ____   _____/  |_ \n \\   Y   /   /     /  |    |  \\   ______ /    \\  \\/|  | |  |/ __ \\ /    \\   __\\ \n  \\     /   /     /_  |    `   \\ /_____/ \\     \\___|  |_|  \\  ___/|   |  \\  |\n   \\___/   /_______ \\/_______  /          \\______  /____/__|\\___  >___|  /__|\n                   \\/        \\/                  \\/             \\/     \\/      \n";
  private static final String GOOFY = "    __    __      __     ___________    __     _____      __        __      ___    __        __\n|  |  |  | (___   ) |    \\         /  __) \\   |    (_    _) \\    ___) |    \\  |  | (__    __) \n|  |  |  |    /  /  |     |  ___  |  /     |  |      |  |    |  (__    |  |  \\    |    |  |    \n|  |  |  |   /  /   |     | (___) | |      |  |      |  |    |   __)   |  | \\ \\|  |    |  |    \n \\  \\/  /   /  /__  |     |       |  \\__   |  |__   _|  |_   |  (___   |  |  \\    |    |  |    \n__\\    /___(      )_|    /_________\\    )_/      )_(      )_/       )_ |  |___\\   |____|  |____\n";
  private static final String FUZZY = ".-..-..----..---.         .--. .-.   _              .-. \n: :: :`--. :: .  :       : .--': :  :_;            .' `.\n: :: :  ,',': :: : _____ : :   : :  .-. .--. ,-.,-.`. .'\n: `' ;.'.'_ : :; ::_____:: :__ : :_ : :' '_.': ,. : : : \n `.,' :____;:___.'       `.__.'`.__;:_;`.__.':_;:_; :_;\n";
  private static final String FOURTOPS = "|    |~~/|~~\\     /~~|'        | \n \\  /  / |   |---|   ||/~/|/~\\~|~\n  \\/  /__|__/     \\__||\\/_|   || \n";
  private static final String FENDER = "\\\\      // |'''''/ '||'''|.     .|'''', '||`                        ||    \n \\\\    //      //   ||   ||     ||       ||   ''                    ||    \n  \\\\  //      //    ||   || --- ||       ||   ||  .|''|, `||''|,  ''||''  \n   \\\\//      //     ||   ||     ||       ||   ||  ||..||  ||  ||    ||    \n    \\/     /.....| .||...|'     `|....' .||. .||. `|...  .||  ||.   `|..' \n";
  private static final String UNIVERS = "8b           d8 888888888888 88888888ba,              ,ad8888ba,  88 88  \n`8b         d8'          ,88 88      `\"8b            d8\"'    `\"8b 88 \"\"                          ,d     \n `8b       d8'         ,88\"  88        `8b          d8'           88                             88     \n  `8b     d8'        ,88\"    88         88          88            88 88   ,adPPYba, 8b,dPPYba, MM88MMM  \n   `8b   d8'       ,88\"      88         88 aaaaaaaa 88            88 88  a8P_____88 88P'   `\"8a  88     \n    `8b d8'      ,88\"        88         8P \"\"\"\"\"\"\"\" Y8,           88 88  8PP\"\"\"\"\"\"\" 88       88  88     \n     `888'      88\"          88      .a8P            Y8a.    .a8P 88 88  \"8b,   ,aa 88       88  88,    \n      `8'       888888888888 88888888Y\"'              `\"Y8888Y\"'  88 88  `\\\"Ybbd8\\\"'88       88  \\\"Y888 \"\n";
  private static final String SLANT = " _    _______   ____        ________    ___________   ________\n| |  / /__  /  / __ \\      / ____/ /   /  _/ ____/ | / /_  __/\n| | / /  / /  / / / /_____/ /   / /    / // __/ /  |/ / / /   \n| |/ /  / /__/ /_/ /_____/ /___/ /____/ // /___/ /|  / / /    \n|___/  /____/_____/      \\____/_____/___/_____/_/ |_/ /_/     \n";
  private static final String MERLIN = " ___      ___  ________   ________-   ______   ___        __     _______  _____  ___  ___________  \n|\"  \\    /\"  |(\"      \"\\ |\"      \"\\  /\" _  \"\\ |\"  |      |\" \\   /\"     \"|(\\\"   \\|\"  \\(\"     _   \") \n \\   \\  //  /  \\___/   :)(.  ___  :)(: ( \\___)||  |      ||  | (: ______)|.\\\\   \\    |)__/  \\\\__/  \n  \\\\  \\/. ./     /  ___/ |: \\   ) || \\/ \\     |:  |      |:  |  \\/    |  |: \\.   \\\\  |   \\\\_ /     \n   \\.    //     //  \\__  (| (___\\ || //  \\ _   \\  |___   |.  |  // ___)_ |.  \\    \\. |   |.  |     \n    \\\\   /     (:   / \"\\ |:       :)(:   _) \\ ( \\_|:  \\  /\\  |\\(:      \"||    \\    \\ |   \\:  |     \n     \\__/       \\_______)(________/  \\_______) \\_______)(__\\_|_)\\_______) \\___|\\____\\)    \\__|     \n";
  private static final String DIMENSION3 = "**      ** ******** *******           ******  **       ** ******** ****     ** **********\n/**     /**//////** /**////**         **////**/**      /**/**///// /**/**   /**/////**/// \n/**     /**     **  /**    /**       **    // /**      /**/**      /**//**  /**    /**    \n//**    **     **   /**    /** *****/**       /**      /**/******* /** //** /**    /**    \n //**  **     **    /**    /**///// /**       /**      /**/**////  /**  //**/**    /**    \n  //****     **     /**    **       //**    **/**      /**/**      /**   //****    /**    \n   //**     ********/*******         //****** /********/**/********/**    //***    /**    \n    //     //////// ///////           //////  //////// // //////// //      ///     //     ";
  private static final String ISOMETRIC = "      ___           ___           ___           ___           ___                   ___           ___           ___     \n     /\\__\\         /\\  \\         /\\  \\         /\\  \\         /\\__\\      ___        /\\  \\         /\\__\\         /\\  \\    \n    /:/  /         \\:\\  \\       /::\\  \\       /::\\  \\       /:/  /     /\\  \\      /::\\  \\       /::|  |        \\:\\  \\   \n   /:/  /           \\:\\  \\     /:/\\:\\  \\     /:/\\:\\  \\     /:/  /      \\:\\  \\    /:/\\:\\  \\     /:|:|  |         \\:\\  \\  \n  /:/__/  ___        \\:\\  \\   /:/  \\:\\__\\   /:/  \\:\\  \\   /:/  /       /::\\__\\  /::\\~\\:\\  \\   /:/|:|  |__       /::\\  \\ \n  |:|  | /\\__\\ _______\\:\\__\\ /:/__/ \\:|__| /:/__/ \\:\\__\\ /:/__/     __/:/\\/__/ /:/\\:\\ \\:\\__\\ /:/ |:| /\\__\\     /:/\\:\\__\\\n  |:|  |/:/  / \\::::::::/__/ \\:\\  \\ /:/  / \\:\\  \\  \\/__/ \\:\\  \\    /\\/:/  /    \\:\\~\\:\\ \\/__/ \\/__|:|/:/  /    /:/  \\/__/\n  |:|__/:/  /   \\:\\~~\\~~      \\:\\  /:/  /   \\:\\  \\        \\:\\  \\   \\::/__/      \\:\\ \\:\\__\\       |:/:/  /    /:/  /     \n   \\::::/__/     \\:\\  \\        \\:\\/:/  /     \\:\\  \\        \\:\\  \\   \\:\\__\\       \\:\\ \\/__/       |::/  /     \\/__/      \n    ~~~~          \\:\\__\\        \\::/__/       \\:\\__\\        \\:\\__\\   \\/__/        \\:\\__\\         /:/  /                 \n                   \\/__/         ~~            \\/__/         \\/__/                 \\/__/         \\/__/                  ";
  private static final List<String> PICS = Arrays
      .asList(GOTHINC, GOOFY, FUZZY, FOURTOPS, FENDER, UNIVERS, SLANT, MERLIN, DIMENSION3,
          ISOMETRIC);
  public static final String LINE = "\n=====================================================================\n\t\t\t=========================================================\n=====================================================================\n";

  public static String listToString(List<String> list) {
    if (list.isEmpty()) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s + ",");
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - 1);
    }
    return sb.toString();
  }

  public static String getPic() {
    StringBuffer asciPic = new StringBuffer();
    asciPic.append(LINE);
    asciPic.append(PICS.get(new Random().nextInt(GemStringUtils.PICS.size())));
    asciPic.append(LINE);
    return asciPic.toString();
  }

}
