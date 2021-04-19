package rain.sshshell;

import java.util.List;

public interface Commend {
    String getCommendLine();

    Object dealResult(List<String> resultLines);
}
