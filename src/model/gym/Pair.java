package model.gym;

import java.io.Serializable;

public class Pair<X,Y> implements Serializable {
	

	private static final long serialVersionUID = 6209827569491880779L;
	
	private final X x;
    private final Y y;
    
    public Pair(final X x, final Y y) {
            super();
            this.x = x;
            this.y = y;
    }
    
    public X getX() {
            return x;
    }
    
    public Y getY() {
            return y;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        final Pair other = (Pair) obj;
        if (this.x == null && other.x != null) {
            return false;
        }
        
        if (!this.x.equals(other.x)) {
            return false;
        }
        
        if (y == null && other.y != null) {
            return false;
        }
        
        if (!this.y.equals(other.y)) {
            return false;
        }
        
        return true;
    }
    
    
}
