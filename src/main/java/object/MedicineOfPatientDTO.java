package object;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "medicineofpatient")

public class MedicineOfPatientDTO implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicineofpatientId")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patientId")
	private PatientDTO patient;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "medicineId")
	private MedicineDTO medicine;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "treatmentDays")
	private String treatmentDays;

	@Column(name = "daily")
	private String daily;

	@Column(name = "startDate")
	private Date startDate;

	public void MedicineOfPatient() {

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public MedicineDTO getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineDTO medicine) {
		this.medicine = medicine;
	}

}
