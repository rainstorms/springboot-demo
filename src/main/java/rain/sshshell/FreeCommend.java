package rain.sshshell;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

public class FreeCommend implements Commend {

    @Override public String getCommendLine() {
        return "free -m";
    }

    @Override public HashMap<String, String> dealResult(List<String> resultLines) {
        HashMap<String, String> map = Maps.newHashMap();
        String titleLine = resultLines.get(0);
        List<String> title = Splitter.on(" ").omitEmptyStrings().trimResults()
                .splitToList(titleLine);

        String resultLine = resultLines.get(1);
        List<String> result1 = Splitter.on(" ").omitEmptyStrings().trimResults()
                .splitToList(resultLine);

        for (int i = 0; i < title.size(); i++) {
            map.put(title.get(i), result1.get(i + 1));
        }
        return map;
    }
}
