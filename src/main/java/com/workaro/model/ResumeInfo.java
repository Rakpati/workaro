package com.workaro.model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name="ResumeInfo")
public class ResumeInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique = true)
	private Long id;

	@Column(name="name", nullable = false)
	private String name;

	@Column(name="contactNo")
	private String contactNo;

	@Column(name="jobTitle")
	private String jobTitle;

	@Column(name="emailId", nullable = false)
	private String emailId;

	@Column(name="content")
	private String content;

	@Column(name="fileName")
	private String fileName;
	
	@Column(name="fileType")
	private String fileType;

	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name="resume")
	private byte[] resume;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
