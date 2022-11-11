package persistence;

import org.json.JSONObject;

import java.io.IOException;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson() throws IOException;
}
