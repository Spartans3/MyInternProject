package object;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="medicineofpatient")
@AssociationOverrides({
	@AssociationOverride(name = "pk.medicine", 
		joinColumns = @JoinColumn(name = "idmedicine")),
	@AssociationOverride(name = "pk.newpatient", 
		joinColumns = @JoinColumn(name = "idnewPatient")) })

public class MedicineOfPatientDTO implements java.io.Serializable {
	
	private MedicineOfPatientIdDTO pk = new MedicineOfPatientIdDTO();
	
	
	@Column(name="quantity")
	private String quantity;
	
	@Column(name="treatmentDays")
	private String treatmentDays;
	
	@Column(name="daily")
	private String daily;
	
	@Column(name="startDate")
	private Date startDate;
	
	
	public void MedicineOfPatient() {
		
	}
	
	/*
	public void MedicineOfPatient(String quantity, String treatmentDays, String daily, Date startDate) {
		this.quantity=quantity;
		this.treatmentDays=treatmentDays;
		this.daily=daily;
		this.startDate=startDate;
	}
	*/
	@EmbeddedId
	public MedicineOfPatientIdDTO getPk() {
		return pk;
	}

	public void setPk(MedicineOfPatientIdDTO pk) {
		this.pk = pk;
	}
	
	@Transient
	public PatientDTO getPatientDTO() {
		return getPk().getPatientDTO();
	}
	public void setPatientDTO(PatientDTO patientDTO) {
		getPk().setPatientDTO(patientDTO);
	}
	
	@Transient
	public MedicineDTO getMedicineDTO() {
		return getPk().getMedicineDTO();
	}
	public void setMedicineDTO(MedicineDTO medicineDTO) {
		getPk().setMedicineDTO(medicineDTO);
	}
	

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTreatmentDays() {
		return treatmentDays;
	}

	public void setTreatmentDays(String treatmentDays) {
		this.treatmentDays = treatmentDays;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MedicineOfPatientDTO that = (MedicineOfPatientDTO) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
	
	
	
	
}
