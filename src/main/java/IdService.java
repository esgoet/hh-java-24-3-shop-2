import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdService {
    List<UUID> generatedIds = new ArrayList<>();

    public UUID generateId() {
        UUID newId = UUID.randomUUID();
        while (generatedIds.contains(newId)) {
            newId = UUID.randomUUID();
        }
        generatedIds.add(newId);
        return newId;
    }

}
