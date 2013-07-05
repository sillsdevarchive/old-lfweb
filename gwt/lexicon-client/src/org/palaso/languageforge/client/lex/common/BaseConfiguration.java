package org.palaso.languageforge.client.lex.common;

public class BaseConfiguration {
	private static BaseConfiguration instance;
	private boolean isInitialized = false;
	// public static final String LFBASE_API = "LFBaseAPI.php";
	// public static final String LANG_FORGE_API = "LanguageForgeAPI.php";
	// public static final String LF_BASE_API_PATH = "/lf/";
	private String lfApiFile = "";
	private String lfApiPath = "";

	private BaseConfiguration() {
	}

	public static synchronized BaseConfiguration getInstance() {
		if (instance == null)
			instance = new BaseConfiguration();
		return instance;
	}

	public void setApiPath(String LFApiFileName, String LFPath) {
		lfApiFile = LFApiFileName;
		lfApiPath = LFPath;
		isInitialized = true;
	}

	public String getApiFileName() {
		if (!isInitialized) {
			throw new RuntimeException("LFBase not initialized, BaseConfiguration.setApiPath must called first.");
		}
		return lfApiFile;
	}

	public String getLFApiPath() {
		if (!isInitialized) {
			throw new RuntimeException("LFBase not initialized, BaseConfiguration.setApiPath must called first.");
		}
		return lfApiPath;
	}
}