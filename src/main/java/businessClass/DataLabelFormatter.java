package businessClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

import dbConnection.HibernateUtil;

public class DataLabelFormatter extends AbstractFormatter {

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-M-yyy");

	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}
		
		//HibernateUtil.getSessionFactory().openSession();

		return "";
	}
}
