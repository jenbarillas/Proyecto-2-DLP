
package Fase2;

/**
 *
 * @author Jenny
 */
public class Token {
    String id;
    String cuerpo;

    public Token() {
        this.id = null;
        this.cuerpo = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    @Override
    public String toString() {
        return  "<" + id + ", \"" + cuerpo + "\"> ";
    }
    
    
    
    
}
