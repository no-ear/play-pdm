package models.partversions;

import javax.persistence.Entity;

import play.db.ebean.Model;

/**
 * Part version in design view Model.
 */
@Entity
public class DesignPartVersion extends PartVersion {

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, DesignPartVersion> find = new Model.Finder<Long, DesignPartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, DesignPartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -6561028321214130106L;
}
