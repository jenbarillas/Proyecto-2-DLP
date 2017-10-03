

package estructuras;
import java.util.*;

public class EstadoAFD extends Estado{
    ArrayList<Estado> Dstates;
    boolean marcado;
    
    public EstadoAFD(int idEstadoD){
        super(idEstadoD);
        Dstates = new ArrayList<>();
        marcado = false;
    }
    
    public EstadoAFD(ArrayList<Estado> Dstates, boolean marcado, int idEstado, boolean esFinal){
        super(idEstado, esFinal);
        this.Dstates = Dstates;
        this.marcado = marcado;
    }

    public ArrayList<Estado> getDstates() {
        return Dstates;
    }

    public void setDstates(ArrayList<Estado> Dstates) {
        this.Dstates = Dstates;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoAFD other = (EstadoAFD) obj;
        if (other.getDstates().containsAll(Dstates) && Dstates.containsAll(other.getDstates())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
    
    
}
