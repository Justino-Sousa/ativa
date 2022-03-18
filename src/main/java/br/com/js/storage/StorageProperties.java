package br.com.js.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "upload-dir";
	private String fullLocation;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFullLocation() {
		return fullLocation;
	}

	public void setFullLocation(String fullLocation) {
		this.fullLocation = fullLocation;
	}
	
	

}
