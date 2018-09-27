package object;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MedicineOfPatientIdDTO implements java.io.Serializable{
	private MedicineDTO medicine;
	private PatientDTO newpatient;
	
	@ManyToOne
	public MedicineDTO getMedicineDTO() {
		return medicine;
	}
	public void setMedicineDTO(MedicineDTO medicine) {
		this.medicine = medicine;
	}
	
	@ManyToOne
	public PatientDTO getPatientDTO() {
		return newpatient;
	}
	public void setPatientDTO(PatientDTO newpatient) {
		this.newpatient = newpatient;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineOfPatientIdDTO that = (MedicineOfPatientIdDTO) o;

        if (newpatient != null ? !newpatient.equals(that.newpatient) : that.newpatient != null) return false;
        if (medicine != null ? !medicine.equals(that.medicine) : that.medicine != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (newpatient != null ? newpatient.hashCode() : 0);
        result = 31 * result + (medicine != null ? medicine.hashCode() : 0);
        return result;
    }
	
}
