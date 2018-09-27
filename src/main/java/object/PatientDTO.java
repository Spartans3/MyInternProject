package object;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name= "newpatient")

public class PatientDTO implements java.io.Serializable {
	
	@Id
	
	@Column(name="idnewPatient")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="tc")
	private String tc;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="birthday")
	private Date birthday;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.newpatient", cascade=CascadeType.ALL)
	private Set<MedicineOfPatientDTO> medicineOfPatient = new HashSet<MedicineOfPatientDTO>(0);
	
	
	
	public void NewPatient(String name, String surname, String tc, String tel, Date birthday) {
		this.name = name;
		this.surname = surname;
		this.tc = tc;
		this.tel = tel;
		this.birthday = birthday;
	}
	
	public void NewPatient(String name, String surname, String tc, String tel, Date birthday, Set<MedicineOfPatientDTO> medicineOfPatient) {
		this.name = name;
		this.surname = surname;
		this.tc = tc;
		this.tel = tel;
		this.birthday = birthday;
		this.medicineOfPatient = medicineOfPatient;
	}
	
	

	public Set<MedicineOfPatientDTO> getMedicineOfPatient() {
		return medicineOfPatient;
	}

	public void setMedicineOfPatient(Set<MedicineOfPatientDTO> medicineOfPatient) {
		this.medicineOfPatient = medicineOfPatient;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	

}
