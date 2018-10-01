package object;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patient")

public class PatientDTO implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patientId")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "tc")
	private String tc;

	@Column(name = "tel")
	private String tel;

	@Column(name = "birthday")
	private Date birthday;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = CascadeType.ALL)
	private List<MedicineDTO> medicine = new ArrayList<MedicineDTO>();

	public void NewPatient(String name, String surname, String tc, String tel, Date birthday) {
		this.name = name;
		this.surname = surname;
		this.tc = tc;
		this.tel = tel;
		this.birthday = birthday;
	}

	public void NewPatient(String name, String surname, String tc, String tel, Date birthday,
			List<MedicineDTO> medicine) {
		this.name = name;
		this.surname = surname;
		this.tc = tc;
		this.tel = tel;
		this.birthday = birthday;
		this.medicine = medicine;
	}

	public List<MedicineDTO> getMedicine() {
		return medicine;
	}

	public void setMedicine(List<MedicineDTO> medicine) {
		this.medicine = medicine;
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
