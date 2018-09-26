package object;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicine")


public class MedicineDTO {

	@Id
	
	@Column(name="idmedicine")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name="expire_date")
	private Date expire_date;
	
	@Column(name="producer")
	private String producer;
	
	public void NewMedicine(String name, String barcode, Date expire_date, String producer) {
		this.name=name;
		this.barcode=barcode;
		this.expire_date=expire_date;
		this.producer=producer;
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
	
	
	
	
	
}
