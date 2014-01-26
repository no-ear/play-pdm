package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

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
	 * File binary data.
	 */
	@Lob
	@NotNull
	public byte[] file; // SUPPRESS CHECKSTYLE

	/**
	 * Original file name.
	 */
	@Column
	@NotNull
	public String fileName; // SUPPRESS CHECKSTYLE

	/**
	 * File hash.
	 */
	@Column(unique = true)
	@NotNull
	public String fileHash; // SUPPRESS CHECKSTYLE

	/**
	 * Original file extension.
	 */
	@Column
	public String fileExtension; // SUPPRESS CHECKSTYLE

	/**
	 * Related part version.
	 */
	@Column
	@ManyToOne
	public PartVersion partVersion; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, Document> find = new Model.Finder<Long, Document>( // SUPPRESS CHECKSTYLE
			Long.class, Document.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -1695193238431975776L;

	/**
	 * Build document.
	 * 
	 * @param properties
	 *            Document properties
	 * @param file
	 *            File contents
	 * @param partVersion
	 *            Part version relation
	 * @return New document
	 * @throws IOException
	 *             Not found file, Access error, etc.
	 */
	public static Document buildDocument(
			final HashMap<String, Object> properties, final File file,
			final PartVersion partVersion) throws IOException {
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

		document.file = readFileToByte(file);
		document.fileHash = DigestUtils.sha256Hex(document.file);
		document.partVersion = partVersion;

		document.save();

		return document;
	}

	/**
	 * File to byte[].
	 * 
	 * @param file
	 *            Target file
	 * @return byte array
	 * @throws IOException
	 *             Not found file, Access error, etc.
	 */
	private static byte[] readFileToByte(final File file) throws IOException {
		byte[] byteArray = null;

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);

			byteArray = IOUtils.toByteArray(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return byteArray;
	}
}
