package object;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="medicine")


public class MedicineDTO implements java.io.Serializable {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="medicineId")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name="expire_date")
	private Date expire_date;
	
	@Column(name="producer")
	private String producer;
		
	@OneToMany(fetch = FetchType.LAZY /*,mappedBy = "medicine"*/)
	private List<PatientDTO> patient = new ArrayList<PatientDTO>();

	public void NewMedicine(String name, String barcode, Date expire_date, String producer) {
		this.name=name;
		this.barcode=barcode;
		this.expire_date=expire_date;
		this.producer=producer;
	}
	
	public void NewMedicine(String name, String barcode, Date expire_date, String producer,List<PatientDTO> patient ) {
		this.name=name;
		this.barcode=barcode;
		this.expire_date=expire_date;
		this.producer=producer;
		this.patient=patient;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public List<PatientDTO> getPatient() {
		return patient;
	}

	public void setPatient(List<PatientDTO> patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return this.name;
	}
	

	
}
