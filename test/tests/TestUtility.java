package tests;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;

/**
 * テスト用雑多な関数クラス.<br>
 * クラス名は変更するかも。
 */
public final class TestUtility {

	/**
	 * コンストラクタキルする.
	 */
	private TestUtility() {
	}

	/**
	 * テストするとき、データベースのデータをリフレッシュする.
	 */
	public static void dropCreateDb() {

		String serverName = "default";

		EbeanServer server = Ebean.getServer(serverName);

		ServerConfig config = new ServerConfig();

		DdlGenerator ddl = new DdlGenerator();
		ddl.setup((SpiEbeanServer) server, new MySqlPlatform(), config);
		ddl.runScript(false, ddl.generateDropDdl());
		ddl.runScript(false, ddl.generateCreateDdl());
	}
}
