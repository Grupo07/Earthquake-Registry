
package data;

import data.FaultOrigin;
import data.Province;
import java.time.LocalDateTime;

public class Earthquake {
    
    private LocalDateTime date;
    private Province province;
    private FaultOrigin faultOrigin;
    private float magnitude;
    
    public Earthquake() {
        
    }
    
    public Earthquake(LocalDateTime date, float magnitude, Province province, FaultOrigin faultOrigin) {
        this.date = date;
        this.magnitude = magnitude;
        this.province = province;
        this.faultOrigin = faultOrigin;
    }

    public FaultOrigin getFaultOrigin() {
        return faultOrigin;
    }
    
    public void setFaultOrigin(FaultOrigin faultOrigin) {
        this.faultOrigin = faultOrigin;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }
            
}
