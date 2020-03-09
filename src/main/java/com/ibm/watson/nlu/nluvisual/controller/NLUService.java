package com.ibm.watson.nlu.nluvisual.controller;

import java.util.Properties;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

class NLUService {

	private String baseURL = "<url>";
	private String username = "<username>";
	private String password = "<password>";
	private String apikey = "<apikey>";
	private boolean useIamApiKey = false;
	private NaturalLanguageUnderstanding service = null;
	private String versionDate = "2019-07-12";

	/**
	 * Return NLU service
	 */
	public NaturalLanguageUnderstanding getService() {
		if (getConfigParams()) {
			if (useIamApiKey) {
				IamOptions options = new IamOptions.Builder()
					.apiKey(apikey)
					.build();			
				service = new NaturalLanguageUnderstanding(versionDate, options);
			} else {
				service = new NaturalLanguageUnderstanding(versionDate, username, password);
			}
			service.setEndPoint(baseURL);
		}
		return service;
	}

	/**
	 * Value is considered entered if it is set, and doesn't equal the placeholder value
	 */
	private boolean keyValueEntered(String key, String value) {
		if ((value != null) && (!value.isEmpty()) && (!value.startsWith("<add_"))) {
			System.out.println(key + " has been set to: " + value);
			return true;
		}
		System.out.println(key + " has NOT been set");
		return false;
	}

	/**
	 * If set, use user provided config params
	 */
	private boolean getConfigParams() {
		String configFile = "/config.properties";

		try {
			Properties props = new Properties();
			props.load(this.getClass().getResourceAsStream(configFile));
			baseURL = props.getProperty("NATURAL_LANGUAGE_UNDERSTANDING_URL");
			apikey = props.getProperty("NATURAL_LANGUAGE_UNDERSTANDING_IAM_APIKEY");
			username = props.getProperty("NATURAL_LANGUAGE_UNDERSTANDING_USERNAME");
			password = props.getProperty("NATURAL_LANGUAGE_UNDERSTANDING_PASSWORD");
		} catch (Exception e) {
			System.out.println("Reading config properties error: " + e.getMessage());
			return false;
		}

		// make sure we have what we need

		// always need nlu url
		if (!keyValueEntered("config-baseURL", baseURL)) {
			return false;
		}

		// need at least one type of credential
		if (!keyValueEntered("config-apikey", apikey) &&
			(!keyValueEntered("config-uname", username) || (!keyValueEntered("config-pwd", password)))) {
			return false;
		}

		if (keyValueEntered("config-apikey", apikey)) {
			useIamApiKey = true;
		}

		System.out.println("using apikey = " + useIamApiKey);

		return true;
	}

}
