package models.partversions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.db.ebean.Model;

/**
 * Example Frame Part version in design view Model.
 */
@Entity
@DiscriminatorValue("FRAME_PART_VERSION")
public class FramePartVersion extends PartVersion {

	/**
	 * Example unique attribute.
	 */
	@Column
	public double width; // SUPPRESS CHECKSTYLE

	/**
	 * Example unique attribute.
	 */
	@Column
	public double height; // SUPPRESS CHECKSTYLE

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, FramePartVersion> find = new Model.Finder<Long, FramePartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, FramePartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -59223505116187178L;
}
