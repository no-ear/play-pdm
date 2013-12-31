package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import play.db.ebean.Model;

/**
 * 実績モデル.
 */
@Entity
@DiscriminatorValue("PART_VERSION")
public final class PartVersion extends AbstractPartVersion {

	/**
	 * Finder.
	 */
	public static Model.Finder<Long, AbstractPartVersion> find = new Model.Finder<Long, AbstractPartVersion>( // SUPPRESS CHECKSTYLE
			Long.class, AbstractPartVersion.class);

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -6561028321214130106L;

}
