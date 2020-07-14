/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gemClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GemStringUtils {

    private static final String gothic = "____   ______________________            _________ .__  .__               __   \n\\   \\ /   /\\____    /\\______ \\           \\_   ___ \\|  | |__| ____   _____/  |_ \n \\   Y   /   /     /  |    |  \\   ______ /    \\  \\/|  | |  |/ __ \\ /    \\   __\\ \n  \\     /   /     /_  |    `   \\ /_____/ \\     \\___|  |_|  \\  ___/|   |  \\  |\n   \\___/   /_______ \\/_______  /          \\______  /____/__|\\___  >___|  /__|\n                   \\/        \\/                  \\/             \\/     \\/      \n";
    private static final String goofy = "    __    __      __     ___________    __     _____      __        __      ___    __        __\n|  |  |  | (___   ) |    \\         /  __) \\   |    (_    _) \\    ___) |    \\  |  | (__    __) \n|  |  |  |    /  /  |     |  ___  |  /     |  |      |  |    |  (__    |  |  \\    |    |  |    \n|  |  |  |   /  /   |     | (___) | |      |  |      |  |    |   __)   |  | \\ \\|  |    |  |    \n \\  \\/  /   /  /__  |     |       |  \\__   |  |__   _|  |_   |  (___   |  |  \\    |    |  |    \n__\\    /___(      )_|    /_________\\    )_/      )_(      )_/       )_ |  |___\\   |____|  |____\n";
    private static final String fuzzy = ".-..-..----..---.         .--. .-.   _              .-. \n: :: :`--. :: .  :       : .--': :  :_;            .' `.\n: :: :  ,',': :: : _____ : :   : :  .-. .--. ,-.,-.`. .'\n: `' ;.'.'_ : :; ::_____:: :__ : :_ : :' '_.': ,. : : : \n `.,' :____;:___.'       `.__.'`.__;:_;`.__.':_;:_; :_;\n";
    private static final String fourtops = "|    |~~/|~~\\     /~~|'        | \n \\  /  / |   |---|   ||/~/|/~\\~|~\n  \\/  /__|__/     \\__||\\/_|   || \n";
    private static final String fender = "\\\\      // |'''''/ '||'''|.     .|'''', '||`                        ||    \n \\\\    //      //   ||   ||     ||       ||   ''                    ||    \n  \\\\  //      //    ||   || --- ||       ||   ||  .|''|, `||''|,  ''||''  \n   \\\\//      //     ||   ||     ||       ||   ||  ||..||  ||  ||    ||    \n    \\/     /.....| .||...|'     `|....' .||. .||. `|...  .||  ||.   `|..' \n";
    private static final String univers = "8b           d8 888888888888 88888888ba,              ,ad8888ba,  88 88  \n`8b         d8'          ,88 88      `\"8b            d8\"'    `\"8b 88 \"\"                          ,d     \n `8b       d8'         ,88\"  88        `8b          d8'           88                             88     \n  `8b     d8'        ,88\"    88         88          88            88 88   ,adPPYba, 8b,dPPYba, MM88MMM  \n   `8b   d8'       ,88\"      88         88 aaaaaaaa 88            88 88  a8P_____88 88P'   `\"8a  88     \n    `8b d8'      ,88\"        88         8P \"\"\"\"\"\"\"\" Y8,           88 88  8PP\"\"\"\"\"\"\" 88       88  88     \n     `888'      88\"          88      .a8P            Y8a.    .a8P 88 88  \"8b,   ,aa 88       88  88,    \n      `8'       888888888888 88888888Y\"'              `\"Y8888Y\"'  88 88  `\\\"Ybbd8\\\"'88       88  \\\"Y888 \"\n";
    private static final List<String> pics = Arrays
        .asList(gothic, goofy, fuzzy, fourtops, fender, univers);

    public static String listToString(List<String> list) {
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
        asciPic.append("\n=====================================================================\n");
        asciPic.append("\t\t\t=========================================================\n");
        asciPic.append("=====================================================================\n");
        asciPic.append(pics.get(new Random().nextInt(GemStringUtils.pics.size())));
        asciPic.append("=====================================================================\n");
        asciPic.append("\t\t\t=========================================================\n");
        asciPic.append("=====================================================================\n");
        return asciPic.toString();
    }
}
