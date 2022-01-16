package cr.ac.ucr.ecci.eseg.guiaucr;

import com.google.firebase.database.ServerValue;

public class Comentario {

    private String content;
    private Object timestamp;

    public Comentario() {
    }

    public Comentario(String content) {
        this.content = content;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Comentario(String content, Object timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
