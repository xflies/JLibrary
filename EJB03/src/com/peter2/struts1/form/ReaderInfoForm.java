/**
 * 
 */
package com.peter2.struts1.form;

import org.apache.struts.action.ActionForm;

/**
 * @author Peter2_Weng
 *
 */
public class ReaderInfoForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6368405898233739733L;
	
	private Integer id;
	private String password;
	private String name;
	private String mailAddr;
	private Boolean activated;
	private String newPassword;
	private String confirmPassword;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the mailAddr
	 */
	public String getMailAddr() {
		return mailAddr;
	}
	/**
	 * @param mailAddr the mailAddr to set
	 */
	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}
	/**
	 * @return the activated
	 */
	public Boolean getActivated() {
		return activated;
	}
	/**
	 * @param activated the activated to set
	 */
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
