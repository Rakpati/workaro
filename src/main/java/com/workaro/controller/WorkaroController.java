package com.workaro.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.workaro.model.CorporateZone;
import com.workaro.model.Industry;
import com.workaro.model.ResumeInfo;
import com.workaro.repository.CorporateZoneRepository;
import com.workaro.repository.IndustryRepository;
import com.workaro.repository.ResumeInfoRepository;

@Controller
public class WorkaroController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResumeInfoRepository resumeInfoRepository;

	@Autowired
	CorporateZoneRepository corporateZoneRepository;

	@Autowired
	IndustryRepository industryRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String viewHomePage(Model model) {

		try {
			System.out.println("Main Page loaded");
			
			List<Industry> industry = industryRepository.findAll();
			model.addAttribute("industryVal", industry);
			
			logger.info("Main Page loaded");
			return "index";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "errorClient";
		}
	}

	@RequestMapping(value = "/uploadResume", method = RequestMethod.POST)
	public String uploadResume(Model model, @RequestParam("name") String name, @RequestParam("emailId") String emailId,
			@RequestParam("contactNo") String contactNo, @RequestParam("content") String content,
			@RequestParam("jobTitle") String jobTitle, @RequestParam("resume") MultipartFile resume)
					throws Exception {
		try {
			String fileName = StringUtils.cleanPath(resume.getOriginalFilename());

			if(fileName.contains("..")) {
				throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
			}

			ResumeInfo resumeInfo = new ResumeInfo();
			resumeInfo.setFileType(resume.getContentType());
			resumeInfo.setFileName(fileName);
			resumeInfo.setName(name);
			resumeInfo.setContent(content);
			resumeInfo.setContactNo(contactNo);
			resumeInfo.setEmailId(emailId);
			resumeInfo.setJobTitle(jobTitle);
			resumeInfo.setResume(resume.getBytes());


			List<Industry> industry = industryRepository.findAll();
			model.addAttribute("industryVal", industry);

			resumeInfoRepository.save(resumeInfo);
			logger.info(resumeInfo.getName() + " -- " + resumeInfo.getEmailId() + " -- " + resumeInfo.getContent()
			+ " RESUME SAVED");
			return "index";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "errorClient";

		}
	}

	@RequestMapping(value = "/corporateZone", method = RequestMethod.POST)
	public String corporateZone(Model model,
			@ModelAttribute("CorporateZone") @Valid @RequestBody CorporateZone corporateZone, BindingResult result) {
		try {
			List<Industry> industry = industryRepository.findAll();
			Optional<Industry> ind = null;
			ind = industryRepository.findById(Long.parseLong(corporateZone.getIndustry()));
			corporateZone.setIndustry(ind.get().getIndustryType());

			model.addAttribute("industryVal", industry);

			corporateZoneRepository.save(corporateZone);

			logger.info(corporateZone.getFirmname() + " -- " + corporateZone.getEmailId() + " CORPORATE SAVED");
			return "index";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "errorClient";
		}
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.POST)
	public String contactUs(Model model, @ModelAttribute("ResumeInfo") ResumeInfo resumeInfo) {
		try {
			List<Industry> industry = industryRepository.findAll();
			model.addAttribute("industryVal", industry);

			resumeInfoRepository.save(resumeInfo);
			logger.info(
					resumeInfo.getName() + " -- " + resumeInfo.getEmailId() + " -- " + resumeInfo.getContent() + " SAVED");
			System.out.println(
					resumeInfo.getName() + " -- " + resumeInfo.getEmailId() + " -- " + resumeInfo.getContent() + " SAVED");

			return "index";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "errorClient";
		}
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String corporateCompanies(Model model) {

		try {

			var corporateList = (List<CorporateZone>) corporateZoneRepository.findAll();

			List<ResumeInfo> resumeList = resumeInfoRepository.findAllResume();

			List<ResumeInfo> contactUs = resumeInfoRepository.findContactList();

			model.addAttribute("corporateList", corporateList);
			model.addAttribute("resumeList", resumeList);
			model.addAttribute("contactUs", contactUs);

			return "corporateView";
		} catch (Exception e) {
			e.printStackTrace();
			return "errorClient";
		}
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletResponse httpServletResponse) throws IOException {
		// Load file from database
		try {
			Optional<ResumeInfo> dbFile = resumeInfoRepository.findById(Long.valueOf(fileId));
			System.out.println(fileId);

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(dbFile.get().getFileType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.get().getFileName()+ "\"")
					.body(new ByteArrayResource(dbFile.get().getResume()));

		} catch (Exception e) {
			// TODO: handle exception
			httpServletResponse.sendRedirect("errorClient");
			return null ;
		}
	}
	@RequestMapping(value = "/downloadFile/errorClient", method = RequestMethod.GET)
	public String viewErrorPage(Model model) {
		System.out.println("Error Page loaded");

		return "errorClient";
	}
}
