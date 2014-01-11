package models;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.partversions.PartVersion;
import play.db.ebean.Model;

/**
 * Document file model class.
 */
@Entity
@Table(name = "documents")
public final class Document extends Model {

	/**
	 * Surrogate key.
	 */
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id; // SUPPRESS CHECKSTYLE getter/setter create play framework.

	/**
	 * Part name.
	 */
	@Column
	public String name; // SUPPRESS CHECKSTYLE

	/**
	 * File binary data.
	 */
	@Lob
	public byte[] file; // SUPPRESS CHECKSTYLE

	/**
	 * Related part version.
	 */
	@Column
	@ManyToOne
	public PartVersion partVersion; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Part> find = new Model.Finder<Long, Part>( // SUPPRESS CHECKSTYLE
			Long.class, Part.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -1695193238431975776L;

	public static Document buildDocument(
			final HashMap<String, Object> properties, final File file) throws Exception {
		Document document = new Document();

		Field[] fields = Document.class.getFields();

		for (Field field : fields) {
			String key = "Document." + field.getName();
			if (!properties.containsKey(key)) {
				continue;
			}

			Object object = properties.get(key);
			try {
				field.set(document, object);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// No reachable code?
				e.printStackTrace();
			}
		}

		document.file = readFileToByte(file.getPath());

		document.save();

		return document;
	}

	/**
	 * File to byte[].
	 * 
	 * @param filePath
	 *            Target file path
	 * @return byte array
	 * @throws Exception
	 *             Not found file, Access error, etc.
	 */
	private static byte[] readFileToByte(final String filePath)
			throws Exception {
		byte[] b = new byte[1];
		FileInputStream fis = new FileInputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (fis.read(b) > 0) {
			baos.write(b);
		}
		baos.close();
		fis.close();
		b = baos.toByteArray();

		return b;
	}
}
